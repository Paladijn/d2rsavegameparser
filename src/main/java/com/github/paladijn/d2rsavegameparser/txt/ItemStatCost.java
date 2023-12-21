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

/**
 * Representation of itemstatcost.txt which is used to retrieve the values of {@link com.github.paladijn.d2rsavegameparser.model.ItemProperty} from a savegame file.
 *
 * @author Paladijn
 */
public final class ItemStatCost {
    private int id;
    private String stat;
    private int saveBits;
    private int saveAdd;
    private int saveParamBits;
    private int descPriority;

    /**
     * Constructor which parses a tab-separated line from itemstatcost.txt
     * @param line a tab-separated line which contains the fields we need for this representation
     */
    public ItemStatCost(final String line) {
        String[] blocks = line.split("\t");
        if (blocks.length == 52) {
            stat = blocks[0];
            id = getInt(blocks[1]);
            saveBits = getInt(blocks[20]);
            saveAdd = getInt(blocks[21]);
            if (saveAdd == -1) {
                saveAdd = 0;
            }
            saveParamBits = getInt(blocks[22]);
            descPriority = getInt(blocks[37]);
        }
    }

    /**
     * ItemStatCost id
     * @return The numerical id of the ItemStatCost field
     */
    public int getId() {
        return id;
    }

    /**
     * A textual description for the ItemStatCost field
     * @return the stat label
     */
    public String getStat() {
        return stat;
    }

    /**
     * Length in bits of the final stored param of the itemStat
     * @return length in bits
     */
    public int getSaveBits() {
        return saveBits;
    }

    /**
     * This is the value added to a saved property value, so for reading you're required to subtract it from the stored value.
     * @return amount to add for saving a property value or subtract for reading a stored property value.
     */
    public int getSaveAdd() {
        return saveAdd;
    }

    /**
     * Length in bits of the first stored param of the itemStat
     * @return length in bits
     */
    public int getSaveParamBits() {
        return saveParamBits;
    }

    /**
     * Sort order, higher is sorted on top
     * @return a number indicating the sort order
     */
    public int getDescPriority() {
        return descPriority;
    }

    private int getInt(String block) {
        try {
            return Integer.parseInt(block);
        } catch (NumberFormatException nfe) {
            return -1;
        }
    }
}
