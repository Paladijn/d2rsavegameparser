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
 * Representation of stats from the misc.txt resource file.
 *
 * @author Paladijn
 */
public final class MiscStats {
    private final String name;

    private final String type;

    private final String type2;

    private final String code;

    private final boolean isStackable;

    private int reqStr;

    private int reqDex;

    private final int reqLvl;

    private final int invWidth;

    private final int invHeight;

    private int maxStacks;

    /**
     * Constructor which parses a tab-separated line from misc.txt
     * @param line a tab-separated line which contains the fields we need for this item representation
     */
    public MiscStats(final String line) {
        final String[] blocks = line.split("\t");
        name = blocks[0];
        type = blocks[32];
        type2 = blocks[33];
        code = blocks[15];
        invWidth = Integer.parseInt(blocks[19]);
        invHeight = Integer.parseInt(blocks[20]);

        if (ParseHelper.isNumeric(blocks[6])) {
            reqStr = Integer.parseInt(blocks[6]);
        }

        if (ParseHelper.isNumeric(blocks[7])) {
            reqDex = Integer.parseInt(blocks[7]);
        }

        reqLvl = Integer.parseInt(blocks[5]);
        isStackable = blocks[43].equals("1");
        if (isStackable && ParseHelper.isNumeric(blocks[45])) {
            maxStacks = Integer.parseInt(blocks[45]);
        }
    }

    /**
     * Gets the code of the item.
     *
     * @return The code of the item.
     */
    public String getCode() {
        return code;
    }

    /**
     * Checks if the item is stackable.
     *
     * @return True if the item is stackable, false otherwise.
     */
    public boolean isStackable() {
        return isStackable;
    }

    /**
     * Gets the name of the item.
     *
     * @return The name of the item.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the type of the item.
     *
     * @return The type of the item.
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the second type of the item.
     *
     * @return The second type of the item.
     */
    public String getType2() {
        return type2;
    }

    /**
     * Gets the strength requirement for the item.
     *
     * @return The strength requirement for the item.
     */
    public int getReqStr() {
        return reqStr;
    }

    /**
     * Gets the dexterity requirement for the item.
     *
     * @return The dexterity requirement for the item.
     */
    public int getReqDex() {
        return reqDex;
    }

    /**
     * Gets the level requirement for the item.
     *
     * @return The level requirement for the item.
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

    /**
     * if the item {#isStackable} this will contain the max amount of stacks available for the item
     * @return the max amount of stacks available for the item
     */
    public int getMaxStacks() {
        return maxStacks;
    }
}
