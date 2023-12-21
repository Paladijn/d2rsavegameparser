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

import java.util.Arrays;

/** The container an item is stored in when it is not equipped.
 *
 * @author Paladijn
 */
public enum ItemContainer {

    /**
     * No specific container.
     */
    NONE(0),

    /**
     * Inventory container.
     */
    INVENTORY(1),

    /**
     * Horadric Cube container.
     */
    HORADRIC_CUBE(4),

    /**
     * Stash container.
     */
    STASH(5),

    /**
     * Unknown container type.
     */
    UNKNOWN(-99);

    private final int bitValue;

    ItemContainer(int bitValue) {
        this.bitValue = bitValue;
    }

    /**
     * Retrieve the container an item is stored in by the {@link #bitValue}
     * @param value the value to match the itemContainers {@link #bitValue}
     * @return the matching {@link ItemContainer}
     */
    public static ItemContainer findByValue(int value) {
        return Arrays.stream(values()).filter(it -> it.bitValue == value).findAny().orElse(UNKNOWN);
    }
}
