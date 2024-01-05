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
 * Representation of achieved QuestData for a character. At the moment only some quests are tracked, as the focus is to calculate stats or find characters with beneficial quests.
 * <p>
 * Supported quests:
 *  A5.0: Siege on Harrogath -> socketQuestStarted
 *  A5.0: Siege on Harrogath bit 1 and 5 -> socketQuestRewardAvailable
 *  A5.0: Siege on Harrogath bit 0 and 5 -> socketQuestUsed
 *  A5.2: Prison of ice bit 7 -> resistanceScrollRead
 * </p>
 *
 * @author Paladijn
 */
public record QuestData(Difficulty difficulty, boolean socketQuestStarted, boolean socketQuestRewardAvailable, boolean socketQuestUsed, boolean resistanceScrollRead) {

    /**
     * Builder class for creating instances of the {@link QuestData} class.
     * Allows for fluent construction of QuestData objects with various properties.
     */
    public static final class QuestDataBuilder {

        /**
         * The difficulty level for which the quest data is being built.
         */
        private Difficulty difficulty;

        /**
         * A flag indicating whether the socket quest has been started in the specified difficulty.
         */
        private boolean socketQuestStarted;

        /**
         * A flag indicating whether the socket quest reward is available in the specified difficulty.
         */
        private boolean socketQuestRewardAvailable;

        /**
         * A flag indicating whether the socket quest reward has been used in the specified difficulty.
         */
        private boolean socketQuestUsed;

        /**
         * A flag indicating whether the resistance scroll has been read in the specified difficulty.
         */
        private boolean resistanceScrollRead;

        /**
         * Initialize the QuestDataBuilder for a specific difficulty.
         *
         * @param difficulty The Diablo II {@link Difficulty} of this QuestData.
         */
        public QuestDataBuilder(Difficulty difficulty) {
            this.difficulty = difficulty;
        }

        /**
         * Sets the status of the socket quest started in the specified difficulty.
         *
         * @param started True if the socket quest has been started, false otherwise.
         * @return The current QuestDataBuilder instance.
         */
        public QuestDataBuilder socketQuestStarted(boolean started) {
            this.socketQuestStarted = started;
            return this;
        }

        /**
         * Sets the status of the socket quest reward in the specified difficulty.
         *
         * @param available True if the socket quest is available, false otherwise.
         * @return The current QuestDataBuilder instance.
         */
        public QuestDataBuilder socketQuestRewardAvailable(boolean available) {
            this.socketQuestRewardAvailable = available;
            return this;
        }

        /**
         * Sets the status of the socket quest reward to used in the specified difficulty.
         *
         * @param used True if the socket quest reward has been used, false otherwise.
         * @return The current QuestDataBuilder instance.
         */
        public QuestDataBuilder socketQuestUsed(boolean used) {
            this.socketQuestUsed = used;
            return this;
        }

        /**
         * Sets the status of the resistance scroll being read in the specified difficulty.
         *
         * @param read True if the resistance scroll has been read, false otherwise.
         * @return The current QuestDataBuilder instance.
         */
        public QuestDataBuilder resistanceScrollRead(boolean read) {
            this.resistanceScrollRead = read;
            return this;
        }

        /**
         * Builds and returns a new instance of the {@link QuestData} class
         * with the specified properties.
         *
         * @return A new QuestData instance.
         */
        public QuestData build() {
            return new QuestData(difficulty, socketQuestStarted, socketQuestRewardAvailable, socketQuestUsed, resistanceScrollRead);
        }
    }
}
