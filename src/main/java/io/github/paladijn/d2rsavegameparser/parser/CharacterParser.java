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

import io.github.paladijn.d2rsavegameparser.internal.parser.AttributeParser;
import io.github.paladijn.d2rsavegameparser.internal.parser.BitReader;
import io.github.paladijn.d2rsavegameparser.model.CharacterType;
import io.github.paladijn.d2rsavegameparser.model.D2Character;
import io.github.paladijn.d2rsavegameparser.model.Difficulty;
import io.github.paladijn.d2rsavegameparser.model.FileData;
import io.github.paladijn.d2rsavegameparser.model.Item;
import io.github.paladijn.d2rsavegameparser.model.ItemLocation;
import io.github.paladijn.d2rsavegameparser.model.ItemPosition;
import io.github.paladijn.d2rsavegameparser.model.ItemProperty;
import io.github.paladijn.d2rsavegameparser.model.ItemQuality;
import io.github.paladijn.d2rsavegameparser.model.Location;
import io.github.paladijn.d2rsavegameparser.model.Mercenary;
import io.github.paladijn.d2rsavegameparser.model.QuestData;
import io.github.paladijn.d2rsavegameparser.model.Skill;
import io.github.paladijn.d2rsavegameparser.model.SkillType;
import io.github.paladijn.d2rsavegameparser.model.StarterAttributes;
import io.github.paladijn.d2rsavegameparser.model.WaypointStatus;
import io.github.paladijn.d2rsavegameparser.txt.SetData;
import io.github.paladijn.d2rsavegameparser.txt.TXTProperties;
import org.slf4j.Logger;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * This is the main entrypoint of the library. It will accept a {@link ByteBuffer} of a savegame file of the Diablo II Resurrected action RPG by Blizzard Entertainment and represent
 * the character data along with all equipped items in Java PoJo's as covered in the <a href="../model/package-summary.html">io.github.paladijn.d2rsavegameparser.model package</a>.
 * Developers can use these to create their own tooling such as a character viewer for streaming or a holy grail tracker.
 * <p>
 * This library only supports parsing/reading the data of the latest game version and does not offer functionality to store the data after altering it. If you're looking for a
 * Java solution to perform mutations please check out the GoMule application by Silospen on sourceforge.
 * </p>
 *
 * @author Paladijn
 */
public final class CharacterParser {
    private static final Logger log = getLogger(CharacterParser.class);
    public static final int QUEST_START_INDEX = 403;

    private final ItemParser itemParser;

    private final AttributeParser attributeParser;

    /**
     * Constructor for the {@link CharacterParser}
     * @param printItemBytes helper boolean to log the bytes[] per Item for unit tests and debugging.
     */
    public CharacterParser(boolean printItemBytes) {
        itemParser = new ItemParser(printItemBytes);
        attributeParser = new AttributeParser();
    }

