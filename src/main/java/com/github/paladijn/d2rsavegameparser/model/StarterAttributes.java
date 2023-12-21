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
 * Helper class to supply starter {@link CharacterAttributes} for Diablo II characters. These are used in case a new character file is present without any existing stats.
 * It usually takes five minutes for the saveGame to be updated resulting in empty or wrong values for the display.
 *
 * @author Paladijn
 */
public interface StarterAttributes {
    /**
     * Rerieve starter {@link CharacterAttributes} for the specific {@link CharacterType}
     *
     * @param characterType The {@link CharacterType} to filter
     * @return starter {@link CharacterAttributes} for the specific {@link CharacterType}
     */
    static CharacterAttributes getStarterAttributesByClass(CharacterType characterType) {
        return switch (characterType) {
            case AMAZON -> new CharacterAttributes.CharacterAttributesBuilder(20, 25, 20, 15, 50, 15, 84).build();
            case BARBARIAN -> new CharacterAttributes.CharacterAttributesBuilder(30, 20, 25, 10, 55, 10, 92).build();
            case NECROMANCER -> new CharacterAttributes.CharacterAttributesBuilder(15, 25, 15, 25, 45, 25, 79).build();
            case PALADIN -> new CharacterAttributes.CharacterAttributesBuilder(25, 20, 25, 15, 55, 15, 89).build();
            case SORCERESS -> new CharacterAttributes.CharacterAttributesBuilder(10, 25, 10, 35, 40, 30, 74).build();
            case ASSASSIN -> new CharacterAttributes.CharacterAttributesBuilder(20, 20, 20, 25, 50, 25, 95).build();
            case DRUID -> new CharacterAttributes.CharacterAttributesBuilder(15, 20, 25, 20, 55, 20, 84).build();
        };
    }
}
