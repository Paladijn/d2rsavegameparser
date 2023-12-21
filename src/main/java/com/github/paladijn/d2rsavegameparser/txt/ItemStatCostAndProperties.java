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

import java.util.Map;

/**
 * @author Paladijn
 *
 * Helper container record to pass on the itemStatCosts and genericProperty maps, so we don't have to parse those along separately.
 * @param itemStatCostsByCode All {@link ItemStatCost} mapped on the code.
 * @param genericPropertiesByCode All generic properties mapped on the code.
 */
record ItemStatCostAndProperties(Map<String, ItemStatCost> itemStatCostsByCode, Map<String, String> genericPropertiesByCode) { }
