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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Representation of a Diablo II character. This contains all the data retrieved from a savegame file and can be used to display or calculate statistics (needed for the resistance scroll for rescuing Anya).
 * It also contains all the items on the character itself and in its private stash.
 *
 * @author Paladijn
 */

public record D2Character(FileData fileData, String name, boolean hardcore, boolean died, boolean lordOfDestruction, boolean riseOfTheWarlock, byte actProgression,
                         CharacterType characterType, byte level, List<Location> locations, long mapId, List<QuestData> questDataPerDifficulty,
                         List<WaypointStatus> waypoints, Mercenary mercenary, CharacterAttributes attributes, List<Item> items,
                         List<Item> deadBodyItems, Item golemItem, List<Skill> skills, List<ItemProperty> equippedSetBenefits) {

    private static final Logger log = LoggerFactory.getLogger(D2Character.class);

    /**
     * Builder class for constructing instances of {@link D2Character}.
     * Allows for fluent construction of character data.
     */
    public static final class D2CharacterBuilder {
        private FileData fileData;

        private String name;

        private boolean hardcore;

        private boolean died;

        private boolean lordOfDestruction;

        private boolean riseOfTheWarlock;

        private byte actProgression;

        private CharacterType characterType;

        private byte level;

        private List<Location> locations = new ArrayList<>();

        private long mapId;

        private List<QuestData> questDataPerDifficulty = List.of(
                new QuestData.QuestDataBuilder(Difficulty.NORMAL).build(),
                new QuestData.QuestDataBuilder(Difficulty.NIGHTMARE).build(),
                new QuestData.QuestDataBuilder(Difficulty.HELL).build()
        );

        private List<WaypointStatus> waypoints = new ArrayList<>();

        private Mercenary mercenary;

        private CharacterAttributes attributes;

        private List<Skill> skills = new ArrayList<>();

        private List<Item> items = new ArrayList<>();

        private List<Item> deadBodyItems = new ArrayList<>();

        private Item golemItem;

        private List<ItemProperty> equippedSetBenefits = new ArrayList<>();

        /**
         * Set the file data for the Diablo II character being built.
         *
         * @param fileData The {@link FileData} to set for the character.
         * @return The builder instance for method chaining.
         */
        public D2CharacterBuilder fileData(FileData fileData) {
            this.fileData = fileData;
            return this;
        }

        /**
         * Set the name for the Diablo II character being built.
         *
         * @param name The name to set for the character.
         * @return The builder instance for method chaining.
         */
        public D2CharacterBuilder name(String name) {
            this.name = name;
            return this;
        }

        /**
         * Set the act progression for the Diablo II character being built.
         *
         * @param actProgression A byte representing the act progression for the character.
         * @return The builder instance for method chaining.
         */
        public D2CharacterBuilder actProgression(byte actProgression) {
            this.actProgression = actProgression;
            return this;
        }

        /**
         * Set the character type for the Diablo II character being built.
         *
         * @param characterType The {@link CharacterType} to set for the character.
         * @return The builder instance for method chaining.
         */
        public D2CharacterBuilder characterType(CharacterType characterType) {
            this.characterType = characterType;
            return this;
        }

        /**
         * Set the level for the Diablo II character being built.
         *
         * @param level The level of the character.
         * @return The builder instance for method chaining.
         */
        public D2CharacterBuilder level(byte level) {
            this.level = level;
            return this;
        }

        /**
         * Set the locations for the Diablo II character being built.
         *
         * @param locations The {@link Location}s to set for the character.
         * @return The builder instance for method chaining.
         */
        public D2CharacterBuilder locations(List<Location> locations) {
            this.locations = locations;
            return this;
        }

        public D2CharacterBuilder mapId(long mapId) {
            this.mapId = mapId;
            return this;
        }

        /**
         * Set the quest data per difficulty for the Diablo II character being built. The length is three where each index is Normal, Nightmare and Hell.
         *
         * @param questDataPerDifficulty The {@link QuestData} per difficulty to set for the character.
         * @return The builder instance for method chaining.
         */
        public D2CharacterBuilder questDataPerDifficulty(List<QuestData> questDataPerDifficulty) {
            this.questDataPerDifficulty = questDataPerDifficulty;
            return this;
        }

        /**
         * Set the waypoints for the Diablo II character being built.
         *
         * @param waypoints The list of {@link WaypointStatus} to set for the character.
         * @return The builder instance for method chaining.
         */
        public D2CharacterBuilder waypoints(List<WaypointStatus> waypoints) {
            this.waypoints = waypoints;
            return this;
        }

        /**
         * Set the mercenary information for the Diablo II character being built.
         *
         * @param mercenary The {@link Mercenary} to set for the character.
         * @return The builder instance for method chaining.
         */
        public D2CharacterBuilder mercenary(Mercenary mercenary) {
            this.mercenary = mercenary;
            return this;
        }

        /**
         * Set the attributes for the Diablo II character being built.
         *
         * @param attributes The {@link CharacterAttributes} to set for the character.
         * @return The builder instance for method chaining.
         */
        public D2CharacterBuilder attributes(CharacterAttributes attributes) {
            this.attributes = attributes;
            return this;
        }

        /**
         * Set the skills for the Diablo II character being built.
         *
         * @param skills a list of {@link Skill}s belonging to this character.
         * @return The builder instance for method chaining.
         */
        public D2CharacterBuilder skills(List<Skill> skills) {
            this.skills = skills;
            return this;
        }

        /**
         * Set the items for the Diablo II character being built.
         *
         * @param items The {@link Item}s to set for the character.
         * @return The builder instance for method chaining.
         */
        public D2CharacterBuilder items(List<Item> items) {
            this.items = items;
            return this;
        }

        /**
         * Set the dead body items for the Diablo II character being built.
         *
         * @param deadBodyItems The dead body {@link Item}s to set for the character.
         * @return The builder instance for method chaining.
         */
        public D2CharacterBuilder deadBodyItems(List<Item> deadBodyItems) {
            this.deadBodyItems = deadBodyItems;
            return this;
        }

        /**
         * Set the golem item for the Diablo II character being built.
         *
         * @param golemItem The {@link Item} used to construct the Iron Golem.
         * @return The builder instance for method chaining.
         */
        public D2CharacterBuilder golemItem(Item golemItem) {
            this.golemItem = golemItem;
            return this;
        }

        /**
         * Set the equipped set benefits for the Diablo II character being built.
         *
         * @param equippedSetBenefits The equipped set benefits as a list of {@link ItemProperty} to set for the character.
         * @return The builder instance for method chaining.
         */
        public D2CharacterBuilder equippedSetBenefits(List<ItemProperty> equippedSetBenefits) {
            this.equippedSetBenefits = equippedSetBenefits;
            return this;
        }

        public D2CharacterBuilder riseOfTheWarlock(byte value) {
            this.riseOfTheWarlock = (value == 3);
            return this;
        }

        /**
         * parse the status bits for this character. This fills the state for hardcore and expansion characters as wel as an indication of the character died.
         *
         * @param statusBits a byte value containing the three bits needed to determine the hardcore, expansion and (previous) death indicators.
         * @return The builder instance for method chaining.
         */
        public D2CharacterBuilder parseCharacterStatus(final byte statusBits) {
            hardcore = (0xff & statusBits & 1 << 2) != 0;
            died = (0xff & statusBits & 1 << 3) != 0;
            lordOfDestruction = (0xff & statusBits & 1 << 5) != 0;
            return this;
        }

        /**
         * Indication whether this is a Lord of Destruction expansion character. Classic characters don't have an iron golem or mercenary items stored in the savegame.
         *
         * @return true if this is a Lord of Destruction expansion character, false if it's classic or Rise of the Warlock.
         */
        public boolean isLordOfDestruction() {
            return lordOfDestruction;
        }

        public boolean isRiseOfTheWarlock() {
            return riseOfTheWarlock;
        }

        /**
         * Build the {@link D2Character} instance with the configured values.
         *
         * @return The constructed {@link D2Character} instance.
         */
        public D2Character build() {
            return new D2Character(fileData, name, hardcore, died, lordOfDestruction, riseOfTheWarlock, actProgression, characterType, level,
                    List.copyOf(locations), mapId, List.copyOf(questDataPerDifficulty),
                    List.copyOf(waypoints), mercenary, attributes, List.copyOf(items),
                    List.copyOf(deadBodyItems), golemItem, List.copyOf(skills),
                    List.copyOf(equippedSetBenefits));
        }
    }
}
