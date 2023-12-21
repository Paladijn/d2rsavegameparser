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
 * All the Diablo characters main attributes. These include both the main stats as well as experience, level and the gold in inventory and stash.
 *
 * @author Paladijn
 */
public record CharacterAttributes(int strength, int energy, int dexterity, int vitality, int statPointsLeft, int skillPointsLeft,
                                  long hp, long maxHP, long mana, long maxMana, long stamina, long maxStamina, int level,
                                  long experience, long gold, long goldInStash) {

    /**
     * Builder class for constructing instances of {@link CharacterAttributes}.
     * Allows for fluent construction of character attributes with various customization options.
     */
    public static final class CharacterAttributesBuilder {
        private int strength;
        private int energy;
        private int dexterity;
        private int vitality;
        private int statPointsLeft;
        private int skillPointsLeft;
        private long hp;
        private long maxHP;
        private long mana;
        private long maxMana;
        private long stamina;
        private long maxStamina;
        private int level;
        private long experience;
        private long gold;
        private long goldInStash;

        /**
         * Empty constructor, initialising all values on 0.
         */
        public CharacterAttributesBuilder() {

        }

        /**
         * Load the character with initial main stats
         *
         * @param strength  The Strength for this character
         * @param dexterity The Dexterity for this character
         * @param vitality  The Vitality for this character
         * @param energy    The Energy for this character
         * @param hp        The current and max hitpoints for this character
         * @param mana      The current and max mana for this character
         * @param stamina   The current and max stamina for this character
         */
        public CharacterAttributesBuilder(int strength, int dexterity, int vitality, int energy, int hp, long mana, long stamina) {
            this.strength = strength;
            this.energy = energy;
            this.dexterity = dexterity;
            this.vitality = vitality;
            this.hp = this.maxHP = hp;
            this.mana = this.maxMana = mana;
            this.stamina = this.maxStamina = stamina;
        }

        /**
         * Set the Strength attribute for the character being built.
         *
         * @param strength The Strength value to set for the character.
         * @return The builder instance for method chaining.
         */
        public CharacterAttributesBuilder strength(int strength) {
            this.strength = strength;
            return this;
        }

        /**
         * Set the Energy attribute for the character being built.
         *
         * @param energy The Energy value to set for the character.
         * @return The builder instance for method chaining.
         */
        public CharacterAttributesBuilder energy(int energy) {
            this.energy = energy;
            return this;
        }

        /**
         * Set the Dexterity attribute for the character being built.
         *
         * @param dexterity The Dexterity value to set for the character.
         * @return The builder instance for method chaining.
         */
        public CharacterAttributesBuilder dexterity(int dexterity) {
            this.dexterity = dexterity;
            return this;
        }

        /**
         * Set the Vitality attribute for the character being built.
         *
         * @param vitality The Vitality value to set for the character.
         * @return The builder instance for method chaining.
         */
        public CharacterAttributesBuilder vitality(int vitality) {
            this.vitality = vitality;
            return this;
        }

        /**
         * Set the remaining stat points for the character being built.
         *
         * @param statPointsLeft The remaining stat points for the character.
         * @return The builder instance for method chaining.
         */
        public CharacterAttributesBuilder statPointsLeft(int statPointsLeft) {
            this.statPointsLeft = statPointsLeft;
            return this;
        }

        /**
         * Set the remaining skill points for the character being built.
         *
         * @param skillPointsLeft The remaining skill points for the character.
         * @return The builder instance for method chaining.
         */
        public CharacterAttributesBuilder skillPointsLeft(int skillPointsLeft) {
            this.skillPointsLeft = skillPointsLeft;
            return this;
        }

        /**
         * Set the current hitpoints for the character being built.
         *
         * @param hp The current hitpoints value to set for the character.
         * @return The builder instance for method chaining.
         */
        public CharacterAttributesBuilder hp(long hp) {
            this.hp = hp;
            return this;
        }

        /**
         * Set the maximum hitpoints for the character being built.
         *
         * @param maxHP The maximum hitpoints value to set for the character.
         * @return The builder instance for method chaining.
         */
        public CharacterAttributesBuilder maxHP(long maxHP) {
            this.maxHP = maxHP;
            return this;
        }

        /**
         * Set the current mana points for the character being built.
         *
         * @param mana The current mana value to set for the character.
         * @return The builder instance for method chaining.
         */
        public CharacterAttributesBuilder mana(long mana) {
            this.mana = mana;
            return this;
        }

        /**
         * Set the maximum mana points for the character being built.
         *
         * @param maxMana The maximum mana value to set for the character.
         * @return The builder instance for method chaining.
         */
        public CharacterAttributesBuilder maxMana(long maxMana) {
            this.maxMana = maxMana;
            return this;
        }

        /**
         * Set the current stamina points for the character being built.
         *
         * @param stamina The current stamina value to set for the character.
         * @return The builder instance for method chaining.
         */
        public CharacterAttributesBuilder stamina(long stamina) {
            this.stamina = stamina;
            return this;
        }

        /**
         * Set the maximum stamina points for the character being built.
         *
         * @param maxStamina The maximum stamina value to set for the character.
         * @return The builder instance for method chaining.
         */
        public CharacterAttributesBuilder maxStamina(long maxStamina) {
            this.maxStamina = maxStamina;
            return this;
        }

        /**
         * Set the level of the character.
         *
         * @param level The level of the character.
         * @return The builder instance for method chaining.
         */
        public CharacterAttributesBuilder level(int level) {
            this.level = level;
            return this;
        }

        /**
         * Set the experience points of the character.
         *
         * @param experience The experience points of the character.
         * @return The builder instance for method chaining.
         */
        public CharacterAttributesBuilder experience(long experience) {
            this.experience = experience;
            return this;
        }

        /**
         * Set the gold of the character.
         *
         * @param gold The gold of the character.
         * @return The builder instance for method chaining.
         */
        public CharacterAttributesBuilder gold(long gold) {
            this.gold = gold;
            return this;
        }

        /**
         * Set the gold in stash of the character.
         *
         * @param goldInStash The gold in stash of the character.
         * @return The builder instance for method chaining.
         */
        public CharacterAttributesBuilder goldInStash(long goldInStash) {
            this.goldInStash = goldInStash;
            return this;
        }

        /**
         * Build the {@link CharacterAttributes} instance with the configured values.
         *
         * @return The constructed {@link CharacterAttributes} instance.
         */
        public CharacterAttributes build() {
            return new CharacterAttributes(strength, energy, dexterity, vitality, statPointsLeft, skillPointsLeft, hp, maxHP,
                    mana, maxMana, stamina, maxStamina, level, experience, gold, goldInStash);
        }
    }
}