    /**
     * parse the {@link ByteBuffer} of a savegame file to a {@link D2Character}.
     * @param buffer a {@link ByteBuffer} of a Diablo II: resurrected savegame file
     * @return a filled {@link D2Character}
     * @throws ParseException in case there was an issue parsing the buffer
     */
    public D2Character parse(final ByteBuffer buffer) {

        buffer.order(ByteOrder.LITTLE_ENDIAN);

        if (buffer.limit() < 335) {
            throw new ParseException("Less than 335 bytes read (%d), either the file is locked, or this is not a valid .d2s file".formatted(buffer.limit()));
        }

        final int header = buffer.getInt(0);
        if (header != 0xaa55aa55) {
            throw new ParseException("Wrong fileHeader %d, this is not a Diablo II saveGame file".formatted(header));
        }
        final FileData fileData = getFileData(buffer);

        D2Character.D2CharacterBuilder characterBuilder = new D2Character.D2CharacterBuilder().fileData(fileData);

        if (fileData.version() != 105) {
            throw new ParseException("Unsupported version: " + fileData.version());
        }
        characterBuilder
                .parseCharacterStatus(buffer.get(20))
                .actProgression(buffer.get(21));

        final CharacterType characterType = CharacterType.values()[buffer.get(24)];

        characterBuilder.characterType(characterType)
                .level(buffer.get(27))
                // not bothering to parse the hotkeys and selected left/right click assigned skills. We may in the future.
                .locations(List.of(
                        parseLocation(Difficulty.NORMAL, buffer.get(152)),
                        parseLocation(Difficulty.NIGHTMARE, buffer.get(153)),
                        parseLocation(Difficulty.HELL, buffer.get(154))
                ))
                .mapId(buffer.getLong(155));

        Mercenary.MercenaryBuilder mercenaryBuilder = new Mercenary.MercenaryBuilder()
                .alive(buffer.getShort(177))
                .id(buffer.getInt(179))
                .nameId(buffer.getShort(183))
                .typeId(buffer.getShort(185))
                .experience(buffer.getInt(187));

        characterBuilder.riseOfTheWarlock(buffer.get(248));

        byte[] nameBytes = new byte[16];
        buffer.get(299, nameBytes, 0, 16);
        characterBuilder.name(new String(nameBytes).trim());

        if (buffer.limit() == 403) {
            log.info("This is a newly created character, so we'll only supply the starter attributes until the game triggers another save.");
            characterBuilder.attributes(StarterAttributes.getStarterAttributesByClass(characterType));
            return characterBuilder.build();
        }

        characterBuilder
                .questDataPerDifficulty(parseQuestData(buffer))
                .waypoints(parseWaypoints(buffer));

        byte[] npcHeaderBytes = new byte[2];
        buffer.get(782, npcHeaderBytes, 0, 2);
        String npcHeader = new String(npcHeaderBytes); // "w4"
        if (!"w4".equals(npcHeader)) {
            throw new ParseException("Could not find NPC header");
        }

        // stats
        byte[] statHeaderBytes = new byte[2];
        buffer.get(833, statHeaderBytes, 0, 2);
        String statHeader = new String(statHeaderBytes); // "gf" until "if"
        if (!"gf".equals(statHeader)) {
            throw new ParseException("Could not find stat header");
        }

        // stat length is at least xx bytes and at most yy bytes, followed by if header for the skills. We're assuming max 60 which so far seems to work (it should be < 36, but we've encountered one crash on >= 40 so far)
        byte[] skillHeaderBytes = new byte[2];
        int skillIndex = -1;
        for(int i = 870; i < 933; i++) {
            buffer.get(i, skillHeaderBytes, 0, 2);
            if("if".equals(new String(skillHeaderBytes))) {
                skillIndex = i;
                break;
            }
        }
        if (skillIndex == -1) {
            throw new ParseException("Could not find skill header 'if' below index 860");
        }

        final int statLength = skillIndex - 835;
        byte[] statBytes = new byte[statLength];
        buffer.get(835, statBytes, 0, statLength);
        characterBuilder.attributes(attributeParser.parse(statBytes));

        byte[] skillBytes = new byte[30];
        buffer.get(skillIndex + 2, skillBytes, 0, 30);
        final List<Skill> skills = parseSkills(characterType, skillBytes);

        // (available) skills are 30 bytes after the skillindex plus the 2 header bytes
        int itemIndex = skillIndex + 32;
        log.debug("parsing character items");
        // There should be a dead body indicator here JM + short value = 0, you're alive. short value = 1, then we have the items of your dead body here.
        // Skip 16 bytes -> JM items on body. So we'll look for the next JM, if it's nearby with count 0 => we're alive.
        int deadBodyIndex = findNextJM(buffer, itemIndex + 2);
        log.debug("dead body at index {}, items start at {}", deadBodyIndex, itemIndex);

        final List<Item> items = itemParser.parseItems(buffer, itemIndex, buffer.limit());

        // adjusting sets
        final HashMap<String, Integer> setCounts = getEquippedSetCounts(getEquippedSetItems(items));
        final List<Item> adjustedItems = removeSetBonuses(items, setCounts);
        final List<ItemProperty> activeSetBenefits = getActiveSetBenefits(setCounts);
        characterBuilder
                .items(adjustedItems)
                .equippedSetBenefits(activeSetBenefits);

        // adjusting passive skill benefits
        characterBuilder.skills(adjustSkillsForPassives(skills, getEquippedItems(adjustedItems), activeSetBenefits));

        if(characterBuilder.isLordOfDestruction() || characterBuilder.isRiseOfTheWarlock()) {
            // the Classic characters don't have merc items, and don't store the iron golem item in the savegame file (it even disappears when switching acts!)

            // for iron lem and merc we'll search backwards as that is faster.
            // iron golem starts with kf and in case the following byte is 1 the item will follow without a JM prefix
            byte[] ironLemHeaderBytes = new byte[2];
            int ironIndex = -1;
            for (int i = buffer.limit() - 3; i > deadBodyIndex; i--) {
                buffer.get(i, ironLemHeaderBytes, 0, 2);
                if("kf".equals(new String(ironLemHeaderBytes))) {
                    ironIndex = i;
                    break;
                }
            }
            if (ironIndex == -1) {
                throw new ParseException("Could not find iron golem header 'kf'");
            }

            // merc items are at "jf"
            byte[] mercItemHeaderBytes = new byte[2];
            int mercItemIndex = -1;
            for (int i = ironIndex; i > deadBodyIndex; i--) {
                buffer.get(i, mercItemHeaderBytes, 0, 2);
                if("jf".equals(new String(mercItemHeaderBytes))) {
                    mercItemIndex = i + 2;
                    break;
                }
            }

            if (ironIndex > mercItemIndex) {
                log.debug("parsing mercenary at index {}", mercItemIndex);
                final List<Item> mercItems = itemParser.parseItems(buffer, mercItemIndex, ironIndex);
                final HashMap<String, Integer> mercSetCounts = getEquippedSetCounts(getEquippedSetItems(mercItems));
                final List<Item> mercAdjustedItems = removeSetBonuses(mercItems, mercSetCounts);
                mercenaryBuilder.items(mercAdjustedItems);
                characterBuilder.mercenary(mercenaryBuilder.build());
            } else {
                log.debug("No mercenary found");
            }

            // parse the iron golem item
            if (buffer.get(ironIndex + 2) == 1) {
                final int ironItemLength = buffer.limit() - ironIndex - 3;
                byte[] ironBytes = new byte[ironItemLength];
                buffer.get(ironIndex + 3, ironBytes, 0, ironItemLength);
                BitReader igBR = new BitReader(ironBytes);
                characterBuilder.golemItem(itemParser.parseItem(igBR));
            }
        }

        final short deadIndicator = buffer.getShort(deadBodyIndex + 2);
        if (deadIndicator == 1) {
            log.debug("dead body items found at index {}", deadBodyIndex);
            characterBuilder.deadBodyItems(itemParser.parseItems(buffer, deadBodyIndex + 16, buffer.limit()));
        } else {
            log.debug("No dead body items found");
        }

        return characterBuilder.build();
    }

