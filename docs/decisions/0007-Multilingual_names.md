# Multilingual names

## Context and Problem Statement

Currently we use the names as supplied by the .txt files which are in English and even contain several typoes. A clear example is McAuley's which is displayed as Sander's in game.  
Do we want to offer the translated version, or leave that outside the purpose of the library?

## Considered Options

* Included the required .json files to translate the output to the right language
* Leave the translation up to the application using the library

## Decision Outcome

Chosen option: leave the translation up to the application using the library
* Good because it isn't always needed
* Good because it will keep the library scope smaller: no need to supply json files and a library to parse them
* Good as it allows on the fly translations by applications without reloading the entire character file
* Bad is it will impact adoption: the user of the library will have to supply the .json files and take care of translation
