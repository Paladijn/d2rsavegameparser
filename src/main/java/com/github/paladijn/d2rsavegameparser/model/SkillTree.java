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

import static com.github.paladijn.d2rsavegameparser.model.CharacterType.AMAZON;
import static com.github.paladijn.d2rsavegameparser.model.CharacterType.ASSASSIN;
import static com.github.paladijn.d2rsavegameparser.model.CharacterType.BARBARIAN;
import static com.github.paladijn.d2rsavegameparser.model.CharacterType.DRUID;
import static com.github.paladijn.d2rsavegameparser.model.CharacterType.NECROMANCER;
import static com.github.paladijn.d2rsavegameparser.model.CharacterType.PALADIN;
import static com.github.paladijn.d2rsavegameparser.model.CharacterType.SORCERESS;

/**
 * Enum representing different skill trees in the game.
 */
public enum SkillTree {

    /**
     * Skill tree for Bow and Crossbow skills (Amazon Only).
     */
    BOW(0, AMAZON, "Bow and Crossbow Skills (Amazon Only)"),

    /**
     * Skill tree for Passive and Magic skills (Amazon Only).
     */
    PASSIVE(1, AMAZON, "Passive and Magic Skills (Amazon Only)"),

    /**
     * Skill tree for Javelin and Spear skills (Amazon Only).
     */
    JAVELIN(2, AMAZON, "Javelin and Spear Skills (Amazon Only)"),

    /**
     * Skill tree for Fire skills (Sorceress Only).
     */
    FIRE(8, SORCERESS, "Fire Skills (Sorceress Only)"),

    /**
     * Skill tree for Lightning skills (Sorceress Only).
     */
    LIGHTNING(9, SORCERESS, "Lightning Skills (Sorceress Only)"),

    /**
     * Skill tree for Cold skills (Sorceress Only).
     */
    COLD(10, SORCERESS, "Cold Skills (Sorceress Only)"),

    /**
     * Skill tree for Curses (Necromancer Only).
     */
    CURSES(16, NECROMANCER, "Curses (Necromancer only)"),

    /**
     * Skill tree for Poison and Bone skills (Necromancer Only).
     */
    POISON_BONE(17, NECROMANCER, "Poison and Bone Skills (Necromancer Only)"),

    /**
     * Skill tree for Summoning skills (Necromancer Only).
     */
    SUMMON(18, NECROMANCER, "Summoning Skills (Necromancer Only)"),

    /**
     * Skill tree for Combat skills (Paladin Only).
     */
    COMBAT(24, PALADIN, "Combat Skills (Paladin Only)"),

    /**
     * Skill tree for Offensive Aura skills (Paladin Only).
     */
    OFFENSIVE(25, PALADIN, "Offensive Aura Skills (Paladin Only)"),

    /**
     * Skill tree for Defensive Aura skills (Paladin Only).
     */
    DEFENSIVE(26, PALADIN, "Defensive Aura Skills (Paladin Only)"),

    /**
     * Skill tree for Combat skills (Barbarian Only).
     */
    COMBAT_BARB(32, BARBARIAN, "Combat Skills (Barbarian Only)"),

    /**
     * Skill tree for Masteries skills (Barbarian Only).
     */
    MASTERIES(33, BARBARIAN, "Masteries Skills (Barbarian Only)"),

    /**
     * Skill tree for Warcry skills (Barbarian Only).
     */
    SHOUT(34, BARBARIAN, "Warcry Skills (Barbarian Only)"),

    /**
     * Skill tree for Summoning skills (Druid Only).
     */
    SUMMON_DRUID(40, DRUID, "Summoning Skills (Druid Only)"),

    /**
     * Skill tree for Shape-Shifting skills (Druid Only).
     */
    SHAPESHIFT(41, DRUID, "Shape-Shifting Skills (Druid Only)"),

    /**
     * Skill tree for Elemental skills (Druid Only).
     */
    ELEMENTAL(42, DRUID, "Elemental Skills (Druid Only)"),

    /**
     * Skill tree for Trap skills (Assassin Only).
     */
    TRAPS(48, ASSASSIN, "Trap Skills (Assassin Only)"),

    /**
     * Skill tree for Shadow Discipline skills (Assassin Only).
     */
    SHADOW(49, ASSASSIN, "Shadow Discipline Skills (Assassin Only)"),

    /**
     * Skill tree for Martial Art skills (Assassin Only).
     */
    MARTIAL(50, ASSASSIN, "Martial Art Skills (Assassin Only)"),

    /**
     * Unknown skill tree.
     */
    UNKNOWN(999, null, "Unknown skilltree");

    private final int index;

    private final CharacterType characterType;

    private final String description;

    SkillTree(int index, CharacterType characterType, String description) {
        this.index = index;
        this.characterType = characterType;
        this.description = description;
    }

    /**
     * Get the {@link SkillTree} by index id
     * @param id The id of the {@link SkillTree} you're looking for
     * @return The {@link SkillTree} element if available, or {@link SkillTree#UNKNOWN} if not found
     */
    public static SkillTree findSkillTreeById(int id) {
        for(SkillTree skillTree: SkillTree.values()) {
            if (skillTree.index == id) {
                return skillTree;
            }
        }
        return UNKNOWN;
    }

    /**
     * Gets the index ID of the skill tree.
     *
     * @return The index ID of the skill tree.
     */
    public final int getIndex() {
        return index;
    }

    /**
     * Gets the character class associated with the skill tree.
     *
     * @return The character class associated with the skill tree.
     */
    public final CharacterType getCharacterType() {
        return characterType;
    }

    /**
     * Gets the description of the skill tree.
     *
     * @return The description of the skill tree.
     */
    public final String getDescription() {
        return description;
    }
}
