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
package io.github.paladijn.d2rsavegameparser.txt;

import io.github.paladijn.d2rsavegameparser.internal.parser.ParseHelper;

/**
 * Representation of an item from the armor.txt resource file.
 *
 * @author Paladijn
 */
public final class ArmorStats {
    private final String name;
    private final String type;
    private final String type2;
    private final String code;
    private int reqStr;
    private int reqDex;
    private final int reqLvl;
    private final int invWidth;
    private final int invHeight;
    private final boolean questDiffCheck;

    /**
     * Constructor which parses a tab-separated line from armor.txt
     * @param line a tab-separated line which contains the fields we need for this item representation
     */
    public ArmorStats(final String line) {
        final String[] blocks = line.split("\t");
        name = blocks[0];
        type = blocks[52];
        type2 = blocks[53];
        code = blocks[19];

        invWidth = Integer.parseInt(blocks[28]);
        invHeight = Integer.parseInt(blocks[29]);

        if (ParseHelper.isNumeric(blocks[9])) {
            reqStr = Integer.parseInt(blocks[9]);
        }

        if (ParseHelper.isNumeric(blocks[10])) {
            reqDex = Integer.parseInt(blocks[10]);
        }

        reqLvl = Integer.parseInt(blocks[16]);

        questDiffCheck = "1".equals(blocks[64]); // this is currently always false, but in case we get an expansion with armour quest items it'll work out of the box.
    }

    /**
     * Get the code of the armor.
     *
     * @return the code of the armor
     */
    public String getCode() {
        return code;
    }

    /**
     * Get the name of the armor.
     *
     * @return the name of the armor
     */
    public String getName() {
        return name;
    }

    /**
     * Get the primary type of the armor.
     *
     * @return the primary type of the armor
     */
    public String getType() {
        return type;
    }

    /**
     * Get the secondary type of the armor.
     *
     * @return the secondary type of the armor
     */
    public String getType2() {
        return type2;
    }

    /**
     * Get the strength requirement of the armor.
     *
     * @return the strength requirement of the armor
     */
    public int getReqStr() {
        return reqStr;
    }

    /**
     * Get the dexterity requirement of the armor.
     *
     * @return the dexterity requirement of the armor
     */
    public int getReqDex() {
        return reqDex;
    }

    /**
     * Get the level requirement of the armor.
     *
     * @return the level requirement of the armor
     */
    public int getReqLvl() {
        return reqLvl;
    }

    /**
     * the inventory width this item uses
     *
     * @return the inventory width of this item.
     */
    public int getInvWidth() {
        return invWidth;
    }

    /**
     * the inventory height this item uses
     *
     * @return the inventory height of this item.
     */
    public int getInvHeight() {
        return invHeight;
    }

    public boolean isQuestDiffCheck() {
        return questDiffCheck;
    }
}
