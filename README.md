# Savegame parser for Diablo II: Resurrected
This library started as an attempt to read Diablo II: Resurrected savegame files to display current statistics on stream by exporting them to a html file.

Version 2.8 (patch 1.6.84219) which was released December 3rd, 2024 is currently supported. The library will target the latest available version (as the game requires to be patched) and will only parse, not write savegames.

### Functionality
* Parse full character file
  * Character stats
  * Items
  * Quest status (only the ones with impact on character stats at the moment)
  * Mercenary and Iron golem
* Read items from shared stash
* TxtProperties for direct access to the DII:R data files

Other applications of the library may include:
* A holy grail tracker
* A Key tracker
* List all single player runewords you can make, along with the location of the runes
* A tool to search items in your character files 
* The ability to display a characters inventory and items.
* Etc., etc

### How to use
Minimal Java version: 21  
Ensure the txt files you wish to use are available in the txt folder of your application. You can copy the supplied patch 1.6.84219 ones from the txt folder in this project.

Dependency:
```xml
<dependency>
  <groupId>io.github.paladijn</groupId>
  <artifactId>d2rsavegameparser</artifactId>
  <version>1.5.2</version>
</dependency>
```
Required dependencies: org.slf4j:org.slf4j-api:2.0.16+ (this is defined as provided, so you'll need to declare your own version if you didn't already)

Example projects:
* [My own example project](https://github.com/Paladijn/d2rsavegameparser-examples) includes socket reward available, list specific items (Sets) and translations.

To use the library, there are two entry points: CharacterParser and SharedStashParser as these are parsing separate files.  

Both classes are instantiated with a boolean parameter indicating if you want to log the Item bytes (for debugging and testing purposes) which is typically false. 
You then call the instance with the .parse(java.nio.ByteBuffer bytebuffer_of_the_file) and retrieve a read-only record with the data or a ParseException if something went wrong.

For an example on how to create the bytebuffer check the TestCommons::getBuffer implementation supplied with the test classes.

From this point on you can do with the read-only/immutable data as you want. Create counters, holy grail lists or looking for a specific item/quest in all your files as well as displaying data of the latest save on stream. The world is your oyster.

Be aware that the library will not offer translations or correct names like the Sander's (McAuley's) set. You will have to take care of this yourself by mapping to the item-names.json. See [0007-Multilingual_names.md](docs/decisions/0007-Multilingual_names.md) for more information and [the example project](https://github.com/Paladijn/d2rsavegameparser-examples) for example code to implement this.

### Support / contribute
If you encounter any issues or would like to see improvements, please use the [GitHub issue tracker](https://github.com/Paladijn/d2rsavegameparser/issues) to document them.  
Some restrictions on requests are in place due to [design decisions made](docs/decisions), so please make sure they are not already covered. In case you feel that decision was in error, feel free to open an issue and start a discussion. You're always free to fork and make adjustments by yourself due to the license.

Please include a savegame file in case it cannot be read, or it needs to be covered in an improvement.

Contributions are appreciated! Please read the [design decisions made](docs/decisions) before writing code and submitting a merge request. If there's anything unclear, or you have questions feel free to reach out first.

### Thanks + useful links
Many thanks to Trevin Beattie's description of the 1.0.9 format [hosted here](http://user.xmission.com/~trevin/DiabloIIv1.09_File_Format.shtml).  
[GoMule](https://sourceforge.net/projects/gomule/) by _Randall_ and _Silospen_ has been a great resource as well. This project loads and saves character files using a Java Swing UI, so if you're looking for that please check it out.

### External content
The .txt files supplied in the tzt folder are retrieved from the game files and match their patch level. These are usually altered for modding purposes, but in our case we use a couple of them for fast updates of game data.  
Diablo II: Resurrected was developed by Vicarious Visions/Blizzard Albany and is a trademark of Blizzard entertainment. More information is available on [Wikipedia](https://en.wikipedia.org/wiki/Diablo_II:_Resurrected) or at [Blizzard's site](https://diablo2.blizzard.com).
