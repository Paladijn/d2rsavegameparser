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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representation of the items and gold in a shared stash tab.
 *
 * @author Paladijn
 */

public record SharedStashTab (int version, int gold, int lengthInBytes, List<Item> items) {

    /**
     * Builder class for creating instances of the {@link SharedStashTab} class.
     * Allows for fluent construction of SharedStashTab objects with various properties.
     */
    public static final class SharedStashTabBuilder {

        /**
         * The version of the shared stash tab.
         */
        private int version;

        /**
         * The amount of gold in the shared stash tab.
         */
        private int gold;

        /**
         * The length of the shared stash tab in bytes.
         */
        private int lengthInBytes;

        /**
         * The list of items stored in the shared stash tab.
         */
        private List<Item> items = new ArrayList<>();

        /**
         * Sets the version of the shared stash tab.
         *
         * @param version The version of the shared stash tab.
         * @return The current SharedStashTabBuilder instance.
         */
        public SharedStashTabBuilder version(int version) {
            this.version = version;
            return this;
        }

        /**
         * Sets the amount of gold in the shared stash tab.
         *
         * @param gold The amount of gold in the shared stash tab.
         * @return The current SharedStashTabBuilder instance.
         */
        public SharedStashTabBuilder gold(int gold) {
            this.gold = gold;
            return this;
        }

        /**
         * Sets the length of the shared stash tab in bytes.
         *
         * @param lengthInBytes The length of the shared stash tab in bytes.
         * @return The current SharedStashTabBuilder instance.
         */
        public SharedStashTabBuilder lengthInBytes(int lengthInBytes) {
            this.lengthInBytes = lengthInBytes;
            return this;
        }

        /**
         * Sets the list of items stored in the shared stash tab.
         *
         * @param items The list of items to be stored in the shared stash tab.
         * @return The current SharedStashTabBuilder instance.
         */
        public SharedStashTabBuilder items(List<Item> items) {
            this.items = items;
            return this;
        }

        /**
         * Builds and returns a new instance of the {@link SharedStashTab} class
         * with the specified properties.
         *
         * @return A new SharedStashTab instance.
         */
        public SharedStashTab build() {
            return new SharedStashTab(version, gold, lengthInBytes, Collections.unmodifiableList(items));
        }
    }
}
