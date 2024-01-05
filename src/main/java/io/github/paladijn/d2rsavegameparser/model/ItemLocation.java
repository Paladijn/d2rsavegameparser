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
package io.github.paladijn.d2rsavegameparser.model;

import java.util.Arrays;

/**
 * The location of the item on the character display. Check {@link ItemLocation} or {@link ItemPosition} for the specific location.
 * @author Paladijn
 */
public enum ItemLocation {
    /** The item is stored in either a stash, cube or inventory which is indicated by the items {@link ItemLocation}*/
    STORED(0),
    /** The item is equipped, the exact location is specified by {@link ItemPosition} */
    EQUIPPED(1),
    /** one of the items in a belt, can be potions, but also scrolls */
    BELT(2),
    /** item is held at the cursor, usually when exiting the game after grabbing it */
    TRANSIT(4),
    /** the items is socketed inside another item */
    SOCKET(6),
    /** unknown location, likely an error while parsing */
    UNKNOWN(-99);

    private final int bitValue;

    ItemLocation(int value) {
        this.bitValue = value;
    }

    /**
     * Retrieve the container an item is stored in by the {@link #bitValue}
     * @param value the value to match the itemContainers {@link #bitValue}
     * @return the matching {@link ItemLocation}
     */
    public static ItemLocation findByValue(int value) {
        return Arrays.stream(values()).filter(it -> it.bitValue == value).findAny().orElse(UNKNOWN);
    }
}
