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
package com.github.paladijn.d2rsavegameparser.internal.parser;

import com.github.paladijn.d2rsavegameparser.model.Attribute;
import com.github.paladijn.d2rsavegameparser.model.CharacterAttributes;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.BitSet;
import java.util.List;

/**
 * Parser specific for the {@link CharacterAttributes} in the savegame.
 * This block of data is not fixed-width as attributes that do not exist are completely left out.
 *
 */
public final class AttributeParser {
    private static final int ID_BINARY_SIZE = 9;
    private static final int STOP_CODE = 0x01FF;

    private static final List<Attribute> ATTRIBUTES = List.of(
            new Attribute("Strength", 10, 1),
            new Attribute("Energy", 10, 1),
            new Attribute("Dexterity", 10, 1),
            new Attribute("Vitality", 10, 1),
            new Attribute("Stat points", 10, 1),
            new Attribute("Skill points", 8, 1),
            new Attribute("Hit points", 21, 255),
            new Attribute("Max hit points", 21, 255),
            new Attribute("Mana", 21, 255),
            new Attribute("Max mana", 21, 255),
            new Attribute("Stamina points", 21, 255),
            new Attribute("Max stamina points", 21, 255),
            new Attribute("Level", 7, 1),
            new Attribute("Experience", 32, 1),
            new Attribute("Gold", 25, 1),
            new Attribute("Gold in stash", 25, 1)
    );

    /**
     * Parse the {@link CharacterAttributes} from the supplied bytes. These start at index 765 with identifier "gf". These are not fixed-length as statistics that don't exist (points left, gold at 0, etc.) are not stored.
     * @param statBytes the bytes containing the character statistics
     * @return the parsed {@link CharacterAttributes}
     */
    public CharacterAttributes parse(final byte[] statBytes) {
        CharacterAttributes.CharacterAttributesBuilder builder = new CharacterAttributes.CharacterAttributesBuilder();
        final BitSet bits = BitSet.valueOf(statBytes);

        int currentPositionInBitSequence = 0;
        while (true) {
            int fromIndex = currentPositionInBitSequence;
            int toIndex = fromIndex + ID_BINARY_SIZE;

            int attributeId = bitsAsInt(bits.get(fromIndex, toIndex));
            if (attributeId == STOP_CODE) {
                break;
            }

            Attribute attribute = ATTRIBUTES.get(attributeId);
            fromIndex = toIndex;
            toIndex = fromIndex + attribute.numberOfBits();
            long attributeValue = bitsAsLong(bits.get(fromIndex, toIndex)) / attribute.coefficient();

            builder = switch (attribute.name()) {
                case "Strength" -> builder.strength(Math.toIntExact(attributeValue));
                case "Energy" -> builder.energy(Math.toIntExact(attributeValue));
                case "Dexterity" -> builder.dexterity(Math.toIntExact(attributeValue));
                case "Vitality" -> builder.vitality(Math.toIntExact(attributeValue));
                case "Stat points" -> builder.statPointsLeft(Math.toIntExact(attributeValue));
                case "Skill points" -> builder.skillPointsLeft(Math.toIntExact(attributeValue));
                case "Hit points" -> builder.hp(attributeValue);
                case "Max hit points" -> builder.maxHP(attributeValue);
                case "Mana" -> builder.mana(attributeValue);
                case "Max mana" -> builder.maxMana(attributeValue);
                case "Stamina points" -> builder.stamina(attributeValue);
                case "Max stamina points" -> builder.maxStamina(attributeValue);
                case "Level" -> builder.level(Math.toIntExact(attributeValue));
                case "Experience" -> builder.experience(attributeValue);
                case "Gold" -> builder.gold(attributeValue);
                case "Gold in stash" -> builder.goldInStash(attributeValue);
                default -> builder;
            };

            currentPositionInBitSequence = toIndex;
        }
        return builder.build();
    }

   private int bitsAsInt(BitSet bitSet) {
        return ByteBuffer.allocate(Integer.BYTES)
                .order(ByteOrder.LITTLE_ENDIAN)
                .put(bitSet.toByteArray())
                .rewind()
                .getInt();
    }

    private long bitsAsLong(BitSet bitSet) {
        return ByteBuffer.allocate(Long.BYTES)
                .order(ByteOrder.LITTLE_ENDIAN)
                .put(bitSet.toByteArray())
                .rewind()
                .getLong();
    }
}
