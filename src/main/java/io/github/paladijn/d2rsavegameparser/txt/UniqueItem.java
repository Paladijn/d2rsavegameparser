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

/**
 * Representation of a unique item from the uniqueitems.txt resource file.
 *
 * @author Paladijn
 */
public final class UniqueItem {
    private final String name;
    private final short id;
    private final boolean enabled;
    private final String ladderFirst;
    private final String ladderLast;
    private final String code;
    private final String itemName;
    private final int reqLvl;

    /**
     * Constructor which parses a tab-separated line from uniqueitems.txt
     * @param line a tab-separated line which contains the fields we need for this item representation
     */
    public UniqueItem(final String line) {
        final String[] blocks = line.split("\t");
        name = blocks[0];
        id = Short.parseShort(blocks[1]);
        ladderFirst = blocks[4];
        ladderLast = blocks[5];
        reqLvl = "".equals(blocks[9]) ? 0 : Integer.parseInt(blocks[9]); // some of the not enabled unique items have no requirement set.
        code = blocks[10];
        itemName = blocks[11];
        enabled = "1".equals(blocks[3]) || "2".equals(ladderFirst); // the sunder charms are also enabled in SP. Perhaps this should be a check for non-empty?
    }

    /**
     * Returns whether the unique item is enabled.
     *
     * @return {@code true} if the unique item is enabled, {@code false} otherwise.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Retrieves the ID of the unique item.
     *
     * @return The ID of the unique item.
     */
    public short getId() {
        return id;
    }

    /**
     * Retrieves the name of the unique item.
     *
     * @return The name of the unique item.
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the item name of the unique item.
     *
     * @return The item name of the unique item.
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Retrieves the ladder first attribute of the unique item.
     *
     * @return The ladder first attribute of the unique item.
     */
    public String getLadderFirst() {
        return ladderFirst;
    }

    /**
     * Retrieves the ladder last attribute of the unique item.
     *
     * @return The ladder last attribute of the unique item.
     */
    public String getLadderLast() {
        return ladderLast;
    }

    /**
     * Retrieves the code attribute of the unique item.
     *
     * @return The code attribute of the unique item.
     */
    public String getCode() {
        return code;
    }

    /**
     * Retrieves the required minimum character level to equip the unique item.
     *
     * @return The required level to equip the unique item.
     */
    public int getReqLvl() {
        return reqLvl;
    }

    /**
     * Returns a string representation of the unique item.
     *
     * @return A string representation of the unique item.
     */
    @Override
    public String toString() {
        return "UniqueItem{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", enabled=" + enabled +
                ", ladderFirst='" + ladderFirst + '\'' +
                ", ladderLast='" + ladderLast + '\'' +
                ", code='" + code + '\'' +
                ", itemName='" + itemName + '\'' +
                ", reqLvl='" + reqLvl + '\'' +
                '}';
    }
}
