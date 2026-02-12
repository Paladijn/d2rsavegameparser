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
 * Representation of a set item from the setitems.txt resource file.
 *
 * @author Paladijn
 */
public final class SetItem {
    private final String name;
    private final int id;
    private final String setName;
    private final boolean cannotLoot;
    private final String code;
    private final String itemName;

    /**
     * Constructor which parses a tab-separated line from uniqueitems.txt
     * @param line a tab-separated line which contains the fields we need for this item representation
     */
    public SetItem(final String line) {
        String[] blocks = line.split("\t");
        name = blocks[0];
        id = Integer.parseInt(blocks[1]);
        setName = blocks[2];
        cannotLoot = "1".equals(blocks[3]); // Oddly enough this is only set to 1 for the "Warlord's Glory" items, and "" instead of 0 for the other ones.
        code = blocks[10];
        itemName = blocks[11];
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

    /**
     * The set item cannot be looted - actually meaning it cannot drop. This was added in 1.7.90471 for the Chinese-only "Warlord's Glory" set.
     * @return true if the item cannot be looted (or dropped?) in this version of the game
     */
    public boolean cannotLoot() {
        return cannotLoot;
    }
}
