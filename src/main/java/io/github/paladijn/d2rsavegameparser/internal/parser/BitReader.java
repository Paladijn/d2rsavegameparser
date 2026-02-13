/*
 * Copyright (C) 2023   Paladijn (paladijn2960+d2rsavegameparser@gmail.com)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA 
 */
package io.github.paladijn.d2rsavegameparser.internal.parser;

import io.github.paladijn.d2rsavegameparser.model.Item;
import io.github.paladijn.d2rsavegameparser.parser.ParseException;
import org.slf4j.Logger;

import java.util.Map;

import static java.util.Map.entry;
import static org.slf4j.LoggerFactory.getLogger;

public final class BitReader {
    private static final Logger log = getLogger(BitReader.class);

    private final byte[] data;
    private int positionInBits;

    private final Map<String, Character> huffmanDictionary = Map.ofEntries(
            entry("11110", 'a'),
            entry("0101", 'b'),
            entry("01000", 'c'),
            entry("110001", 'd'),
            entry("110000", 'e'),
            entry("010011", 'f'),
            entry("11010", 'g'),
            entry("00011", 'h'),
            entry("1111110", 'i'),
            entry("000101110", 'j'),
            entry("010010", 'k'),
            entry( "11101", 'l'),
            entry("01101", 'm'),
            entry("001101", 'n'),
            entry("1111111", 'o'),
            entry("11011001", 'q'),
            entry( "11001", 'p'),
            entry("11100", 'r'),
            entry("0010", 's'),
            entry("01100", 't'),
            entry("00001", 'u'),
            entry("1101110", 'v'),
            entry("00000", 'w'),
            entry("00111", 'x'),
            entry("0001010", 'y'),
            entry("11011000", 'z'),
            entry("10", ' '),
            entry("11111011", '0'),
            entry("1111100", '1'),
            entry("001100", '2'),
            entry("1101101", '3'),
            entry("11111010", '4'),
            entry("00010110", '5'),
            entry("1101111", '6'),
            entry("01111", '7'),
            entry("000100", '8'),
            entry( "01110", '9')
    );


    public BitReader(byte[] data) {
        this.data = data;
        positionInBits = 0;
    }

    public void skip(int bits) {
        increasePositionInBits(bits);
    }

    public void moveToNextByteBoundary() {
        int newPosition = (positionInBits + 7) & (~7);
        log.debug("moveToNextByteBoundary:: {} -> {}", positionInBits, newPosition);
        positionInBits = newPosition;
    }

    public int bitsToNextBoundary() {
        return ((positionInBits + 7) & ~7) - positionInBits;
    }

    public char readChar(int bits) {
        return (char) unflip(read(bits), bits);
    }

    public short readShort(int bits) {
        return (short) unflip(read(bits), bits);
    }

    public int readInt() {
        return readInt(32);
    }
    
    public int readInt(int bits) {
        return (int) unflip(read(bits), bits);
    }

    public long readLong(int bits) {
        return unflip(read(bits), bits);
    }

    public int readFlippedInt(int bits) {
        return (int) read(bits);
    }

    public int getPositionInBits() {
        return positionInBits;
    }

    private void increasePositionInBits(int amount) {
        positionInBits += amount;
    }

    private long read(int bits) {
        final int positionInBytes = positionInBits / 8;
        final int bitsInLastByte = positionInBits % 8;

        long result = 0;
        for (int i = 0; i < 8; i++) {
            result = result << 8;
            if (positionInBytes + i < data.length) {
                byte readByte = data[positionInBytes + i];
                int unsigned = 0x000000ff & flipByte(readByte);
                result += unsigned;
            } else {
                result += 0;
            }
        }

        result = result << bitsInLastByte;
        result = result >>> (64 - bits);

        // update position
        increasePositionInBits(bits);

        return result;
    }

    public String readHuffmanEncodedString() {
        StringBuilder reader = new StringBuilder();
        StringBuilder result = new StringBuilder();
        Character character;
        do {
            reader.append(read(1));
            character = huffmanDictionary.get(reader.toString());
            if (character != null && ' ' != character) {
                result.append(character);
                reader.setLength(0);
            }
            if (reader.length() > 100 || result.length() > 100) {
                throw new ParseException("Huffman decoding failed, string too long");
            }
        } while (character == null || ' ' != character);
        return result.toString();
    }

    private static int flipByte(byte b) {
        int ret = 0;
        for (int i = 0; i < 8; i++) {
            int bit = (b >> i) & 0x01;
            ret = ret << 1;
            ret += bit;
        }
        return ret;
    }

    private long unflip(long l, int bits) {
        long ret = 0;
        for (int i = 0; i < bits; i++) {
            int bit = (int) ((l >> i) & 0x01);
            ret = ret << 1;
            ret += bit;
        }
        return ret;
    }

    /**
     * helper function to print the current item's bytes, so they can be used for debugging and unit tests. This does not log
     * @param item The {@link Item} to print. This will only list the bytes, the item itself will also be printed on debug level.
     * @param startIndex The start index of the current item in this {@link BitReader} instance.
     */
    public void printBytes(Item item, int startIndex) {
        int endIndex = positionInBits / 8;
        log.debug("printing bytes {} -> {} of {}", startIndex, endIndex, item);
        StringBuilder sbBytes = new StringBuilder();
        sbBytes.append(item)
                .append("\n\t")
                .append(item.itemName())
                .append(" :: ")
                .append("byte[] bytes = {");

        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                sbBytes.append(", ");
            }
            sbBytes.append(data[i]);
        }

        sbBytes.append("};");
        log.info(String.valueOf(sbBytes));
    }

    /**
     * helper function to print the current item's bytes in hexadecimal format, so they can be used for debugging and unit tests.
     * @param item The {@link Item} to print.
     * @param startIndex The start index of the current item in this {@link BitReader} instance.
     */
    public void printHexBytes(Item item, int startIndex) {
        int endIndex = positionInBits / 8;
        log.debug("printing hex bytes {} -> {} of {}", startIndex, endIndex, item.itemName());
        StringBuilder sbBytes = new StringBuilder();
        sbBytes.append(item.itemName())
                .append(" in Hex\n");

        int displayIndex = 0;
        for (int i = startIndex; i < endIndex; i++) {
            if (displayIndex % 16 == 0) {
                sbBytes.append("\n");
            }
            displayIndex++;
            sbBytes.append(String.format("%02X ", data[i]));
        }

        sbBytes.append("\n");
        log.info(String.valueOf(sbBytes));
    }

    public byte getData(int i) {
        return data[i];
    }
}
