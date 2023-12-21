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

import com.github.paladijn.d2rsavegameparser.model.CharacterType;

/**
 * Representation of a skill from the skills.txt resource file.
 *
 * @author Paladijn
 */
public final class Skill {
    private final int id;
    private final String name;
    private CharacterType characterType;

    /**
     * Constructor which parses a tab-separated line from skills.txt
     * @param line a tab-separated line which contains the fields we need for this item representation
     */
    public Skill(String line) {
        final String[] blocks = line.split("\t");

        id = Integer.parseInt(blocks[1]);
        name = blocks[0];
        if (!blocks[2].isBlank()) {
            characterType = CharacterType.parseByShortString(blocks[2]);
        }
    }

    /**
     * Get the id of the skill.
     * @return The id of the skill
     */
    public int getId() {
        return id;
    }

    /**
     * Get the name of the skill.
     * @return The name of the skill
     */
    public String getName() {
        return name;
    }

    /**
     * Get the {@link CharacterType} the skill belongs to.
     * @return The {@link CharacterType} the skill belongs to.
     */
    public CharacterType getCharacterType() {
        return characterType;
    }
}
