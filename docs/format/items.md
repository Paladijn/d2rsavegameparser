# Diablo II item format

The `Reign of the Warlock` expansion significantly changed the item format as used from 1.09 up to 3.1.91636. This document is an attempt to document the changes as of file identifier 105

## Item list Header

The item list header will occur for any list of items, such as the players or the active Mercenary's items. [The character format](characterFile.md) can be used to determine their locations.

| Byte position | Size    | Contents                             |
|---------------|---------|--------------------------------------|
| 0             | 2 chars | The string identifier `JM` (4A 4D)   |
| 2             | short   | the number of items on the character |

## Item header

An item always starts at a new byte boundary (meaning: you won't start mid-way a byte, if your previous item ended on bit 5, you'll start at the next byte, bit 0).  
The following byte/bits are always present on an item, regardless if it's simple, or a unique filled with socketed jewels and runes.

!! Note: these are bit positions, not bytes!

| Bit position | Size                 | Contents                                                                  |
|--------------|----------------------|---------------------------------------------------------------------------|
| 0            | 5                    | unknown                                                                   |
| 5            | 1                    | Item has been identified                                                  |
| 6            | 6                    | unknown                                                                   |
| 12           | 1                    | The item is socketed                                                      |
| 13           | 4                    | unknown                                                                   |
| 17           | 1                    | The item is a player ear (yuck).                                          |
| 18           | 4                    | unknown                                                                   |
| 22           | 1                    | This is a simple item                                                     |
| 23           | 1                    | The item is ethereal (can't be repaired, lower req, higher defense)       |
| 24           | 1                    | unknown                                                                   |
| 25           | 1                    | The item is personalised                                                  |
| 26           | 1                    | unknown                                                                   |
| 27           | 1                    | The item is a runeword                                                    |
| 28           | 3                    | unknown                                                                   |
| 31           | 3                    | The item location                                                         |
| 34           | 4                    | The item position (0 when not equipped)                                   |
| 38           | 4                    | X-coordinate (column) of the item in a container (inventory, stash, cube) |
| 42           | 4                    | Y-coordinate (row) of the item in a container                             |
| 46           | 3                    | The item container                                                        |
| 49           | 4 chars (4 * 8 bits) | Huffman encoded string of the item code, this field is not byte-aligned   |
| 81           | 3                    | the number of socketed items                                              |

