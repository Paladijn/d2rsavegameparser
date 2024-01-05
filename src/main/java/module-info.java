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
/**
 * Library to parse savegames for the 2021 Diablo II: Resurrected game. Please refer to the README.md for full information.
 * <p>
 * Functionality
 * <ul>
 *     <li>Parse full character file</li>
 *     <li>
 *      <ul>
 *          <li>Character stats</li>
 *          <li>Items</li>
 *          <li>Quest status (only the ones with impact on character stats at the moment)</li>
 *          <li>Mercenary and Iron golem</li>
 *      </ul>
 *     </li>
 *     <li>Read items from shared stash</li>
 *     <li>TxtProperties for direct access to the DII:R data files</li>
 * </ul>
 *
 * @author Paladijn (Paladijn2960+d2rsavegameparser@gmail.com)
 */
module io.github.paladijn.d2rsavegameparser {
    requires org.slf4j;

    exports io.github.paladijn.d2rsavegameparser.parser;
    exports io.github.paladijn.d2rsavegameparser.model;
    exports io.github.paladijn.d2rsavegameparser.txt;
}
