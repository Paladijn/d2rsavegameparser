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
package io.github.paladijn.d2rsavegameparser.parser;


import io.github.paladijn.d2rsavegameparser.internal.parser.BitReader;
import io.github.paladijn.d2rsavegameparser.model.SharedStashTab;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Parser for the shared stash files that were introduced by Diablo II: resurrected. This reads both the SharedStashSoftCoreV2.d2i and the SharedStashHardCoreV2.d2i.
 *
 * @author Paladijn
 */
public final class SharedStashParser {
    private static final byte[] STASH_INIT_BYTES = {85, -86, 85, -86};

    /** Softcore shared stash filename */
    public static final String SOFTCORE_SHARED_STASH = "SharedStashSoftCoreV2.d2i";

    public static final String ROTW_SHARED_STASH = "ModernSharedStashSoftCoreV2.d2i";

    /** Hardcore shared stash filename */
    public static final String HARDCORE_SHARED_STASH = "SharedStashHardCoreV2.d2i";
    private static final Logger log = LoggerFactory.getLogger(SharedStashParser.class);

    private final ItemParser itemParser;

    /**
     * Constructor for the {@link SharedStashParser}
     * @param printItemBytes helper boolean to log the bytes[] per Item for unit tests and debugging.
     */
    public SharedStashParser(boolean printItemBytes) {
        itemParser = new ItemParser(printItemBytes);
    }

    /**
     * Parse the {@link ByteBuffer} of a shared stash file to a list of {@link SharedStashTab}.
     * @param buffer a {@link ByteBuffer} of a Diablo II: resurrected savegame file
     * @return a list of {@link SharedStashTab}
     * @throws ParseException in case there was an issue parsing the buffer
     */
    public List<SharedStashTab> parse(final ByteBuffer buffer) {
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        final List<SharedStashTab> tabs = new ArrayList<>();
        final List<Integer> tabIndices = getStartIndices(buffer);

        if (tabIndices.size() == 3) {
            log.debug("parsing pre-RotW shared stash");
            tabIndices.forEach( index -> tabs.add(parseTab(index, buffer)));
        } else {
            if (tabIndices.size() != 7) {
                throw new ParseException("SharedStash did not contain seven tabs, but " + tabIndices.size());
            }
            for (int i = 0; i < 6; i++) { // skip the last tab as that contains the Chronicle data
                tabs.add(parseTab(tabIndices.get(i), buffer));
            }
            parseChronicleTab(tabIndices.get(6), buffer);
        }

        return tabs;
    }

    private void parseChronicleTab(final int index, final ByteBuffer buffer) {
        final SharedStashTab stashWithoutItems = parseHeader(index, buffer);
        log.debug("Parsing Chronicle tab at index {} length {}", index, stashWithoutItems.lengthInBytes());

        // data seems to start at 88 bytes in, each time a short, int and long. I assume in the fields before there's an amount of items found available (per unique, set, runeword perhaps?)
        int start = index + 88;
        while (start < (index + stashWithoutItems.lengthInBytes())) {
            byte[] chronicleData = new byte[7];
            buffer.get(start, chronicleData);
            BitReader br = new BitReader(chronicleData);
            final short monsterId = br.readShort(16);
            if (monsterId == 0) {
                log.debug("end of the chronicle items at index {}?", start);
                // likely the next items are the runewords as they lack a monsterId (for obvious reasons).
                break;
            }
            final LocalDateTime found = LocalDateTime.ofInstant(
                    Instant.ofEpochSecond(br.readInt(32) * 60),
                    ZoneId.systemDefault()
            );
            final short extraID = br.readShort(16);
            log.debug("Chronicle data at {} monsterId {}, time {}, unknown {}", start, monsterId, found, extraID);
            start += 10;
        }
    }

    private SharedStashTab parseTab(final int index, final ByteBuffer buffer) {
        final SharedStashTab stashWithoutItems = parseHeader(index, buffer);
        log.debug("Parsing tab at index {} length {}, gold {}", index, stashWithoutItems.lengthInBytes(), stashWithoutItems.gold());

        // the items start at byte 64, this feels a bit hacky because we have to re-use the header content to calculate the end index of the items
        return new SharedStashTab.SharedStashTabBuilder()
                .version(stashWithoutItems.version())
                .gold(stashWithoutItems.gold())
                .lengthInBytes(stashWithoutItems.lengthInBytes())
                .items(itemParser.parseItems(buffer, index + 64, index + stashWithoutItems.lengthInBytes()))
                .build();
    }

    private SharedStashTab parseHeader(final int index, final ByteBuffer buffer) {
        byte[] headerBytes = new byte[20];
        buffer.get(index, headerBytes, 0, 20);
        final BitReader headerData = new BitReader(headerBytes);
        headerData.skip(64); // skip the first 8 bytes

        final int version = headerData.readInt();
        if (version != 105) {
            throw new ParseException("Unsupported shared stash version " + version);
        }

        return new SharedStashTab.SharedStashTabBuilder()
                .version(version)
                .gold(headerData.readInt())
                .lengthInBytes(headerData.readInt())
                .build();
    }

    private List<Integer> getStartIndices(ByteBuffer buffer) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < buffer.limit() - 3; i++) {
            if (buffer.get(i) == 85) { // in this case check for 55 AA 55 AA
                byte[] header = new byte[4];
                buffer.get(i, header, 0, 4);
                if (Arrays.equals(header, STASH_INIT_BYTES)) {
                    indices.add(i);
                    i += 3; // we can skip the next 3 bytes
                }
            }
        }
        log.debug("start indices: {}", indices);

        return indices;
    }
}
