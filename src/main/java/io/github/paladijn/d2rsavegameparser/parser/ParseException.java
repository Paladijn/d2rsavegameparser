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
package io.github.paladijn.d2rsavegameparser.parser;

/**
 * Internal wrapper of {@link RuntimeException} to encapsulate any error occurring during the parsing of either an item or the savegame file itself.
 */
public final class ParseException extends RuntimeException{
    /**
     * Constructs a new ParseException with the specified detail message. The cause is not initialized, and may subsequently be initialized by a call to initCause.
     * @param message the detail message. The detail message is saved for later retrieval by the getMessage() method.
     */
    public ParseException(String message) {
        super(message);
    }

    /**
     * Constructs a new ParseException with the specified detail message and cause.
     * Note that the detail message associated with cause is not automatically incorporated in this runtime exception's detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the getMessage() method).
     * @param cause the cause (which is saved for later retrieval by the getCause() method). (A null value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
