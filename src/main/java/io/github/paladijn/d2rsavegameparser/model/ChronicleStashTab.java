/*
 * Copyright (C) 2026   Paladijn (paladijn2960+d2rsavegameparser@gmail.com)
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

import java.util.List;

public record ChronicleStashTab(
        int setItemsDiscovered,
        int uniqueItemsDiscovered,
        int runewordsDiscovered,
        List<ChronicleItem> setItems,
        List<ChronicleItem> uniques,
        List<ChronicleItem> runewords
) {
    public ChronicleStashTab {
        setItems = List.copyOf(setItems);
        uniques = List.copyOf(uniques);
        runewords = List.copyOf(runewords);
    }
}
