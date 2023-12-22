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
package com.github.paladijn.d2rsavegameparser.txt;

import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Representation of a runeword from the runes.txt resource file.
 *
 * @author Paladijn
 */
public final class Runeword {
    private static final Logger log = getLogger(Runeword.class);

    private final String name;

    private final boolean active;

    private String patch;

    private final List<String> supportedTypes = new ArrayList<>();

    private String runeString;

    private final Map<String, Integer> runes = new HashMap<>();

    /**
     * Constructor which parses a tab-separated line from runes.txt
     * @param line a tab-separated line which contains the fields we need for this item representation
     */
    public Runeword(String line) {
        String[] blocks = line.split("\t");
        name = blocks[1];
        active = "1".equals(blocks[2]);
        if (!active) {
            return;
        }
        patch = blocks[5];
        for (int i = 5; i <=10; i++) { // iType1..6
            if (blocks[i].isBlank()) {
                break;
            }
            supportedTypes.add(blocks[i]);
        }
        runeString = blocks[15];

        for (int i = 16; i <= 21; i++) { // Rune1..6
            if (blocks[i].isBlank()) {
                break;
            }
            final String rune = blocks[i];
            int count = runes.getOrDefault(rune, 0);
            runes.put(rune, ++count);
        }
    }

    /**
     * Verify this is a valid runeword combination
     * @param runes the runes in the order they were inserted into the item (without any separator)
     * @return true if this is a valid and active runeword, false if not
     */
    public boolean isThisARuneword(String runes) {
        return active && runeString.equals(runes);
    }

    /**
     * Is this runeword possible with the map of available runes
     * @param availableRunes a map per rune with the number of them available.
     * @return true if the runeword is possible with the supplied runes, false if not.
     */
    public boolean isRunewordPossible(Map<String, Integer> availableRunes) {
        if (!active) {
            return false;
        }
        if (patch != null && patch.startsWith("D2R Ladder")) {
            log.debug("DII:R Ladder runeword found, which is likely allowed in SP. Start: {}", patch);
        }

        for (Map.Entry<String, Integer> requiredRune: runes.entrySet()) {
            if (availableRunes.getOrDefault(requiredRune.getKey(), 0) < requiredRune.getValue()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Get the name of this Runeword.
     * @return the name of this {@link Runeword}
     */
    public String getName() {
        return name;
    }
}
