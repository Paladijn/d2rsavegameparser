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

import io.github.paladijn.d2rsavegameparser.model.ItemProperty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Representation of a set from the sets.txt resource file.
 *
 * @author Paladijn
 */
public final class SetData {
    private final String name;

    private final List<Short> itemIDs;

    private final List<ItemProperty> partialBonuses = new ArrayList<>();
    private final List<ItemProperty> fullBonuses = new ArrayList<>();

    /**
     * Constructor which parses a tab-separated line from sets.txt
     * @param line a tab-separated line which contains the fields we need for this item representation
     * @param setIDs list of the item id's from setitems.txt that belong to this set
     * @param itemStatCostAndProperties a container {@link ItemStatCostAndProperties} containing both {@link ItemStatCost} and a map of properties used for the Gems statistics.
     */
    public SetData(String line, final Map<String, List<Short>> setIDs, ItemStatCostAndProperties itemStatCostAndProperties) {
        final String[] blocks = line.split("\t");
        name = blocks[1];

        itemIDs = setIDs.getOrDefault(name, new ArrayList<>());

        int startPartial = 3;
        for (int i = 2; i < 6; i++) {
            if (!blocks[startPartial].isBlank()) {
                String propName = blocks[startPartial];
                String min = blocks[startPartial + 2];
                String max = blocks[startPartial + 3];
                String param = blocks[startPartial + 1];
                partialBonuses.addAll(TXTProperties.getPropertiesByName(propName, min, max, param, i + 20, itemStatCostAndProperties));
            }
            startPartial = startPartial + 8;
        }

        int startFullSet = 35;
        for (int i = 1; i < 9; i++) {
            if (!blocks[startFullSet].isBlank()) {
                String propName = blocks[startFullSet];
                String min = blocks[startFullSet + 2];
                String max = blocks[startFullSet + 3];
                String param = blocks[startFullSet + 1];
                fullBonuses.addAll(TXTProperties.getPropertiesByName(propName, min, max, param, 26, itemStatCostAndProperties));
            }
            startFullSet = startFullSet + 4;
        }
    }

    /**
     * Get the name of this Set.
     * @return the name of this Set
     */
    public String getName() {
        return name;
    }

    /**
     * Get the list of partial bonuses for this set
     * @return List of immutable {@link ItemProperty} of partial bonuses for this set
     */
    public List<ItemProperty> getPartialBonuses() {
        return Collections.unmodifiableList(partialBonuses);
    }

    /**
     * Get the list of set item id's belonging to this set
     * @return An immutable list of set item id's belonging to this set
     */
    public List<Short> getItemIDs() {
        return Collections.unmodifiableList(itemIDs);
    }

    /**
     * Get the list of full set bonuses for this set
     * @return List of immutable {@link ItemProperty} of full set bonuses for this set
     */
    public Collection<ItemProperty> getFullBonuses() {
        return Collections.unmodifiableList(fullBonuses);
    }
}
