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

/**
 * Indicator which of the three txt definitions should be used for an item:
 * <ul>
 *  <li>armor.txt</li>
 *  <li>weapons.txt</li>
 *  <li>misc.txt</li>
 * </ul>
 */
public enum ItemType {
    /**
     * Use the armor.txt for data
     */
    ARMOR,
    /**
     * Use the weapons.txt for data
     */
    WEAPON,
    /**
     * Use the misc.txt for data
     */
    MISC
}
