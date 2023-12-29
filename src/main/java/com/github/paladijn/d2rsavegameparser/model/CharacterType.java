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
package com.github.paladijn.d2rsavegameparser.model;

import com.github.paladijn.d2rsavegameparser.parser.ParseException;

/**
 * One of the seven Diablo II character types.
 *
 * @author Paladijn
 */
public enum CharacterType {
    /**
     * Amazon
     */
    AMAZON("Amazon"),
    /**
     * Sorceress
     */
    SORCERESS("Sorceress"),
    /**
     * Necromancer
     */
    NECROMANCER("Necromancer"),
    /**
     * Paladin -- why is this not on #1?
     */
    PALADIN("Paladin"),
    /**
     * Barbarian
     */
    BARBARIAN("Barbarian"),
    /**
     * Druid
     */
    DRUID("Druid"),
    /**
     * Assassin
     */
    ASSASSIN("Assassin"),
    /**
     * None
     */
    NONE("-");

    private final String displayString;

    CharacterType(String displayString) {
        this.displayString = displayString;
    }

    /**
     * @return the display name for this character type
     */
    public final String getDisplayName() {
        return displayString;
    }

    /**
     * Parse the Character type by their short representation String.
     * Will throw {@link ParseException} in case the supplied String did not result in a {@link CharacterType}
     *
     * @param shortString three letter representation of the character type
     * @return One of the seven {@link CharacterType}s, or null if none could be parsed.
     */
    public static CharacterType parseByShortString(String shortString) {
        return switch (shortString) {
            case "ama" -> AMAZON;
            case "ass" -> ASSASSIN;
            case "bar" -> BARBARIAN;
            case "dru" -> DRUID;
            case "nec" -> NECROMANCER;
            case "pal" -> PALADIN;
            case "sor" -> SORCERESS;
            default -> throw new ParseException("Could not determine CharacterType from String " + shortString);
        };
    }
}
