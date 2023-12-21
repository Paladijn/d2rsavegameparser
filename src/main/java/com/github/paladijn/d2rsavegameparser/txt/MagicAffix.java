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

import com.github.paladijn.d2rsavegameparser.model.CharacterType;

import static com.github.paladijn.d2rsavegameparser.internal.parser.ParseHelper.isNumeric;

/**
 * Representation of the magical prefixes and suffixes from magicprefix.txt and magicsuffix.txt.
 *
 * @author Paladijn
 */
public final class MagicAffix {
    private final int index;

    private final String name;

    private int reqLvl;

    private CharacterType restrictedToClass;

    /**
     * Constructor which parses a tab-separated line from magicprefix.txt and magicsuffix.txt. If applicable a minimum required level and restriction to {@link CharacterType} are set.
     * @param index index of this item. Not used at the moment.
     * @param line a tab-separated line which contains the fields we need for this item representation
     */
    public MagicAffix(final int index, final String line) {
        this.index = index;
        String[] blocks = line.split("\t");

        name = blocks[0];
        if (blocks.length == 1) {
            return;
        }

        if (!blocks[7].isBlank()) {
            restrictedToClass = CharacterType.parseByShortString(blocks[7]);
        }

        if (isNumeric(blocks[6])) {
            reqLvl = Integer.parseInt(blocks[6]);
        }
    }

    /**
     * Get the index value of the affix.
     * @return the index value of the affix
     */
    public int getIndex() {
        return index;
    }

    /**
     * Get the required minimum level of the affix.
     * @return the required minimum level of the affix
     */
    public int getReqLvl() {
        return reqLvl;
    }

    /**
     * Get the name of the affix.
     * @return the name of the affix
     */
    public Object getName() {
        return name;
    }

    /**
     * Get the required character type of the affix.
     * @return the required {@link CharacterType} of the affix
     */
    public CharacterType getRestrictedToClass() {
        return restrictedToClass;
    }
}
