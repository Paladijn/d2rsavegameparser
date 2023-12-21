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
 * Representation of a set item from the setitems.txt resource file.
 *
 * @author Paladijn
 */
public final class SetItem {
    private String name;
    private int id;
    private String setName;
    private String code;
    private String itemName;

    /**
     * Constructor which parses a tab-separated line from uniqueitems.txt
     * @param line a tab-separated line which contains the fields we need for this item representation
     */
    public SetItem(final String line) {
        String[] blocks = line.split("\t");
        name = blocks[0];
        id = Integer.parseInt(blocks[1]);
        setName = blocks[2];
        code = blocks[3];
        itemName = blocks[4];
    }

    /**
     * Get the name of the set item.
     * @return the name of the set item
     */
    public String getName() {
        return name;
    }

    /**
     * Get the name of the set.
     * @return the name of the set
     */
    public String getSetName() {
        return setName;
    }

    /**
     * Get the id of the set item.
     * @return the id of the set item
     */
    public int getId() {
        return id;
    }

    /**
     * Get the code of the set item.
     * @return the code of the set item
     */
    public String getCode() {
        return code;
    }

    /**
     * Get the itemtype (name) of the set item.
     * @return the itemtype (name) of the set item
     */
    public String getItemName() {
        return itemName;
    }
}
