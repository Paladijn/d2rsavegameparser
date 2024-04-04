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

import java.util.ArrayList;
import java.util.List;

/**
 * Representation of a Mercenary attached to a character. This contains the type, name, its experience and the equipped items (0..3).
 *
 * @param id The internal mercenary id.
 * @param nameId name id of this mercenary, see <a href="http://user.xmission.com/~trevin/DiabloIIv1.09_Mercenaries.html">this link for names</a> or refer to the mercenaries.json from the original data files.
 * @param typeId type id of the mercenary (class + skills), see type see <a href="http://user.xmission.com/~trevin/DiabloIIv1.09_Mercenaries.html#code">this link for values</a>.
 * @param experience current xp of the mercenary.
 * @param items List of {@link Item}s on the mercenary.
 *
 * @author Paladijn
 */
public record Mercenary (int id, short nameId, short typeId, int experience, List<Item> items) {

    /**
     * Builder class for creating instances of {@link Mercenary}.
     * Allows for fluent construction of a Diablo II {@link Mercenary} with various customization options.
     */
    public static final class MercenaryBuilder {
        private int id;
        private short nameId;
        private short typeId;
        private int experience;
        private List<Item> items = new ArrayList<>();

        /**
         * Sets the ID of the mercenary.
         *
         * @param id The ID of the mercenary.
         * @return The current MercenaryBuilder instance.
         */
        public MercenaryBuilder id(int id) {
            this.id = id;
            return this;
        }

        /**
         * Sets the name ID of the mercenary.
         *
         * @param nameId The name ID of the mercenary.
         * @return The current MercenaryBuilder instance.
         */
        public MercenaryBuilder nameId(short nameId) {
            this.nameId = nameId;
            return this;
        }

        /**
         * Sets the type ID of the mercenary.
         *
         * @param typeId The type ID of the mercenary.
         * @return The current MercenaryBuilder instance.
         */
        public MercenaryBuilder typeId(short typeId) {
            this.typeId = typeId;
            return this;
        }

        /**
         * Sets the experience of the mercenary.
         *
         * @param xp The experience points of the mercenary.
         * @return The current MercenaryBuilder instance.
         */
        public MercenaryBuilder experience(int xp) {
            this.experience = xp;
            return this;
        }

        /**
         * Sets the list of items equipped by the mercenary.
         *
         * @param items The list of items equipped by the mercenary.
         * @return The current MercenaryBuilder instance.
         */
        public MercenaryBuilder items(List<Item> items) {
            this.items = items;
            return this;
        }

        /**
         * Builds and returns a new instance of the {@link Mercenary} class
         * with the specified properties.
         *
         * @return A new Mercenary instance.
         */
        public Mercenary build() {
            return new Mercenary(id, nameId, typeId, experience, List.copyOf(items));
        }
    }
}
