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

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

class ItemTest {

    @Test
    void shouldThrowOnMutableListOnPrefix() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Item(false, false, false, false, false,
                    false, false, false, false, 0, (short) 0, (short) 0,"code", "type",
                    "type2", ItemType.MISC, (short) 0, (short) 0, "", "", (short) 1, (short) 1, new ArrayList<>(), List.of(),
                    (short) 0, (short) 0, (short) 0, (short) 0, "itemName", "setName", "", 0, (short) 0,
                    (short) 0, (short) 0, 0, 0, 0, 0, CharacterType.NECROMANCER, List.of(), List.of(), ItemLocation.BELT, ItemQuality.NONE,
                    ItemPosition.GLOVES, ItemContainer.INVENTORY, 0, (short) 0, 0, 0))
                .withMessage("prefixIds should be an immutable list");
    }

    @Test
    void shouldThrowOnMutableListOnSuffix() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Item(false, false, false, false, false,
                    false, false, false, false, 0, (short) 0, (short) 0,"code", "type",
                    "type2", ItemType.MISC, (short) 0, (short) 0, "", "", (short) 1, (short) 1, List.of(), new ArrayList<>(),
                    (short) 0, (short) 0, (short) 0, (short) 0, "itemName", "setName", "", 0, (short) 0,
                    (short) 0, (short) 0, 0, 0, 0, 0, CharacterType.NECROMANCER, List.of(), List.of(), ItemLocation.BELT, ItemQuality.NONE,
                    ItemPosition.GLOVES, ItemContainer.INVENTORY, 0, (short) 0, 0, 0))
                .withMessage("suffixIds should be an immutable list");
    }

    @Test
    void shouldThrowOnMutableListOnProperties() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Item(false, false, false, false, false,
                    false, false, false, false, 0, (short) 0, (short) 0, "code", "type",
                    "type2", ItemType.MISC, (short) 0, (short) 0, "", "", (short) 1, (short) 1, List.of(), List.of(),
                    (short) 0, (short) 0, (short) 0, (short) 0, "itemName", "setName", "", 0, (short) 0,
                    (short) 0, (short) 0, 0, 0, 0, 0, CharacterType.NECROMANCER, new ArrayList<>(), List.of(), ItemLocation.BELT, ItemQuality.NONE,
                    ItemPosition.GLOVES, ItemContainer.INVENTORY, 0, (short) 0, 0, 0))
                .withMessage("properties should be an immutable list");
    }

    @Test
    void shouldThrowOnMutableListOnSocketedItems() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> new Item(false, false, false, false, false,
                    false, false, false, false, 0, (short) 0, (short) 0, "code", "type",
                    "type2", ItemType.MISC, (short) 0, (short) 0, "", "", (short) 1, (short) 1, List.of(), List.of(),
                    (short) 0, (short) 0, (short) 0, (short) 0, "itemName", "setName", "", 0, (short) 0,
                    (short) 0, (short) 0, 0, 0, 0, 0, CharacterType.NECROMANCER, List.of(), new ArrayList<>(), ItemLocation.BELT, ItemQuality.NONE,
                    ItemPosition.GLOVES, ItemContainer.INVENTORY, 0, (short) 0, 0, 0))
                .withMessage("socketedItems should be an immutable list");
    }
}