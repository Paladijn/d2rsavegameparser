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

import java.util.Arrays;
import java.util.Objects;

/**
 * Representation of an item property. These are the attributes of an item, along with the order in which they are displayed.
 *
 * @param index the index value of the list retrieved from itemstatcost.txt (starting at 0, minus the header)
 * @param name The name of this property, from itemstatcost.txt
 * @param values A list of the values for this property. Usually one, but in some cases more (min-max damage for example).
 * @param qualityFlag This is a topic on its own, in short: typically gems and runes have type 7 (weapon), 8 (armour) and 9 (shield). Sets start at 20 up to 25 and 26 for the full-set bonus.
 * @param order Order in which to display the items, higher values come first.
 *
 * @author Paladijn
 */
public record ItemProperty(int index, String name, int[] values, int qualityFlag, int order) {
    @Override
    public String toString() {
        StringBuilder sbValues = new StringBuilder();
        sbValues.append("[");
        for(int value: values) {
            if (sbValues.length() > 1) {
                sbValues.append(", ");
            }
            sbValues.append(value);
        }
        sbValues.append("]");
        return "ItemProperty(index=%d, name=%s, values=%s, qualityFlag=%d, order=%d)".formatted(index, name, sbValues, qualityFlag, order);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ItemProperty compare = (ItemProperty) o;
        return index == compare.index()
                && name.equals(compare.name())
                && Arrays.equals(values, compare.values())
                && qualityFlag == compare.qualityFlag()
                && order == compare.order();    }

    @Override
    public int hashCode() {
        int result = Objects.hash(index, name, qualityFlag, order);
        result = 31 * result + Arrays.hashCode(values);
        return result;
    }
}