    private int findNextJM(ByteBuffer buffer, int start) {
        // skip 16 bytes, look for the next one:
        byte[] nextJM = new byte[2];
        for (int i = start; i < buffer.limit(); i++) {
            buffer.get(i, nextJM, 0, 2);
            if ("JM".equals(new String(nextJM))) {
                return i;
            }
        }
        return -1;
    }

    private static FileData getFileData(ByteBuffer buffer) {
        return new FileData(
                buffer.getInt(4),   // file version
                buffer.getInt(8),   // file size
                buffer.getInt(48) * 1000L);  // timestamp seconds since Jan 1st, 1970 so * 1000L to get the proper unix value
    }

    private List<Skill> parseSkills(final CharacterType characterType, final byte[] skillBytes) {
        List<Skill> skills = new ArrayList<>();
        int index = 0;
        for(SkillType skillType: SkillType.getSkillListForCharacter(characterType)) {
            byte level = skillBytes[index];
            index++;

            skills.add(new Skill(skillType, level, List.of()));
        }

        return List.copyOf(skills);
    }

    private List<Skill> adjustSkillsForPassives(final List<Skill> skills, final List<Item> equippedItems, final List<ItemProperty> equippedSetBenefits) {
        List<Skill> adjusted = new ArrayList<>();
        for (Skill skill: skills) {
            byte level = skill.level();
            List<ItemProperty> passiveBonuses =
                    switch (skill.skillType()) {
                        case RESIST_FIRE -> List.of(new ItemProperty(40, "maxfireresist", new int[]{(level / 2), 0, 0}, 0, 0));
                        case RESIST_COLD -> List.of(new ItemProperty(44, "maxcoldresist", new int[]{(level / 2), 0, 0}, 0, 0));
                        case RESIST_LIGHTNING -> List.of(new ItemProperty(42, "maxlightresist", new int[]{(level / 2), 0, 0}, 0, 0));
                        case NATURAL_RESISTANCE -> getBarbNaturalResistanceStats(level, equippedItems, equippedSetBenefits);
                        default -> List.of();
                    };
            adjusted.add(new Skill(skill.skillType(), level, passiveBonuses));
        }
        return List.copyOf(adjusted);
    }

