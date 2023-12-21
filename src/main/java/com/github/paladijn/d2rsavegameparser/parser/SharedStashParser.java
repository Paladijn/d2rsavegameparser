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
package com.github.paladijn.d2rsavegameparser.parser;


import com.github.paladijn.d2rsavegameparser.internal.parser.BitReader;
import com.github.paladijn.d2rsavegameparser.model.SharedStashTab;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
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

    /** Hardcore shared stash filename */
    public static final String HARDCORE_SHARED_STASH = "SharedStashHardCoreV2.d2i";

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
        final List<Integer> tabIndeces = getStartIndices(buffer);

        tabIndeces.forEach( index -> tabs.add(parseTab(index, buffer)));

        return tabs;
    }

    private SharedStashTab parseTab(final int index, final ByteBuffer buffer) {
        final SharedStashTab stashWithoutItems = parseHeader(index, buffer);

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
        if (version != 99) {
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
        if (indices.size() != 3) {
            throw new ParseException("SharedStash did not contain three tabs, but " + indices.size());
        }
        return indices;
    }
}
