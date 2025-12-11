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
package io.github.paladijn.d2rsavegameparser.txt;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TXTPropertiesTest {
    private final TXTProperties cut = TXTProperties.getInstance();

    @Test
    void parseTwoHandedWeapons() {
        final WeaponStats giantAxe = cut.getWeaponStatsByCode("gix");
        assertThat(giantAxe.isTwoHanded()).isTrue();

        final WeaponStats grandScepter = cut.getWeaponStatsByCode("gsc");
        assertThat(grandScepter.isTwoHanded()).isFalse();
    }

    @Test
    void setItem() {
        final SetItem ironFist = cut.getSetItemById((short) 4);

        assertThat(ironFist)
                .extracting(SetItem::getId, SetItem::getSetName, SetItem::getName, SetItem::getCode, SetItem::getItemName, SetItem::cannotLoot)
                .containsExactly(4, "Hsarus' Defense", "Hsarus' Iron Fist", "buc", "Buckler", false);
    }
}