    private List<ItemProperty> getBarbNaturalResistanceStats(final byte skillLevel, final List<Item> equippedItems, final List<ItemProperty> equippedSetBenefits) {
        int equippedBonusLevels = 0;
        for (Item item: equippedItems) {
            equippedBonusLevels += getEquippedBonusLevelsForBarbCombatMasteries(item.properties());
        }
        equippedBonusLevels += getEquippedBonusLevelsForBarbCombatMasteries(equippedSetBenefits);
        return getBarbNaturalResistanceStatsByLevel(skillLevel + equippedBonusLevels);
    }

    private static int getEquippedBonusLevelsForBarbCombatMasteries(List<ItemProperty> itemProperties) {
        int equippedBonusLevels = 0;
        for(ItemProperty itemProperty: itemProperties) {
           if(itemProperty.name().equals("item_allskills")) {
               equippedBonusLevels += itemProperty.values()[0];
           } else if (itemProperty.name().equals("item_addskill_tab")
                   && itemProperty.values()[0] == 13) { // Barb combat masteries tab
               equippedBonusLevels += itemProperty.values()[1];
           }
        }
        return equippedBonusLevels;
    }

    private List<ItemProperty> getBarbNaturalResistanceStatsByLevel(final int skillLevel) {
        // source https://diablo2.io/skills/natural-resistance-t4125.html
        int[] resistancePerLevel = {0, 12, 21, 28, 35, 40, 44, 47, 49, 52, 54,
                                    56, 58, 60, 61, 62, 64, 64, 65, 66, 67,
                                    68, 68, 69, 70, 70, 71, 72, 72, 72, 72,
                                    73, 73, 74, 74, 75, 75, 75, 76, 76, 76,
                                    76, 76, 76, 76, 77, 77, 77, 77, 78, 78,
                                    78, 78, 78, 79, 79, 79, 79, 79, 79, 80};
        final int resistance = resistancePerLevel[Math.min(skillLevel, 60)];

        return List.of(
                new ItemProperty(39, "fireresist", new int[]{resistance, 0, 0}, 0, 0),
                new ItemProperty(41, "lightresist", new int[]{resistance, 0, 0}, 0, 0),
                new ItemProperty(43, "coldresist", new int[]{resistance, 0, 0}, 0, 0),
                new ItemProperty(45, "poisonresist", new int[]{resistance, 0, 0}, 0, 0)
        );
    }

