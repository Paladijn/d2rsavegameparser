# Savegame parser for Diablo II: Resurrected release notes

## [1.3.3](https://github.com/Paladijn/d2rsavegameparser/releases/tag/d2rsavegameparser-1.3.3)
2024-09-04
* Fixed reqLvl on socketed items (highest value will be used)
* Updated dependencies

## [1.3.2](https://github.com/Paladijn/d2rsavegameparser/releases/tag/d2rsavegameparser-1.3.2)
2024-07-10
* Invalid pre/suffix id's no longer result in a parserException, but will display an error indicating which item failed.

## [1.3.1](https://github.com/Paladijn/d2rsavegameparser/releases/tag/d2rsavegameparser-1.3.1)
2024-05-11
* Fixed calculation and added testcase for Barbarian natural resistances

## [1.3.0](https://github.com/Paladijn/d2rsavegameparser/releases/tag/d2rsavegameparser-1.3.0)
2024-03-05
* True immutability on lists
* Externalised txt resource files
* Updated dependencies

## [1.2.0](https://github.com/Paladijn/d2rsavegameparser/releases/tag/d2rsavegameparser-1.2.0)
2024-01-05
* Moved packages to io.github.com for publishing on Maven Central

## [1.1.1](https://github.com/Paladijn/d2rsavegameparser/releases/tag/d2rsavegameparser-1.1.1)
2023-12-29
* Fixed nullpointer on skills with a new character

## [1.1.0](https://github.com/Paladijn/d2rsavegameparser/releases/tag/d2rsavegameparser-1.1.0)
2023-12-29
* Added skill support including passive benefits to display (Paladin res aura's, Barbarian's natural resistances)

## [1.0.0](https://github.com/Paladijn/d2rsavegameparser/releases/tag/d2rsavegameparser-1.0.0)
2023-12-24
* Initial release supporting patch 1.6.77312
