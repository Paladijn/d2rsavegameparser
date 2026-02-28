# Diablo II Chronicle stash tab format
For Diablo II: Reign of the Warlock expansion  

Note: this only covers the final tab of the `ModernSharedStashSoftCoreV2.d2i` file, the other tabs will be covered in a separate file.

## File Header

The bytes below are counted from the final `55 AA 55 AA` field in the stash file.

| Byte position | Size              | Contents                                                                                             |
|---------------|-------------------|------------------------------------------------------------------------------------------------------|
| 0             | long              | File Header, must always be 0xAA55AA555                                                              |
| 4             | long              | unknown                                                                                              |
| 8             | int               | File version, this guide covers 105 (0x69), which matches patch 3.1.91636 and higher                 |
| 10            | int               | length in bytes of the data                                                                          |
| 12            | int               | gold in this stash tab, always 0 for the chronicle                                                   |
| 14            | int               | unknown (00 00)                                                                                      |
| 16            | long              | unknown                                                                                              |
| 20            | long              | unknown                                                                                              |
| 24-63         | -                 | 00s                                                                                                  |
| 64            | long              | unknown                                                                                              |
| 68            | short             | unknown                                                                                              |
| 70            | short             | number of set items discovered                                                                       |
| 72            | short             | number of uniques discovered                                                                         |
| 74            | short             | number of runewords discovered                                                                       |
| 76-83         | -                 | 00s                                                                                                  |
| 84            | long              | unknown                                                                                              |
| 88            | 10 bytes per item | List of chronicle registries, first the sets, then uniques and finally the runewords                 |
| varies        | -                 | There is some leftover data here that's still unclear to me. Patterns include `10 00 A0 00` and 0x74 |

## Chronicle item

| Byte position | Size  | Contents                                        |
|---------------|-------|-------------------------------------------------|
| 0             | short | monster ID (name can be found in monsters.json) |
| 2             | long  | timestamp in minutes since 1-1-1970             |
| 6             | long  | unknown id, likely referring to the item        |