    private List<WaypointStatus> parseWaypoints(ByteBuffer buffer) {
        List<WaypointStatus> result = new ArrayList<>();
        byte[] wsHeaderBytes = new byte[2];
        buffer.get(701, wsHeaderBytes, 0, 2);
        String wsHeader = new String(wsHeaderBytes); // "WS"
        if (!"WS".equals(wsHeader)) {
            throw new ParseException("Could not find Waypoints header");
        }
        // skip 6 unknown bytes to end up at 641 for normal. We read 24 bytes here per difficulty, even though the last 17 are (currently) not used.
        for (Difficulty difficulty : Difficulty.values()) {
            byte[] waypointBytes = new byte[24];
            int startIndex = 709 + difficulty.ordinal() * 24;
            buffer.get(startIndex, waypointBytes, 0, 24);
            BitReader brWaypoints = new BitReader(waypointBytes);
            brWaypoints.skip(16); // ignore the first two

            WaypointStatus.WaypointStatusBuilder waypointBuilder = new WaypointStatus.WaypointStatusBuilder()
                    .act1RogueEncampment(brWaypoints.readInt(1) != 0)
                    .act1ColdPlains(brWaypoints.readInt(1) != 0)
                    .act1StonyField(brWaypoints.readInt(1) != 0)
                    .act1DarkWood(brWaypoints.readInt(1) != 0)
                    .act1BlackMarsh(brWaypoints.readInt(1) != 0)
                    .act1OuterCloister(brWaypoints.readInt(1) != 0)
                    .act1Jail(brWaypoints.readInt(1) != 0)
                    .act1InnerCloister(brWaypoints.readInt(1) != 0)
                    .act1Catacombs(brWaypoints.readInt(1) != 0)

                    .act2LutGholein(brWaypoints.readInt(1) != 0)
                    .act2Sewers(brWaypoints.readInt(1) != 0)
                    .act2DryHills(brWaypoints.readInt(1) != 0)
                    .act2HallsOfTheDead(brWaypoints.readInt(1) != 0)
                    .act2FarOasis(brWaypoints.readInt(1) != 0)
                    .act2LostCity(brWaypoints.readInt(1) != 0)
                    .act2PalaceCellar(brWaypoints.readInt(1) != 0)
                    .act2ArcaneSanctuary(brWaypoints.readInt(1) != 0)
                    .act2CanyonOfTheMagi(brWaypoints.readInt(1) != 0)

                    .act3KurastDocks(brWaypoints.readInt(1) != 0)
                    .act3SpiderForest(brWaypoints.readInt(1) != 0)
                    .act3GreatMarsh(brWaypoints.readInt(1) != 0)
                    .act3FlayerJungle(brWaypoints.readInt(1) != 0)
                    .act3LowerKurast(brWaypoints.readInt(1) != 0)
                    .act3KurastBazaar(brWaypoints.readInt(1) != 0)
                    .act3UpperKurast(brWaypoints.readInt(1) != 0)
                    .act3Travincal(brWaypoints.readInt(1) != 0)
                    .act3DuranceOfHate(brWaypoints.readInt(1) != 0)

                    .act4PandemoniumFortress(brWaypoints.readInt(1) != 0)
                    .act4CityOfTheDamned(brWaypoints.readInt(1) != 0)
                    .act4RiverOfFlames(brWaypoints.readInt(1) != 0)

                    .act5Harrogath(brWaypoints.readInt(1) != 0)
                    .act5FrigidHighlands(brWaypoints.readInt(1) != 0)
                    .act5ArreatPlateau(brWaypoints.readInt(1) != 0)
                    .act5CrystallinePassage(brWaypoints.readInt(1) != 0)
                    .act5HallsOfPain(brWaypoints.readInt(1) != 0)
                    .act5GlacialTrail(brWaypoints.readInt(1) != 0)
                    .act5FrozenTundra(brWaypoints.readInt(1) != 0)
                    .act5TheAncientsWay(brWaypoints.readInt(1) != 0)
                    .act5WorldstoneKeep(brWaypoints.readInt(1) != 0);

            result.add(waypointBuilder.build());
        }

        return result;
    }

    private Location parseLocation(Difficulty difficulty, byte input) {
        boolean active = (0xff & input & -1 << 7) != 0;
        int currentAct = (input & 7) + 1;
        return new Location(active, currentAct, difficulty);
    }

    private List<QuestData> parseQuestData(ByteBuffer buffer) {
        List<QuestData> result = new ArrayList<>();
        byte[] questHeaderBytes = new byte[4];
        buffer.get(QUEST_START_INDEX, questHeaderBytes, 0, 4);
        String questHeader = new String(questHeaderBytes);
        if (!"Woo!".equals(questHeader)) {
            throw new ParseException("Could not find quest header");
        }

        for (Difficulty difficulty : Difficulty.values()) {
            // for now, we are only interested in Anya's scroll and Larzuk's socket quest reward still available
            QuestData.QuestDataBuilder questDataBuilder = new QuestData.QuestDataBuilder(difficulty);

            int larzukIndex = QUEST_START_INDEX + 10 + 70 + difficulty.ordinal() * 96;
            short larzuk = buffer.getShort(larzukIndex);
            // 01000100 socket reward available
            // 10000100 socket quest used
            boolean bit0 = (larzuk & 1) != 0;
            boolean bit1 = (larzuk & (1 << 1)) != 0;
            boolean bit5 = (larzuk & (1 << 5)) != 0;
            questDataBuilder
                    .socketQuestStarted(larzuk != 0)
                    .socketQuestRewardAvailable(bit1 && bit5)
                    .socketQuestUsed(bit0 && bit5);

            int anyaIndex = QUEST_START_INDEX + 10 + 74 + difficulty.ordinal() * 96;
            short anya = buffer.getShort(anyaIndex);
            questDataBuilder.resistanceScrollRead((anya & (1 << 7)) != 0);

            result.add(questDataBuilder.build());
        }

        return result;
    }

