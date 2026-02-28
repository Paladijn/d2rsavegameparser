# Diablo II Character skills

## Common skills
The common skill ID's are only used in button actions.

| ID  | Skill                 |
|-----|-----------------------|
| 0   | Attack                |
| 1   | Kick                  |
| 2   | Throw                 |
| 3   | Unsummon              |
| 4   | Left hand swing       |
| 5   | Right hand swing      |
| 217 | Scroll of Identify    |
| 218 | Tome of Identify      |
| 219 | Scroll of Town Portal |
| 220 | Tome of Town Portal   |

There are two numbers shown for each character skill. One is the offset into the [character skills section](characterFile.md#character-skills) of the saved character file.  
The other is the skill ID used in some of the magical properties (i.e., "+2 to Bash (Barbarian Only)", "level 3 Fire Bolt (33/33 Charges)", etc.), and in the button actions.

## Amazon Skills

### Bow and Crossbow skills

| offset | ID | Skill            | level | Prerequisites             |
|--------|----|------------------|-------|---------------------------|
| 0      | 6  | Magic Arrow      | 1     |                           |
| 1      | 7  | Fire Arrow       | 1     |                           |
| 5      | 11 | Cold Arrow       | 6     |                           |
| 6      | 12 | Multiple Shot    | 6     | Magic Arrow               |
| 10     | 16 | Exploding Arrow  | 12    | Fire Arrow, Multiple Shot |
| 15     | 21 | Ice Arrow        | 18    | Cold Arrow                |
| 16     | 22 | Guided Arrow     | 18    | Cold Arrow, Multiple Shot |
| 20     | 26 | Strafe           | 24    | Guided Arrow              |
| 21     | 27 | Immolation Arrow | 24    | Exploding Arrow           |
| 25     | 31 | Freezing Arrow   | 30    | Ice Arrow                 |

### Passive and Magic skills

| offset | ID | Skill           | level | Prerequisites   |
|--------|----|-----------------|-------|-----------------|
| 2      | 8  | Inner Sight     | 1     |                 |
| 3      | 7  | Critical Strike | 1     |                 |
| 7      | 13 | Dodge           | 6     |                 |
| 11     | 17 | Slow Missiles   | 6     | Inner Sight     |
| 12     | 18 | Avoid           | 12    | Dodge           |
| 17     | 23 | Penetrate       | 12    | Critical Strike |
| 22     | 28 | Decoy           | 18    | Slow Missiles   |
| 23     | 29 | Evade           | 24    | Avoid           |
| 26     | 32 | Valkyrie        | 30    | Decoy, Evade    |
| 27     | 33 | Pierce          | 30    | Penetrate       |

### Javelin and Spear skills

| offset | ID | Skill            | level | Prerequisites                |
|--------|----|------------------|-------|------------------------------|
| 4      | 10 | Jab              | 1     |                              |
| 8      | 14 | Power Strike     | 6     | Jab                          |
| 9      | 15 | Poison Javelin   | 6     |                              |
| 13     | 19 | Impale           | 12    | Jab                          |
| 14     | 20 | Lightning Bolt   | 12    | Poison Javelin               |
| 18     | 24 | Charged Strike   | 18    | Power Strike, Lightning Bolt |
| 19     | 25 | Plague Javelin   | 18    | Lightning Bolt               |
| 24     | 30 | Fend             | 24    | Impale                       |
| 28     | 24 | Lightning Strike | 30    | Charged Strike               |
| 29     | 35 | Lightning Fury   | 30    | Plague Javelin               |

...

## Warlock skills

Unlike the other classes the Warlock skills are all over the place in their offset.

### Chaos
| offset | ID  | Skill            | level | Prerequisites            |
|--------|-----|------------------|-------|--------------------------|
| 22     | 395 | Miasma Bolt      | 1     |                          |
| 20     | 393 | Sigil: Lethargy  | 6     |                          |
| 21     | 394 | Ring of Fire     | 6     |                          |
| 23     | 396 | Sigil: Rancor    | 12    | Sigil: Lethargy          |
| 26     | 399 | Miasma Chain     | 12    | Miasma Bolt              |
| 25     | 398 | Flame Wave       | 18    | Ring of Fire             |
| 27     | 400 | Sigil: Death     | 24    | Sigil: Rancor            |
| 24     | 397 | Enhanced Entropy | 24    | Miasma Chain             |
| 28     | 401 | Apocalypse       | 30    | Flame Wave, Sigil: Death |
| 29     | 402 | Abyss            | 30    | Enhanced Entropy         |

### Eldritch

| offset | ID  | Skill              | level | Prerequisites      |
|--------|-----|--------------------|-------|--------------------|
| 10     | 383 | Levitation Mastery | 1     |                    |
| 12     | 385 | Hex: Bane          | 1     |                    |
| 18     | 391 | Cleave             | 6     | Levitation Mastery |
| 15     | 388 | Echoing Strike     | 12    | Levitation Mastery |
| 16     | 389 | Hex: Purge         | 12    | Hex: Bane          |
| 17     | 390 | Blade Warp         | 18    | Echoing Strike     |
| 14     | 387 | Psychic Ward       | 18    | Cleave             |
| 11     | 384 | Eldritch Blast     | 24    | Psychic Ward       |
| 13     | 386 | Hex: Siphon        | 24    | Hex: Purge         |
| 19     | 392 | Mirrored Blades    | 30    | Eldritch Blast     |

### Demon

| offset | ID  | Skill           | level | Prerequisites  |
|--------|-----|-----------------|-------|----------------|
| 0      | 373 | Summon Goatman  | 1     |                |
| 1      | 374 | Demonic Mastery | 1     | Summon Goatman |
| 5      | 378 | Blood Oath      | 6     |                |
| 2      | 375 | Death Mark      | 6     | Summon Goatman |
| 3      | 376 | Summon Tainted  | 12    | Summon Goatman |
| 7      | 380 | Blood Boil      | 18    | Blood Oath     |
| 4      | 377 | Summon Defiler  | 18    | Summon Tainted |
| 6      | 379 | Engorge         | 24    | Blood Boil     |
| 8      | 381 | Consume         | 30    | Blood Oath     |
| 9      | 382 | Bind Demon      | 30    | Engorge        |
