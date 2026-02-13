# Diablo II Saved Game File Format
For Diablo II: Reign of the Warlock expansion  
This overview is partially based on Trevin Beattie's excellent description of the 1.0.9 format [hosted here](http://user.xmission.com/~trevin/DiabloIIv1.09_File_Format.shtml) and adapted to the latest format.

## File Header

| Byte position | Size     | Contents                                                                                                                                                                                                                                                                                                                                                              |
|---------------|----------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 0             | long     | File Header, must always be 0xAA55AA555                                                                                                                                                                                                                                                                                                                               |
| 4             | long     | File version, this guide supports only 105, which matches patch 3.1.91636 and higher                                                                                                                                                                                                                                                                                  |
| 8             | long     | file size                                                                                                                                                                                                                                                                                                                                                             |
| 12            | long     | checksum                                                                                                                                                                                                                                                                                                                                                              |
| 16            | long     | unknown                                                                                                                                                                                                                                                                                                                                                               |
| 20            | byte     | [Character status](#character-status), this is a bit field                                                                                                                                                                                                                                                                                                            |
| 21            | byte     | [Character progression](#character-progression)                                                                                                                                                                                                                                                                                                                       |
| 22            | 2 bytes  | unknown                                                                                                                                                                                                                                                                                                                                                               |
| 24            | byte     | [Character class](#character-class-field)                                                                                                                                                                                                                                                                                                                             |
| 25            | 2 bytes  | unknown                                                                                                                                                                                                                                                                                                                                                               |
| 27            | byte     | Character's level                                                                                                                                                                                                                                                                                                                                                     |
| 28            | long     | unknown                                                                                                                                                                                                                                                                                                                                                               |
| 32            | long     | Time stamp.  This is in the standard time() format of the number of seconds which have elapsed since midnight, January 1, 1970 (UTC).                                                                                                                                                                                                                                 |
| 36            | long     | unknown                                                                                                                                                                                                                                                                                                                                                               |
| 152           | 3 bytes  | [Difficulty](#difficulty-field) These bytes indicate which difficulty the character is playing. The first byte corresponds to Normal, the second Nightmare, and the third Hell.                                                                                                                                                                                       |
| 155           | long     | Map ID                                                                                                                                                                                                                                                                                                                                                                |
| 299           | 16 chars | Character name. The name may be up to 15 characters long; the rest of the field must be padded with null bytes. Remember the rules for Diablo II character names: 2-15 characters, containing only upper and lower case letters (A-Z), with the possible addition of one dash ( - ) or underscore ( _ ) as long as it is not the first or last character of the name. |


## Quest completion data
| Byte position | Size    | Contents                                                         |
|---------------|---------|------------------------------------------------------------------|
| 403           | 4 chars | The string identifier `Woo!`, this used to be 335 prior to RotW. |
| 407           | 6 bytes | unknown                                                          |

## Waypoint data

| Byte position | Size     | Contents                                                       |
|---------------|----------|----------------------------------------------------------------|
| 701           | 2 chars  | The string identifier `WS`, this used to be 633 prior to RotW. |
| 703           | 6 bytes  | unknown                                                        |
| 709           | 24 bytes | [Waypoint data for Normal](#waypoint-data)                     |
| 733           | 24 bytes | [Waypoint data for Nightmare](#waypoint-data)                  |
| 757           | 24 bytes | [Waypoint data for Hell](#waypoint-data)                       |

### Waypoint field
| Byte position | Size     | Contents                                                                                                                                    |
|---------------|----------|---------------------------------------------------------------------------------------------------------------------------------------------|
| 0             | 2 bytes  | Unknown                                                                                                                                     |
| 2             | 5 bytes  | Waypoints.  This is a bitfield, with one bit assigned to each waypoint in LSB order -- so bit 0 in the Rogue Encampment waypoint for Act I. |
| 7             | 17 bytes | Unknown                                                                                                                                     |

## NPC introductions

| Byte position | Size    | Contents                                                       |
|---------------|---------|----------------------------------------------------------------|
| 782           | 2 chars | The string identifier `w4`, this used to be 714 prior to RotW. |
| 784           | byte    | unknown                                                        |
...

## Character statistics

| Byte position | Size    | Contents                                                       |
|---------------|---------|----------------------------------------------------------------|
| 833           | 2 chars | The string identifier `gf`, this used to be 765 prior to RotW. |
| 835           | short   | unknown                                                        |
...

## Character Skills

| Byte position | Size     | Contents                                                                      |
|---------------|----------|-------------------------------------------------------------------------------|
| Varies        | 2 chars  | The string identifier `if`                                                    |
| -             | 30 bytes | Each byte corresponds to one of the character skills listed [here](skills.md) |

## Items

| Byte position | Size    | Contents                                                                                                    |
|---------------|---------|-------------------------------------------------------------------------------------------------------------|
| Varies        | 2 chars | The string identifier `JM`                                                                                  |
| -             | short   | the number of items on the character                                                                        |
| -             | varies  | the item data                                                                                               |
| -             | 2 chars | The string identifier `JM` for items on the corpse                                                          |
| -             | short   | the number of items on the corpse, in case this is 1 12 bytes will follow, after which the items are stored |

## Mercenary items

| Byte position | Size    | Contents                                                      |
|---------------|---------|---------------------------------------------------------------|
| Varies        | jf      | If this is an expansion character the mercs items will follow |
| -             | 2 chars | The string identifier `JM`                                    |
| -             | short   | the number of items on the mercenary                          |

## Iron Golem
| Byte position | Size    | Contents                                                              |
|---------------|---------|-----------------------------------------------------------------------|
| Varies        | 2 chars | The string identifier `kf`                                            |
| -             | byte    | 1 when an iron golem is active                                        |
| -             | varies  | the item data for the iron golem                                      |
| -             | 2 bytes | In case of the Warlock there's also two bytes here, possibly consume? |

## Warlock minion
| Byte position | Size    | Contents                   |
|---------------|---------|----------------------------|
| Varies        | 2 chars | The string identifier `lf` |
| -             | byte    | unknown                    |
| -             | byte    | unknown                    |

## Sub-tables

### Character status

| 7 - 6   | 5                   | 4       | 3                | 2        | 1 - 0   |
|---------|---------------------|---------|------------------|----------|---------|
| Unknown | Expansion Character | Unknown | died in the past | Hardcore | Unknown |

### Character progression

Character progression.  This number tells (sort of) how many acts you have completed from all difficulty levels.  It appears to be incremented when you kill the final demon in an act -- i.e., Andarial, Duriel, Mephisto, and Diablo / Baal.  There's a catch to that last one: in an Expansion game, the value is not incremented after killing Diablo, but is incremented by 2 after killing Baal.  (The reason is unknown.)  So it skips the values 4, 9, and 14.

I believe this value is used in determining your character's title.  The title is one of the following values (depending on the character class' gender):
Value	Standard	Hardcore	Value	Expansion	Hardcode Exp.
0-3	(no title)	0-3	(no title)
4-7	Sir / Dame	Count / Countess	5-8	Slayer	Destroyer
8-11	Lord / Lady	Duke / Duchess	10-13	Champion	Conqueror
12	Baron / Baroness	King / Queen	15	Patriarch / Matriarch	Guardian

### Character class field

Character class. The defined classes are:
0   Amazon
1   Sorceress
2   Necromancer
3   Paladin
4   Barbarian
5   Druid (Expansion character only)
6   Assassin (Expansion character only)
7   Warlock (Warlock expansion character only)

### Difficulty field

This is a byte field describing the difficulty the player is located

| 7      | 6 - 3   | 2 - 0                            |
|--------|---------|----------------------------------|
| Active | Unknown | Which act the player is in (0-4) |
