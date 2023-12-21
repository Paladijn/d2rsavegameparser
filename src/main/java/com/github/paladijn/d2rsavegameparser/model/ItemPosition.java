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

/**
 * The position of an equipped item.
 *
 * @author Paladijn
 */
public enum ItemPosition {
    /**
     * No specific position or unknown position.
     */
    NONE(0),

    /**
     * Head position.
     */
    HEAD(1),

    /**
     * Neck position.
     */
    NECK(2),

    /**
     * Torso position.
     */
    TORSO(3),

    /**
     * Right hand position.
     */
    RIGHT_HAND(4),

    /**
     * Left hand position.
     */
    LEFT_HAND(5),

    /**
     * Right finger position.
     */
    RIGHT_FINGER(6),

    /**
     * Left finger position.
     */
    LEFT_FINGER(7),

    /**
     * Waist position.
     */
    WAIST(8),

    /**
     * Feet position.
     */
    FEET(9),

    /**
     * Gloves position.
     */
    GLOVES(10),

    /**
     * Right swap position.
     */
    RIGHT_SWAP(11),

    /**
     * Left swap position.
     */
    LEFT_SWAP(12),

    /**
     * Unknown position.
     */
    UNKNOWN(-99);

    private final int bitValue;

    ItemPosition(int value) {
        this.bitValue = value;
    }

    /**
     * Retrieve the container an item is stored in by the {@link #bitValue}
     * @param value the value to match the itemContainers {@link #bitValue}
     * @return the matching {@link ItemPosition}
     */
    public static ItemPosition findByValue(int value) {
        return Arrays.stream(values()).filter(it -> it.bitValue == value).findAny().orElse(UNKNOWN);
    }
}
