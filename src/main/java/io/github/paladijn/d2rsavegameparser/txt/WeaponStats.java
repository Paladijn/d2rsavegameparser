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
 * Representation of an item from the weapons.txt resource file.
 *
 * @author Paladijn
 */
public final class WeaponStats {
    private final String name;

    private final String type;

    private final String type2;

    private final String code;

    private final boolean isTwoHanded;

    private final boolean isStackable;

    private final boolean isThrown;

    private int reqStr;

    private int reqDex;

    private final int reqLvl;

    private final int invWidth;

    private final int invHeight;

    private int maxStacks;

    private final boolean questDiffCheck;

    /**
     * Constructor which parses a tab-separated line from weapons.txt
     * @param line a tab-separated line which contains the fields we need for this item representation
     */
    public WeaponStats(final String line) {
        final String[] blocks = line.split("\t");
        name = blocks[0];
        type = blocks[1];
        type2 = blocks[2];
        code = blocks[3];

        isTwoHanded = blocks[18].equals("1");

        invWidth = Integer.parseInt(blocks[45]);
        invHeight = Integer.parseInt(blocks[46]);

        if (ParseHelper.isNumeric(blocks[27])) {
            reqStr = Integer.parseInt(blocks[27]);
        }

        if (ParseHelper.isNumeric(blocks[28])) {
            reqDex = Integer.parseInt(blocks[28]);
        }

        reqLvl = Integer.parseInt(blocks[31]);
        isStackable = blocks[47].equals("1");
        if (isStackable && ParseHelper.isNumeric(blocks[49])) {
            maxStacks = Integer.parseInt(blocks[49]);
        }
        isThrown = "primarily thrown".equals(blocks[58]);
        questDiffCheck = blocks[70].equals("1");
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
     * Checks if the item is thrown.
     *
     * @return True if the item is thrown, false otherwise.
     */
    public boolean isThrown() {
        return isThrown;
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
     * if the item {#isStackable} this will contain the max amount of stacks available for the item
     * @return the max amount of stacks available for the item
     */
    public int getMaxStacks() {
        return maxStacks;
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
     * Checks if the item requires two hands.
     *
     * @return True if the item requires two hands, false otherwise.
     */
    public boolean isTwoHanded() {
        return isTwoHanded;
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
