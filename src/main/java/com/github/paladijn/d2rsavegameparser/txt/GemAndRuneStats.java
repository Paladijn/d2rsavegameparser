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
package com.github.paladijn.d2rsavegameparser.txt;

import com.github.paladijn.d2rsavegameparser.model.ItemProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representation of a gem or rune item from the gems.txt resource file.
 *
 * @author Paladijn
 */
public final class GemAndRuneStats {
    private final String code;

    private final List<ItemProperty> weaponProperties = new ArrayList<>();

    private final List<ItemProperty> helmProperties = new ArrayList<>();

    private final List<ItemProperty> armorProperties = new ArrayList<>();

    private final List<ItemProperty> allProperties = new ArrayList<>();

    /**
     * Constructor which parses a tab-separated line from gems.txt
     * @param line a tab-separated line which contains the fields we need for this item representation
     * @param itemStatCostAndProperties a container {@link ItemStatCostAndProperties} containing both {@link ItemStatCost} and a map of properties used for the Gems statistics.
     */
    public GemAndRuneStats(final String line, ItemStatCostAndProperties itemStatCostAndProperties) {
        final int[][] typeIndixes = {
                {  4,  8, 12 },
                { 16, 20, 24 },
                { 28, 32, 36 }
        };

        final String[] blocks = line.split("\t");

        code = blocks[3];

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                final int index = typeIndixes[x][y];
                final String name = blocks[index];
                if (name.equals("")) {
                    continue;
                }

                final String param = blocks[index + 1];
                final String min = blocks[index + 2];
                final String max = blocks[index + 3];

                switch (x) {
                    case 0 -> weaponProperties.addAll(TXTProperties.getPropertiesByName(name, min, max, param, 7, itemStatCostAndProperties));
                    case 1 -> helmProperties.addAll(TXTProperties.getPropertiesByName(name, min, max, param, 8, itemStatCostAndProperties));
                    case 2 -> armorProperties.addAll(TXTProperties.getPropertiesByName(name, min, max, param, 9, itemStatCostAndProperties));
                }
            }
        }

        allProperties.addAll(weaponProperties);
        allProperties.addAll(helmProperties);
        allProperties.addAll(armorProperties);
    }

    /**
     * Get the code of the gem or rune.
     *
     * @return the code of the gem or rune
     */
    public String getCode() {
        return code;
    }

    /**
     * Get all properties of a gem or rune
     * @return an immutable list of {@link ItemProperty} offered by this item
     */
    public List<ItemProperty> getAllProperties() {
        return Collections.unmodifiableList(allProperties);
    }
}
