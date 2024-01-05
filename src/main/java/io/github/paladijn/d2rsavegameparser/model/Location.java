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

/**
 * Location of the character per {@link Difficulty} which is stored in a list of three where the current difficulty the character is in has the active field set.
 *
 * @param isActive True if this is the difficulty and act the character is currently in. A character that has unlocked {@link Difficulty#HELL} can be in {@link Difficulty#NORMAL}
 * @param currentAct The current act a player is in (1..5)
 * @param difficulty The {@link Difficulty} of this location.
 *
 * @author Paladijn
 */
public record Location(boolean isActive, int currentAct, Difficulty difficulty) { }
