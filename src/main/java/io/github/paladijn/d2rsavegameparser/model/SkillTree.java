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

/**
 * Enum representing different skill trees in the game.
 */
public enum SkillTree {

    /**
     * Skill tree for Bow and Crossbow skills (Amazon Only).
     */
    BOW(0, CharacterType.AMAZON, "SkillCategoryAm3", "StrSklTabItem3"),

    /**
     * Skill tree for Passive and Magic skills (Amazon Only).
     */
    PASSIVE(1, CharacterType.AMAZON, "SkillCategoryAm2", "StrSklTabItem2"),

    /**
     * Skill tree for Javelin and Spear skills (Amazon Only).
     */
    JAVELIN(2, CharacterType.AMAZON, "SkillCategoryAm1", "StrSklTabItem1"),

    /**
     * Skill tree for Fire skills (Sorceress Only).
     */
    FIRE(8, CharacterType.SORCERESS, "SkillCategorySo3", "StrSklTabItem15"),

    /**
     * Skill tree for Lightning skills (Sorceress Only).
     */
    LIGHTNING(9, CharacterType.SORCERESS, "SkillCategorySo2", "StrSklTabItem14"),

    /**
     * Skill tree for Cold skills (Sorceress Only).
     */
    COLD(10, CharacterType.SORCERESS, "SkillCategorySo1", "StrSklTabItem13"),

    /**
     * Skill tree for Curses (Necromancer Only).
     */
    CURSES(16, CharacterType.NECROMANCER, "SkillCategoryNe3", "StrSklTabItem8"),

    /**
     * Skill tree for Poison and Bone skills (Necromancer Only).
     */
    POISON_BONE(17, CharacterType.NECROMANCER, "SkillCategoryNe2", "StrSklTabItem7"),

    /**
     * Skill tree for Summoning skills (Necromancer Only).
     */
    SUMMON(18, CharacterType.NECROMANCER, "SkillCategoryNe1", "StrSklTabItem16"),

    /**
     * Skill tree for Combat skills (Paladin Only).
     */
    COMBAT(24, CharacterType.PALADIN, "SkillCategoryPa3", "StrSklTabItem6"),

    /**
     * Skill tree for Offensive Aura skills (Paladin Only).
     */
    OFFENSIVE(25, CharacterType.PALADIN, "SkillCategoryPa2", "StrSklTabItem5"),

    /**
     * Skill tree for Defensive Aura skills (Paladin Only).
     */
    DEFENSIVE(26, CharacterType.PALADIN, "SkillCategoryPa1", "StrSklTabItem4"),

    /**
     * Skill tree for Combat skills (Barbarian Only).
     */
    COMBAT_BARB(32, CharacterType.BARBARIAN, "SkillCategoryBa3", "StrSklTabItem11"),

    /**
     * Skill tree for Masteries skills (Barbarian Only).
     */
    MASTERIES(33, CharacterType.BARBARIAN, "SkillCategoryBa2", "StrSklTabItem12"),

    /**
     * Skill tree for Warcry skills (Barbarian Only).
     */
    SHOUT(34, CharacterType.BARBARIAN, "SkillCategoryBa1", "StrSklTabItem10"),

    /**
     * Skill tree for Summoning skills (Druid Only).
     */
    SUMMON_DRUID(40, CharacterType.DRUID, "SkillCategoryDr3", "StrSklTabItem16"),

    /**
     * Skill tree for Shape-Shifting skills (Druid Only).
     */
    SHAPESHIFT(41, CharacterType.DRUID, "SkillCategoryDr2", "StrSklTabItem17"),

    /**
     * Skill tree for Elemental skills (Druid Only).
     */
    ELEMENTAL(42, CharacterType.DRUID, "SkillCategoryDr1", "StrSklTabItem18"),

    /**
     * Skill tree for Trap skills (Assassin Only).
     */
    TRAPS(48, CharacterType.ASSASSIN, "SkillCategoryAs3", "StrSklTabItem19"),

    /**
     * Skill tree for Shadow Discipline skills (Assassin Only).
     */
    SHADOW(49, CharacterType.ASSASSIN, "SkillCategoryAs2", "StrSklTabItem20"),

    /**
     * Skill tree for Martial Art skills (Assassin Only).
     */
    MARTIAL(50, CharacterType.ASSASSIN, "SkillCategoryAs1", "StrSklTabItem21"),

    DEMON(51, CharacterType.WARLOCK, "SkillCategoryW1", "StrSklTabItem22"), // don't know the keys yet

    ELDRITCH(52, CharacterType.WARLOCK, "SkillCategoryW2", "StrSklTabItem23"), // don't know the keys yet

    CHAOS(53, CharacterType.WARLOCK, "SkillCategoryW3", "StrSklTabItem24"), // don't know the keys yet

    /**
     * Unknown skill tree.
     */
    UNKNOWN(999, CharacterType.NONE, "Unknown skilltree (or not linked to any character type)", "unknown skill tab");

    private final int index;

    private final CharacterType characterType;

    private final String descriptionKey;

    private final String itemSkillTabKey;

    SkillTree(int index, CharacterType characterType, String descriptionKey, String itemSkillTabKey) {
        this.index = index;
        this.characterType = characterType;
        this.descriptionKey = descriptionKey;
        this.itemSkillTabKey = itemSkillTabKey;
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
     * @return The translatin key for the skill tree.
     */
    public final String getDescriptionKey() {
        return descriptionKey;
    }

    /**
     * Gets the translation key for the skill tab, StrSklTabItem from item-modifiers.json
     *
     * @return the translation key for the skill tab
     */
    public final String getItemSkillTabKey() {
        return itemSkillTabKey;
    }
}
