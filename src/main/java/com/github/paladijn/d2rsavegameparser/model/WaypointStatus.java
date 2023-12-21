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
 * Status of all waypoints in a specific {@link Difficulty}. The fields return true if they're unlocked, false if they still need to be found.
 *
 * @author Paladijn
 */
public record WaypointStatus(
    boolean act1RogueEncampment, boolean act1ColdPlains, boolean act1StonyField, boolean act1DarkWood, boolean act1BlackMarsh, boolean act1OuterCloister, boolean act1Jail, boolean act1InnerCloister, boolean act1Catacombs,
    boolean act2LutGholein, boolean act2Sewers, boolean act2DryHills, boolean act2HallsOfTheDead, boolean act2FarOasis, boolean act2LostCity, boolean act2PalaceCellar, boolean act2ArcaneSanctuary, boolean act2CanyonOfTheMagi,
    boolean act3KurastDocks, boolean act3SpiderForest, boolean act3GreatMarsh, boolean act3FlayerJungle, boolean act3LowerKurast, boolean act3KurastBazaar, boolean act3UpperKurast, boolean act3Travincal, boolean act3DuranceOfHate,
    boolean act4PandemoniumFortress, boolean act4CityOfTheDamned, boolean act4RiverOfFlames,
    boolean act5Harrogath, boolean act5FrigidHighlands, boolean act5ArreatPlateau, boolean act5CrystallinePassage, boolean act5HallsOfPain, boolean act5GlacialTrail, boolean act5FrozenTundra, boolean act5TheAncientsWay, boolean act5WorldstoneKeep
) {

    /**
     * Builder class for creating instances of the {@link WaypointStatus} class.
     * Allows for fluent construction of WaypointStatus objects with various waypoint statuses for each act.
     */
    public static final class WaypointStatusBuilder {
        private boolean act1RogueEncampment;
        private boolean act1ColdPlains;
        private boolean act1StonyField;
        private boolean act1DarkWood;
        private boolean act1BlackMarsh;
        private boolean act1OuterCloister;
        private boolean act1Jail;
        private boolean act1InnerCloister;
        private boolean act1Catacombs;

        private boolean act2LutGholein;
        private boolean act2Sewers;
        private boolean act2DryHills;
        private boolean act2HallsOfTheDead;
        private boolean act2FarOasis;
        private boolean act2LostCity;
        private boolean act2PalaceCellar;
        private boolean act2ArcaneSanctuary;
        private boolean act2CanyonOfTheMagi;

        private boolean act3KurastDocks;
        private boolean act3SpiderForest;
        private boolean act3GreatMarsh;
        private boolean act3FlayerJungle;
        private boolean act3LowerKurast;
        private boolean act3KurastBazaar;
        private boolean act3UpperKurast;
        private boolean act3Travincal;
        private boolean act3DuranceOfHate;

        private boolean act4PandemoniumFortress;
        private boolean act4CityOfTheDamned;
        private boolean act4RiverOfFlames;

        private boolean act5Harrogath;
        private boolean act5FrigidHighlands;
        private boolean act5ArreatPlateau;
        private boolean act5CrystallinePassage;
        private boolean act5HallsOfPain;
        private boolean act5GlacialTrail;
        private boolean act5FrozenTundra;
        private boolean act5TheAncientsWay;
        private boolean act5WorldstoneKeep;

        /**
         * Sets the status of the Act 1 Rogue Encampment waypoint.
         *
         * @param found True if the waypoint is found, false otherwise.
         * @return The current WaypointStatusBuilder instance.
         */
        public WaypointStatusBuilder act1RogueEncampment(boolean found) {
            this.act1RogueEncampment = found;
            return this;
        }

        /**
         * Sets the status of the Act 1 Cold Plains waypoint.
         *
         * @param found True if the waypoint is found, false otherwise.
         * @return The current WaypointStatusBuilder instance.
         */
        public WaypointStatusBuilder act1ColdPlains(boolean found) {
            this.act1ColdPlains = found;
            return this;
        }

        /**
         * Sets the status of the Act 1 Stony Field waypoint.
         *
         * @param found True if the waypoint is found, false otherwise.
         * @return The current WaypointStatusBuilder instance.
         */
        public WaypointStatusBuilder act1StonyField(boolean found) {
            this.act1StonyField = found;
            return this;
        }

        /**
         * Sets the status of the Act 1 Dark Wood waypoint.
         *
         * @param found True if the waypoint is found, false otherwise.
         * @return The current WaypointStatusBuilder instance.
         */
        public WaypointStatusBuilder act1DarkWood(boolean found) {
            this.act1DarkWood = found;
            return this;
        }

        /**
         * Sets the status of the Act 1 Black Marsh waypoint.
         *
         * @param found True if the waypoint is found, false otherwise.
         * @return The current WaypointStatusBuilder instance.
         */
        public WaypointStatusBuilder act1BlackMarsh(boolean found) {
            this.act1BlackMarsh = found;
            return this;
        }

        /**
         * Sets the status of the Act 1 Outer Cloister waypoint.
         *
         * @param found True if the waypoint is found, false otherwise.
         * @return The current WaypointStatusBuilder instance.
         */
        public WaypointStatusBuilder act1OuterCloister(boolean found) {
            this.act1OuterCloister = found;
            return this;
        }

        /**
         * Sets the status of the Act 1 Jail waypoint.
         *
         * @param found True if the waypoint is found, false otherwise.
         * @return The current WaypointStatusBuilder instance.
         */
        public WaypointStatusBuilder act1Jail(boolean found) {
            this.act1Jail = found;
            return this;
        }

        /**
         * Sets the status of the Act 1 Inner Cloister waypoint.
         *
         * @param found True if the waypoint is found, false otherwise.
         * @return The current WaypointStatusBuilder instance.
         */
        public WaypointStatusBuilder act1InnerCloister(boolean found) {
            this.act1InnerCloister = found;
            return this;
        }

        /**
         * Sets the status of the Act 1 Catacombs waypoint.
         *
         * @param found True if the waypoint is found, false otherwise.
         * @return The current WaypointStatusBuilder instance.
         */
        public WaypointStatusBuilder act1Catacombs(boolean found) {
            this.act1Catacombs = found;
            return this;
        }

        /**
         * Sets the status of the Act 2 Lut Gholein waypoint.
         *
         * @param found True if the waypoint is found, false otherwise.
         * @return The current WaypointStatusBuilder instance.
         */
        public WaypointStatusBuilder act2LutGholein(boolean found) {
            this.act2LutGholein = found;
            return this;
        }

        /**
         * Sets the status of the Act 2 Sewers waypoint.
         *
         * @param found True if the waypoint is found, false otherwise.
         * @return The current WaypointStatusBuilder instance.
         */
        public WaypointStatusBuilder act2Sewers(boolean found) {
            this.act2Sewers = found;
            return this;
        }

        /**
         * Sets the status of the Act 2 Dry Hills waypoint.
         *
         * @param found True if the waypoint is found, false otherwise.
         * @return The current WaypointStatusBuilder instance.
         */
        public WaypointStatusBuilder act2DryHills(boolean found) {
            this.act2DryHills = found;
            return this;
        }

        /**
         * Sets the status of the Act 2 Halls of the Dead waypoint.
         *
         * @param found True if the waypoint is found, false otherwise.
         * @return The current WaypointStatusBuilder instance.
         */
        public WaypointStatusBuilder act2HallsOfTheDead(boolean found) {
            this.act2HallsOfTheDead = found;
            return this;
        }

        /**
         * Sets the status of the Act 2 Far Oasis waypoint.
         *
         * @param found True if the waypoint is found, false otherwise.
         * @return The current WaypointStatusBuilder instance.
         */
        public WaypointStatusBuilder act2FarOasis(boolean found) {
            this.act2FarOasis = found;
            return this;
        }

        /**
         * Sets the status of the Act 2 Lost City waypoint.
         *
         * @param found True if the waypoint is found, false otherwise.
         * @return The current WaypointStatusBuilder instance.
         */
        public WaypointStatusBuilder act2LostCity(boolean found) {
            this.act2LostCity = found;
            return this;
        }

        /**
         * Sets the status of the Act 2 Palace Cellar waypoint.
         *
         * @param found True if the waypoint is found, false otherwise.
         * @return The current WaypointStatusBuilder instance.
         */
        public WaypointStatusBuilder act2PalaceCellar(boolean found) {
            this.act2PalaceCellar = found;
            return this;
        }

        /**
         * Sets the status of the Act 2 Arcane Sanctuary waypoint.
         *
         * @param found True if the waypoint is found, false otherwise.
         * @return The current WaypointStatusBuilder instance.
         */
        public WaypointStatusBuilder act2ArcaneSanctuary(boolean found) {
            this.act2ArcaneSanctuary = found;
            return this;
        }

        /**
         * Sets the status of the Act 2 Canyon of the Magi waypoint.
         *
         * @param found True if the waypoint is found, false otherwise.
         * @return The current WaypointStatusBuilder instance.
         */
        public WaypointStatusBuilder act2CanyonOfTheMagi(boolean found) {
            this.act2CanyonOfTheMagi = found;
            return this;
        }

        /**
         * Sets the status of the Act 3 Kurast Docks waypoint.
         *
         * @param found True if the waypoint is found, false otherwise.
         * @return The current WaypointStatusBuilder instance.
         */
        public WaypointStatusBuilder act3KurastDocks(boolean found) {
            this.act3KurastDocks = found;
            return this;
        }

        /**
         * Sets the status of the Act 3 Spider Forest waypoint.
         *
         * @param found True if the waypoint is found, false otherwise.
         * @return The current WaypointStatusBuilder instance.
         */
        public WaypointStatusBuilder act3SpiderForest(boolean found) {
            this.act3SpiderForest = found;
            return this;
        }

        /**
         * Sets the status of the Act 3 Great Marsh waypoint.
         *
         * @param found True if the waypoint is found, false otherwise.
         * @return The current WaypointStatusBuilder instance.
         */
        public WaypointStatusBuilder act3GreatMarsh(boolean found) {
            this.act3GreatMarsh = found;
            return this;
        }

        /**
         * Sets the status of the Act 3 Flayer Jungle waypoint.
         *
         * @param found True if the waypoint is found, false otherwise.
         * @return The current WaypointStatusBuilder instance.
         */
        public WaypointStatusBuilder act3FlayerJungle(boolean found) {
            this.act3FlayerJungle = found;
            return this;
        }

        /**
         * Sets the status of the Act 3 Lower Kurast waypoint.
         *
         * @param found True if the waypoint is found, false otherwise.
         * @return The current WaypointStatusBuilder instance.
         */
        public WaypointStatusBuilder act3LowerKurast(boolean found) {
            this.act3LowerKurast = found;
            return this;
        }

        /**
         * Sets the status of the Act 3 Kurast Bazaar waypoint.
         *
         * @param found True if the waypoint is found, false otherwise.
         * @return The current WaypointStatusBuilder instance.
         */
        public WaypointStatusBuilder act3KurastBazaar(boolean found) {
            this.act3KurastBazaar = found;
            return this;
        }

        /**
         * Sets the status of the Act 3 Upper Kurast waypoint.
         *
         * @param found True if the waypoint is found, false otherwise.
         * @return The current WaypointStatusBuilder instance.
         */
        public WaypointStatusBuilder act3UpperKurast(boolean found) {
            this.act3UpperKurast = found;
            return this;
        }

        /**
         * Sets the status of the Act 3 Travincal waypoint.
         *
         * @param found True if the waypoint is found, false otherwise.
         * @return The current WaypointStatusBuilder instance.
         */
        public WaypointStatusBuilder act3Travincal(boolean found) {
            this.act3Travincal = found;
            return this;
        }

        /**
         * Sets the status of the Act 3 Durance of Hate waypoint.
         *
         * @param found True if the waypoint is found, false otherwise.
         * @return The current WaypointStatusBuilder instance.
         */
        public WaypointStatusBuilder act3DuranceOfHate(boolean found) {
            this.act3DuranceOfHate = found;
            return this;
        }

        /**
         * Sets the status of the Act 4 Pandemonium Fortress waypoint.
         *
         * @param found True if the waypoint is found, false otherwise.
         * @return The current WaypointStatusBuilder instance.
         */
        public WaypointStatusBuilder act4PandemoniumFortress(boolean found) {
            this.act4PandemoniumFortress = found;
            return this;
        }

        /**
         * Sets the status of the Act 4 City of the Damned waypoint.
         *
         * @param found True if the waypoint is found, false otherwise.
         * @return The current WaypointStatusBuilder instance.
         */
        public WaypointStatusBuilder act4CityOfTheDamned(boolean found) {
            this.act4CityOfTheDamned = found;
            return this;
        }

        /**
         * Sets the status of the Act 4 River of Flames waypoint.
         *
         * @param found True if the waypoint is found, false otherwise.
         * @return The current WaypointStatusBuilder instance.
         */
        public WaypointStatusBuilder act4RiverOfFlames(boolean found) {
            this.act4RiverOfFlames = found;
            return this;
        }

        /**
         * Sets the waypoint status for Act 5 - Harrogath.
         *
         * @param found The waypoint status for Act 5 - Harrogath.
         * @return This {@code WaypointStatusBuilder} instance for method chaining.
         */
        public WaypointStatusBuilder act5Harrogath(boolean found) {
            this.act5Harrogath = found;
            return this;
        }

        /**
         * Sets the waypoint status for Act 5 - Frigid Highlands.
         *
         * @param found The waypoint status for Act 5 - Frigid Highlands.
         * @return This {@code WaypointStatusBuilder} instance for method chaining.
         */
        public WaypointStatusBuilder act5FrigidHighlands(boolean found) {
            this.act5FrigidHighlands = found;
            return this;
        }

        /**
         * Sets the waypoint status for Act 5 - Arreat Plateau.
         *
         * @param found The waypoint status for Act 5 - Arreat Plateau.
         * @return This {@code WaypointStatusBuilder} instance for method chaining.
         */
        public WaypointStatusBuilder act5ArreatPlateau(boolean found) {
            this.act5ArreatPlateau = found;
            return this;
        }

        /**
         * Sets the waypoint status for Act 5 - Crystalline Passage.
         *
         * @param found The waypoint status for Act 5 - Crystalline Passage.
         * @return This {@code WaypointStatusBuilder} instance for method chaining.
         */
        public WaypointStatusBuilder act5CrystallinePassage(boolean found) {
            this.act5CrystallinePassage = found;
            return this;
        }

        /**
         * Sets the waypoint status for Act 5 - Halls of Pain.
         *
         * @param found The waypoint status for Act 5 - Halls of Pain.
         * @return This {@code WaypointStatusBuilder} instance for method chaining.
         */
        public WaypointStatusBuilder act5HallsOfPain(boolean found) {
            this.act5HallsOfPain = found;
            return this;
        }

        /**
         * Sets the waypoint status for Act 5 - Glacial Trail.
         *
         * @param found The waypoint status for Act 5 - Glacial Trail.
         * @return This {@code WaypointStatusBuilder} instance for method chaining.
         */
        public WaypointStatusBuilder act5GlacialTrail(boolean found) {
            this.act5GlacialTrail = found;
            return this;
        }

        /**
         * Sets the waypoint status for Act 5 - Frozen Tundra.
         *
         * @param found The waypoint status for Act 5 - Frozen Tundra.
         * @return This {@code WaypointStatusBuilder} instance for method chaining.
         */
        public WaypointStatusBuilder act5FrozenTundra(boolean found) {
            this.act5FrozenTundra = found;
            return this;
        }

        /**
         * Sets the waypoint status for Act 5 - The Ancients' Way.
         *
         * @param found The waypoint status for Act 5 - The Ancients' Way.
         * @return This {@code WaypointStatusBuilder} instance for method chaining.
         */
        public WaypointStatusBuilder act5TheAncientsWay(boolean found) {
            this.act5TheAncientsWay = found;
            return this;
        }

        /**
         * Sets the waypoint status for Act 5 - Worldstone Keep.
         *
         * @param found The waypoint status for Act 5 - Worldstone Keep.
         * @return This {@code WaypointStatusBuilder} instance for method chaining.
         */
        public WaypointStatusBuilder act5WorldstoneKeep(boolean found) {
            this.act5WorldstoneKeep = found;
            return this;
        }

        /**
         * Builds and returns a new instance of {@link WaypointStatus} with the waypoint statuses for all acts.
         *
         * @return A new {@code WaypointStatus} instance.
         */
        public WaypointStatus build() {
            return new WaypointStatus(
                    act1RogueEncampment, act1ColdPlains, act1StonyField, act1DarkWood, act1BlackMarsh, act1OuterCloister, act1Jail, act1InnerCloister, act1Catacombs,
                    act2LutGholein, act2Sewers, act2DryHills, act2HallsOfTheDead, act2FarOasis, act2LostCity, act2PalaceCellar, act2ArcaneSanctuary, act2CanyonOfTheMagi,
                    act3KurastDocks, act3SpiderForest, act3GreatMarsh, act3FlayerJungle, act3LowerKurast, act3KurastBazaar, act3UpperKurast, act3Travincal, act3DuranceOfHate,
                    act4PandemoniumFortress, act4CityOfTheDamned, act4RiverOfFlames,
                    act5Harrogath, act5FrigidHighlands, act5ArreatPlateau, act5CrystallinePassage, act5HallsOfPain, act5GlacialTrail, act5FrozenTundra, act5TheAncientsWay, act5WorldstoneKeep
            );
        }
    }
}
