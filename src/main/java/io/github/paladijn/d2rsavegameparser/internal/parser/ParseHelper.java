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
package io.github.paladijn.d2rsavegameparser.internal.parser;

public final class ParseHelper {
    public static final int PROPERTY_PHYS_MAX_DMG = 17;
    public static final int PROPERTY_FIRE_MIN_DMG = 48;
    public static final int PROPERTY_LIGHT_MIN_DMG = 50;
    public static final int PROPERTY_MAGIC_MIN_DMG = 52;
    public static final int PROPERTY_COLD_MIN_DMG = 54;
    public static final int PROPERTY_POISON_MIN_DMG = 57;
    public static final int PROPERTY_UNDEAD_DMG = 122;
    public static final int PROPERTY_SKILL_ATTACK = 195;
    public static final int PROPERTY_SKILL_KILL = 196;
    public static final int PROPERTY_SKILL_DEATH = 197;
    public static final int PROPERTY_SKILL_HIT = 198;
    public static final int PROPERTY_SKILL_LEVEL_UP = 199;
    public static final int PROPERTY_SKILL_GET_HIT = 201;
    public static final int PROPERTY_CHARGED_SKILL = 204;
    public static final int PROPERTY_END = 511;

    private ParseHelper() {
        // Helper class, don't instantiate me.
    }

    public static boolean isNumeric(final String input) {
        return input != null && input.matches("[-+]?\\d+(\\.\\d+)?");
    }
}