    private HashMap<String, Integer> getEquippedSetCounts(List<Item> setItems) {
        final HashMap<String, List<String>> uniqueSetItems = new HashMap<>();
        for(Item item: setItems) {
            List<String> names = uniqueSetItems.getOrDefault(item.setName(), new ArrayList<>());
            if (!names.contains(item.itemName())
                    && item.location() == ItemLocation.EQUIPPED // we only get the bonus when equipped... but rings can be equipped twice
                    && item.position() != ItemPosition.LEFT_SWAP && item.position() != ItemPosition.RIGHT_SWAP // we also ignored any items on swap
            ) {
                names.add(item.itemName());
            }
            uniqueSetItems.put(item.setName(), names);
        }

        final HashMap<String, Integer> setCounts = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : uniqueSetItems.entrySet()) {
            setCounts.put(entry.getKey(), entry.getValue().size());
        }
        return setCounts;
    }

    private List<Item> removeSetBonuses(List<Item> originalItems, HashMap<String, Integer> setCounts) {
        List<Item> adjustedItems = new ArrayList<>();
        // clean up the set properties on the items themselves that are not available yet due to a too low count or not being equipped
        for(Item item: originalItems) {
            if (item.quality() != ItemQuality.SET) {
                adjustedItems.add(item);
                continue;
            }

            final int count;
            if (item.location() == ItemLocation.EQUIPPED) {
                count = setCounts.getOrDefault(item.setName(), 1);
            } else {
                log.debug("Item {} not equipped, not adding set bonuses by resetting the set item count to 1 for this one", item.itemName());
                count = 1;
            }

            List<ItemProperty> keepThese = item.properties().stream()
                    .filter(itemProperty -> itemProperty.qualityFlag() <= count)
                    .toList();
            log.debug("adjusting set {} item {}, remaining properties: {}", item.setName(), item.itemName(), keepThese.size());

            adjustedItems.add(new Item(item.isIdentified(), item.isSocketed(), item.isEar(),
                    item.isSimple(), item.isEthereal(), item.isPersonalized(), item.isRuneword(), item.isThrown(), item.isTwoHanded(),
                    item.version(), item.x(), item.y(), item.code(), item.type(), item.type2(), item.itemType(),
                    item.cntSockets(), item.cntFilledSockets(), item.fingerPrint(), item.guid(), item.level(), item.pictureId(),
                    item.prefixIds(), item.suffixIds(), item.setItemId(), item.uniqueId(), item.rareNameId1(), item.rareNameId2(),
                    item.itemName(), item.setName(), item.personalizedName(), item.baseDefense(), item.maxDurability(), item.durability(),
                    item.stacks(), item.maxStacks(), item.reqStr(), item.reqDex(), item.reqLvl(), item.restrictedToClass(), keepThese,
                    item.socketedItems(), item.location(), item.quality(), item.position(), item.container(), item.treasureClass(),
                    item.tomeId(), item.invWidth(), item.invHeight()));
        }
        return adjustedItems;
    }

    private List<ItemProperty> getActiveSetBenefits(HashMap<String, Integer> setCounts) {
        List<ItemProperty> setBenefits = new ArrayList<>();

        // add global set benefits (the gold coloured ones)
        for (String setName: setCounts.keySet()) {
            int totalItems = setCounts.getOrDefault(setName, 0);
            if (totalItems > 1) {
                final SetData setWithMultipleItems = TXTProperties.getInstance().getSetDataByName(setName);
                setBenefits.addAll(setWithMultipleItems.getPartialBonuses().stream()
                        .filter(itemProperty -> itemProperty.qualityFlag() <= (20 + totalItems))
                        .toList());
                if (totalItems == setWithMultipleItems.getItemIDs().size()) { // complete set
                    setBenefits.addAll(setWithMultipleItems.getFullBonuses());
                }
            }
        }

        return setBenefits;
    }

    private List<Item> getEquippedSetItems(List<Item> items) {
        return items.stream()
                .filter(item -> item.location() == ItemLocation.EQUIPPED && item.quality() == ItemQuality.SET)
                .toList();
    }

    private List<Item> getEquippedItems(List<Item> items) {
        return items.stream()
                .filter(item -> item.location() == ItemLocation.EQUIPPED)
                .toList();
    }
}
