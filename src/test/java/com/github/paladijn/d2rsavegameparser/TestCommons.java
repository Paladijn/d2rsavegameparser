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
package com.github.paladijn.d2rsavegameparser;

import com.github.paladijn.d2rsavegameparser.parser.ParseException;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface TestCommons {
    static ByteBuffer getBuffer(String fileLocation) {
        ByteBuffer buffer;
        try {
            buffer = ByteBuffer.wrap(ClassLoader.getSystemResourceAsStream(fileLocation).readAllBytes());
        } catch (IOException | NullPointerException e) {
            throw new ParseException("Failed to read characterFile for tests", e);
        }
        return buffer;
    }
}
