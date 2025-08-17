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

import io.github.paladijn.d2rsavegameparser.internal.parser.BitReader;
import io.github.paladijn.d2rsavegameparser.internal.parser.ItemScaffolding;
import io.github.paladijn.d2rsavegameparser.internal.parser.ParseHelper;
import io.github.paladijn.d2rsavegameparser.model.CharacterType;
import io.github.paladijn.d2rsavegameparser.model.Item;
import io.github.paladijn.d2rsavegameparser.model.ItemContainer;
import io.github.paladijn.d2rsavegameparser.model.ItemLocation;
import io.github.paladijn.d2rsavegameparser.model.ItemPosition;
import io.github.paladijn.d2rsavegameparser.model.ItemProperty;
import io.github.paladijn.d2rsavegameparser.model.ItemQuality;
import io.github.paladijn.d2rsavegameparser.model.ItemType;
import io.github.paladijn.d2rsavegameparser.txt.ArmorStats;
import io.github.paladijn.d2rsavegameparser.txt.ItemStatCost;
import io.github.paladijn.d2rsavegameparser.txt.MagicAffix;
import io.github.paladijn.d2rsavegameparser.txt.MiscStats;
import io.github.paladijn.d2rsavegameparser.txt.SetItem;
import io.github.paladijn.d2rsavegameparser.txt.TXTProperties;
import io.github.paladijn.d2rsavegameparser.txt.UniqueItem;
import io.github.paladijn.d2rsavegameparser.txt.WeaponStats;
import org.slf4j.Logger;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Internal parser for Diablo II {@link Item}s, either in as a full list or a singular one from an array of bytes.
 *
 * @author Paladijn
 */
final class ItemParser {
    private static final Logger log = getLogger(ItemParser.class);

    private final boolean printItemBytes;

    private final TXTProperties txtProperties = TXTProperties.getInstance();

    /**
     * Constructor for the {@link ItemParser}
     * @param printItemBytes helper boolean to log the bytes[] per Item for unit tests and debugging.
     */
    ItemParser(boolean printItemBytes) {
        this.printItemBytes = printItemBytes;
    }

    /**
     * Parse a list of {@link Item}s from the {@link ByteBuffer}. The array has to start with "JM".
     * The method will throw a {@link ParseException} in case the buffer doesn't start with "JM", or in case there was an issue parsing one of the items in the buffer.
     *
     * @param buffer a {@link ByteBuffer} of a savegame file starting at the character, mercenary or dead body itemlist or a shared stash tab
     * @param start start index in the buffer
     * @param end last index of the buffer to parse
     * @return a list of {@link Item}s
     *
     */
    List<Item> parseItems(ByteBuffer buffer, int start, int end) {
        final List<Item> result = new ArrayList<>();
        int cntItemBytes = end - start - 4;
        byte[] itemBytes = new byte[cntItemBytes];
        buffer.get(start + 4, itemBytes, 0, cntItemBytes);

        byte[] itemHeaderBytes = new byte[2];
        buffer.get(start, itemHeaderBytes, 0, 2);
        String itemHeader = new String(itemHeaderBytes);
        if (!itemHeader.equals("JM")) {
            throw new ParseException("Problem parsing item header (should be JM): " + itemHeader);
        }

        int cntItems = buffer.getShort(start + 2);
        log.debug("Total items: {}", cntItems);

        final BitReader itemData = new BitReader(itemBytes);
        for (int i = 0; i < cntItems; i++) {
            result.add(parseItem(itemData));
        }

        return result;
    }

    /**
     * Parse one {@link Item} using the {@link BitReader}. As the bits are not byte-aligned in each item we'll need to parse them bit by bit.
     * Will throw a {@link ParseException} in case the bits did not result in a valid item.
     *
     * @param br a {@link BitReader} containing all bytes.
     * @return the parsed {@link Item}
     */
    Item parseItem(final BitReader br) {
        int startIndex = br.getPositionInBits() / 8;

        int flags = br.readFlippedInt(32);
        Item.ItemBuilder itemBuilder = new Item.ItemBuilder()
                .identified(isBitChecked(flags, 5));

        final boolean isSocketed = isBitChecked(flags, 12);
        final boolean isEar = isBitChecked(flags, 17);
        final boolean isSimple = isBitChecked(flags, 22);
        final boolean isEthereal = isBitChecked(flags, 23);

        itemBuilder
                .socketed(isSocketed)
                .ear(isEar)
                .simple(isSimple)
                .ethereal(isEthereal);

        boolean isPersonalized = isBitChecked(flags, 25);
        itemBuilder.personalized(isPersonalized);
        boolean isRuneword = isBitChecked(flags, 27);
        itemBuilder.runeword(isRuneword);

        br.skip(3);
        itemBuilder
                .location(ItemLocation.findByValue(br.readShort(3)))
                .position(ItemPosition.findByValue(br.readShort(4)))
                .y(br.readShort(4))
                .x(br.readShort(4))
                .container(ItemContainer.findByValue(br.readShort(3)));

        if (isEar) {
            parseEar(br);
            return itemBuilder.build();
        }

        final String code = br.readHuffmanEncodedString();

        ArmorStats armorStats = txtProperties.getArmorStatsByCode(code);
        WeaponStats weaponStats = txtProperties.getWeaponStatsByCode(code);
        MiscStats miscStats = txtProperties.getMiscItemsByCode(code);

        ItemType itemType = determineItemType(armorStats, weaponStats, miscStats);

        final ItemScaffolding itemScaffolding = getBasicItemStats(code, itemType, armorStats, weaponStats, miscStats, isPersonalized, isRuneword, isSocketed, isEthereal);

        itemBuilder
                .code(code)
                .itemName(itemScaffolding.getItemName())
                .type(itemScaffolding.getType())
                .type2(itemScaffolding.getType2())
                .reqLvl(itemScaffolding.getReqLvl())
                .reqStr(itemScaffolding.getReqStr())
                .reqDex(itemScaffolding.getReqDex())
                .invWidth(itemScaffolding.getInvWidth())
                .invHeight(itemScaffolding.getInvHeight())
                .treasureClass(txtProperties.getTreasureClass(itemScaffolding.getItemName()))
                .itemType(itemType);

        log.debug("code {} [{}, {}]", itemScaffolding.getCode(), itemScaffolding.getType(), itemScaffolding.getType2());

        if (!isSimple) {
            parseExtendedPart1(itemBuilder, itemScaffolding, br);
        }

        if(br.readShort(1) == 1) { // has GUID
            parseGUID(br, itemScaffolding, miscStats == null, itemBuilder);
        }

        if (!isSimple) {
            parseExtendedPart2(itemBuilder, itemScaffolding, br);
        }

        if (Item.isGem(itemScaffolding.getType(), itemScaffolding.getType2()) || Item.isRune(itemScaffolding.getType())) {
            itemBuilder.addProperties(txtProperties.getGemsAndRunesByCode(code).getAllProperties());
        }

        log.debug("item {} done, moving to the next", itemScaffolding.getItemName());
        // extra skip for special cases
        if (isSimple
                && "ques".equals(itemScaffolding.getType())
                && ("j34".equals(code) || "bkd".equals(code))
                && br.bitsToNextBoundary() == 0) {
            log.info("Skipping 8 extra bits on {} for specific quest items such as a Jade Figurine as they end on a boundary and result in parse errors when not skipping a byte.", itemScaffolding.getItemName());
            br.skip(8);
        }
        br.moveToNextByteBoundary();

        final Item result = itemBuilder.build();
        boolean printBytesDueToError = false;

        if (result.prefixIds().stream().anyMatch(id -> id == 2047)) {
            log.error("Item has at least one unknown prefixId at 2047, likely an adjusted or hacked item!");
            printBytesDueToError = true;
        }

        if (result.suffixIds().stream().anyMatch(id -> id == 2047)) {
            log.error("Item has at least one unknown suffixId at 2047, likely an adjusted or hacked item!");
            printBytesDueToError = true;
        }

        if (printItemBytes || printBytesDueToError) {
            br.printBytes(result, startIndex);
        }


        return result;
    }

    private void parseEar(BitReader br) {
        // as documented on https://user.xmission.com/~trevin/DiabloIIv1.09_Item_Format.shtml#ear
        // we have no way to test this, as these will only exist on imported (open battle.net) characters from the original game
        final short characterClass = br.readShort(3);
        final short characterLevel = br.readShort(7);

        // read the name
        StringBuilder sbFormerOwner = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            char c = br.readChar(7);
            if (c == 0) {
                break;
            } else {
                sbFormerOwner.append(c);
            }
        }
        br.moveToNextByteBoundary();

        // we only log the contents for now, perhaps store this as item data in the future
        log.info("Ear found of lvl {} {} {}", characterLevel, CharacterType.values()[characterClass], sbFormerOwner);
    }

    private void parseExtendedPart1(Item.ItemBuilder itemBuilder, ItemScaffolding itemScaffolding, BitReader br) {
        log.debug("parseExtendedPart1 index: {}", br.getPositionInBits());
        itemScaffolding.setCntFilledSockets(br.readShort(3));
        itemBuilder.cntFilledSockets(itemScaffolding.getCntFilledSockets());

        int fingerprint = br.readInt();
        itemBuilder
                .fingerPrint("0x" + Integer.toHexString(fingerprint))
                .level(br.readShort(7));

        final ItemQuality itemQuality = ItemQuality.findByValue(br.readShort(4));
        itemScaffolding.setQuality(itemQuality);
        itemBuilder.quality(itemQuality);

        if (br.readShort(1) == 1) { // picture flag
            itemBuilder.pictureId(br.readShort(3));
        }

        if (br.readShort(1) == 1) {// class specific item flag
            int classSpecificModInfo = br.readInt(11);
            log.debug("class specific item flag {}", classSpecificModInfo);
            // we won't do anything with these bits. They are used in-game to determine if you can equip it on your character.
        }

        switch (itemQuality) {
            case INFERIOR -> parseInferior(itemBuilder, itemScaffolding, br);
            case NORMAL -> parseNormal(itemBuilder, itemScaffolding.getCode(), br);
            case SUPERIOR -> parseSuperior(br);
            case MAGIC -> parseMagical(itemBuilder, itemScaffolding, br);
            case SET -> parseSetItem(itemBuilder, itemScaffolding, br);
            case RARE -> parseRare(itemBuilder, itemScaffolding, br);
            case UNIQUE -> parseUnique(itemBuilder, br);
            case CRAFT -> parseCrafted(itemBuilder, itemScaffolding, br);
            case NONE, UNKNOWN -> {
                log.error("No or unknown quality for this item: {}", itemBuilder.build());
                throw new ParseException("unknown quality for item");
            }
        }

        if (itemScaffolding.isRuneword()) {
            br.skip(12); // TODO document what these are for, or always skip 16.
            br.skip(4);
            log.debug("index after skipping 16 bits in runeword: {}", br.getPositionInBits());
        }

        if (itemScaffolding.isPersonalized()) {
            personalizeItem(itemBuilder, itemScaffolding, br);
        }
    }

    private void parseExtendedPart2(Item.ItemBuilder itemBuilder, ItemScaffolding itemScaffolding, BitReader br) {
        log.debug("parseExtendedPart2 index: {}", br.getPositionInBits());

        switch (itemScaffolding.getItemType()) {
            case ItemType.ARMOR -> parseArmorStats(itemBuilder, br);
            case ItemType.WEAPON -> parseWeaponStats(itemBuilder, itemScaffolding, br, txtProperties.getWeaponStatsByCode(itemScaffolding.getCode()));
            case ItemType.MISC -> parseMiscStats(itemBuilder, br, txtProperties.getMiscItemsByCode(itemScaffolding.getCode()));
        }

        if (itemScaffolding.isSocketed()) {
            short cntSockets = br.readShort(4);
            itemBuilder.cntSockets(cntSockets);
            log.debug("read total sockets: in item {}", cntSockets);
        }

        int[] lSet = new int[5];

        if (itemScaffolding.getItemQuality() == ItemQuality.SET) {
            for (int i = 0; i < 5; i++) {
                lSet[i] = br.readInt(1);
            }
        }

        itemBuilder.addProperties(readProperties(br, Item.isJewel(itemScaffolding.getCode()) ? 1 : 0));

        if (itemScaffolding.getItemQuality() == ItemQuality.SET) {
            parseSetProperties(itemBuilder, br, lSet);
        }

        if (itemScaffolding.isRuneword()) {
            itemBuilder.addProperties(readProperties(br, 0));
        }

        if (itemScaffolding.getCntFilledSockets() > 0) {
            parseSocketedItems(itemBuilder, itemScaffolding, br);
        }

        if (itemScaffolding.isRuneword()) {
            log.debug("Looking up runeword name");
            final String runes = itemScaffolding.getSocketedItems().stream()
                    .map(item -> item.itemName().replace(" Rune", ""))
                    .collect(Collectors.joining());

            txtProperties.getRunewords().stream()
                    .filter(rw -> rw.isThisARuneword(runes))
                    .findFirst()
                    .ifPresent(value -> itemBuilder.itemName(value.getName()));
        }

        if (itemScaffolding.getCntFilledSockets() > 0
                && itemScaffolding.getItemQuality() == ItemQuality.NORMAL
                && !itemScaffolding.isRuneword()) {
            itemBuilder.itemName("%s %s".formatted("Gemmed", itemScaffolding.getItemName()));
        }

        if (itemScaffolding.isEthereal()) {
            adjustForEthereal(itemBuilder, itemScaffolding);
        }
    }

    private void parseSetProperties(Item.ItemBuilder itemBuilder, BitReader br, int[] lSet) {
        for (int i = 0; i < 5; i++) {
            if (lSet[i] == 1) {
                itemBuilder.addProperties(readProperties(br, i + 2));
            }
        }
    }

    private static void personalizeItem(Item.ItemBuilder itemBuilder, ItemScaffolding itemScaffolding, BitReader br) {
        StringBuilder sbPersonalization = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            char c = br.readChar(8);
            if (c == 0) {
                break;
            } else {
                sbPersonalization.append(c);
            }
        }

        final String personalizedName = sbPersonalization.toString();
        itemBuilder.personalizedName(personalizedName);
        if (!itemScaffolding.isRuneword()) { // Runewords are on odd duck: the item will become "Personalized's Leather Armor" with the name Stealth.
            final String personalizedFormat = personalizedName.endsWith("s") ? "%s' %s" : "%s's %s";
            itemBuilder.itemName(personalizedFormat.formatted(personalizedName, itemScaffolding.getItemName()));
        }
    }

    private static void adjustForEthereal(Item.ItemBuilder itemBuilder, ItemScaffolding itemScaffolding) {
        log.debug("Ethereal item, adjusting str/dex req");
        if (itemScaffolding.getReqStr() > 0) {
            itemBuilder.reqStr(itemScaffolding.getReqStr() - 10);
        }
        if (itemScaffolding.getReqDex() > 0) {
            itemBuilder.reqDex(itemScaffolding.getReqDex() - 10);
        }
    }

    private void parseWeaponStats(Item.ItemBuilder itemBuilder, ItemScaffolding itemScaffolding, BitReader br, WeaponStats weaponStats) {
        itemBuilder.twohanded(weaponStats.isTwoHanded());
        itemBuilder.thrown(weaponStats.isThrown());
        final short maxDurability = br.readShort(8);

        if (maxDurability != 0) {
            itemBuilder
                    .maxDurability(maxDurability)
                    .durability(br.readShort(9));
        }

        if (weaponStats.isStackable()) {
            itemBuilder.stacks(br.readShort(9));
        }

        if ("club".equals(itemScaffolding.getType())
                || "scep".equals(itemScaffolding.getType())
                || "mace".equals(itemScaffolding.getType())
                || "hamm".equals(itemScaffolding.getType())) {
            final ItemStatCost isc = txtProperties.getItemStatCostsByID(ParseHelper.PROPERTY_UNDEAD_DMG);
            final ItemProperty bluntProperty = new ItemProperty(isc.getId(), isc.getStat(), new int[]{50}, 0, isc.getDescPriority());
            itemBuilder.addProperty(bluntProperty);
        }
    }

    private static void parseArmorStats(Item.ItemBuilder itemBuilder, BitReader br) {
        itemBuilder.baseDefense(br.readShort(11) - 10);
        final short maxDurability = br.readShort(8);

        if (maxDurability != 0) {
            itemBuilder
                    .maxDurability(maxDurability)
                    .durability(br.readShort(9));
        }
    }

    private void parseMiscStats(Item.ItemBuilder itemBuilder, BitReader br, MiscStats miscStats) {
        if (miscStats.isStackable()) {
            itemBuilder.stacks(br.readShort(9));
        }
    }

    private static void parseGUID(BitReader br, ItemScaffolding itemScaffolding, boolean noMiscItem, Item.ItemBuilder itemBuilder) {
        if (Item.isRune(itemScaffolding.getType())
                || itemScaffolding.getType().startsWith("gem")
                || itemScaffolding.getType().startsWith("amu")
                || itemScaffolding.getType().startsWith("rin")
                || Item.isCharm(itemScaffolding.getCode())
                || noMiscItem) {

            String guid = "0x" + Integer.toHexString(br.readInt())
                    + " 0x" + Integer.toHexString(br.readInt())
                    + " 0x" + Integer.toHexString(br.readInt())
                    + " 0x" + Integer.toHexString(br.readInt());
            itemBuilder.guid(guid);
        } else {
            // this broke the scroll of Inifuss, as it pushed 1 bit into the next item. leaving this off for now to see if it breaks anything
            log.warn("If something breaks, it's caused by not skipping 2-3 bits below this line on item {}.", itemScaffolding.getItemName());
            if (!itemScaffolding.getCode().equals("bks")) {
                br.skip(3);
            }
        }
    }

    private void parseSocketedItems(Item.ItemBuilder itemBuilder, ItemScaffolding itemScaffolding, BitReader br) {
        log.debug("parsing {} filled sockets", itemScaffolding.getCntFilledSockets());
        List<Item> socketedItems = new ArrayList<>();

        br.moveToNextByteBoundary();
        for (int i = 0; i < itemScaffolding.getCntFilledSockets(); i++) {
            Item socketedItem = parseItem(br); // read the socketed sub-item

            if (Item.isJewel(socketedItem.code())) {
                itemBuilder.addProperties(filterPropertiesByQuality(socketedItem.properties(), 1));
            } else if (itemScaffolding.getItemType() == ItemType.WEAPON) {
                itemBuilder.addProperties(filterPropertiesByQuality(socketedItem.properties(), 7));
            } else if (itemScaffolding.getItemType() == ItemType.ARMOR) {
                if (Item.isHelmetOrTorso(itemScaffolding.getType())) {
                    itemBuilder.addProperties(filterPropertiesByQuality(socketedItem.properties(), 8));
                } else {
                    itemBuilder.addProperties(filterPropertiesByQuality(socketedItem.properties(), 9));
                }
            }

            itemBuilder.addSocketedItem(socketedItem);
            socketedItems.add(socketedItem);
        }

        itemScaffolding.setSocketedItems(socketedItems);
    }

    private ItemType determineItemType(ArmorStats armorStats, WeaponStats weaponStats, MiscStats miscStats) {
        if (armorStats != null) {
            return ItemType.ARMOR;
        }
        if (weaponStats != null) {
            return ItemType.WEAPON;
        }
        if (miscStats != null) {
            return ItemType.MISC;
        }
        throw new ParseException("All item types are null, issue parsing 'item'");
    }

    private List<ItemProperty> readProperties(BitReader br, int qflag) {
        List<ItemProperty> properties = new ArrayList<>();
        int rootProp = br.readInt(9);
        if (rootProp == ParseHelper.PROPERTY_END) {
            log.debug("skipping properties due to value 511");
        }
        while (rootProp != ParseHelper.PROPERTY_END) {
            ItemProperty itemProperty = parseItemProperty(br, rootProp, qflag);
            log.debug("rootprop: {}, property: {}", rootProp, itemProperty);
            properties.add(itemProperty);
            if (rootProp == ParseHelper.PROPERTY_PHYS_MAX_DMG
                    || rootProp == ParseHelper.PROPERTY_FIRE_MIN_DMG
                    || rootProp == ParseHelper.PROPERTY_LIGHT_MIN_DMG
                    || rootProp == ParseHelper.PROPERTY_MAGIC_MIN_DMG) {
                properties.add(parseItemProperty(br, rootProp + 1, qflag));
            } else if (rootProp == ParseHelper.PROPERTY_COLD_MIN_DMG || rootProp == ParseHelper.PROPERTY_POISON_MIN_DMG) { // these also include the duration value
                properties.add(parseItemProperty(br, rootProp + 1, qflag));
                properties.add(parseItemProperty(br, rootProp + 2, qflag));
            }

            rootProp = br.readInt(9);
        }
        return properties;
    }

    private ItemProperty parseItemProperty(BitReader br, int rootProp, int qflag) {
        ItemStatCost itemStatCost = txtProperties.getItemStatCostsByID(rootProp);
        int length = itemStatCost.getSaveBits();
        int saveAdd = itemStatCost.getSaveAdd();

        log.debug("rootProp {}, readLength: {}, saveAdd: {}", rootProp, length, saveAdd);

        if (rootProp == ParseHelper.PROPERTY_SKILL_GET_HIT
                || rootProp == ParseHelper.PROPERTY_SKILL_DEATH
                || rootProp == ParseHelper.PROPERTY_SKILL_LEVEL_UP
                || rootProp == ParseHelper.PROPERTY_SKILL_ATTACK
                || rootProp == ParseHelper.PROPERTY_SKILL_HIT
                || rootProp == ParseHelper.PROPERTY_SKILL_KILL) {
            return new ItemProperty(rootProp, itemStatCost.getStat(), new int[]{br.readInt(6) - saveAdd, br.readInt(10) - saveAdd, br.readInt(length) - saveAdd}, qflag, itemStatCost.getDescPriority());
        } else if (rootProp == ParseHelper.PROPERTY_CHARGED_SKILL) {
            return new ItemProperty(rootProp, itemStatCost.getStat(), new int[]{br.readInt(6) - saveAdd, br.readInt(10) - saveAdd, br.readInt(8) - saveAdd, br.readInt(8) - saveAdd}, qflag, itemStatCost.getDescPriority());
        } else if (itemStatCost.getSaveParamBits() >= 0) {
            return new ItemProperty(rootProp, itemStatCost.getStat(), new int[]{br.readInt(itemStatCost.getSaveParamBits()) - saveAdd, br.readInt(length) - saveAdd}, qflag, itemStatCost.getDescPriority());
        }

        return new ItemProperty(rootProp, itemStatCost.getStat(), new int[]{br.readInt(length) - saveAdd}, qflag, itemStatCost.getDescPriority());
    }

    private ItemScaffolding getBasicItemStats(String code, ItemType itemType, ArmorStats armorStats, WeaponStats weaponStats, final MiscStats miscStats,
                                              final boolean isPersonalized, final boolean isRuneword, final boolean isSocketed, boolean isEthereal) {
        return switch (itemType) {
            case ARMOR -> new ItemScaffolding(code, armorStats.getName(), armorStats.getType(), armorStats.getType2(), armorStats.getReqStr(),
                    armorStats.getReqDex(), armorStats.getReqLvl(), isPersonalized, isRuneword, isSocketed, isEthereal, itemType, armorStats.getInvWidth(), armorStats.getInvHeight());
            case WEAPON -> new ItemScaffolding(code, weaponStats.getName(), weaponStats.getType(), weaponStats.getType2(), weaponStats.getReqStr(),
                    weaponStats.getReqDex(), weaponStats.getReqLvl(), isPersonalized, isRuneword, isSocketed, isEthereal, itemType, weaponStats.getInvWidth(), weaponStats.getInvHeight());
            case MISC -> new ItemScaffolding(code, miscStats.getName(), miscStats.getType(), miscStats.getType2(), miscStats.getReqStr(),
                    miscStats.getReqStr(), miscStats.getReqLvl(), isPersonalized, isRuneword, isSocketed, isEthereal, itemType, miscStats.getInvWidth(), miscStats.getInvHeight());
        };
    }

    private List<ItemProperty> filterPropertiesByQuality(List<ItemProperty> properties, int qualityFlag) {
        return properties.stream()
                .filter(ip -> ip.qualityFlag() == qualityFlag)
                .toList();
    }

    private void parseNormal(Item.ItemBuilder itemBuilder, String code, BitReader br) {
        if (Item.isTome(code)) {
            itemBuilder.tomeId(br.readShort(5));
        }
    }

    private void parseRare(Item.ItemBuilder itemBuilder, ItemScaffolding itemScaffolding, BitReader br) {
        int reqLvl = itemScaffolding.getReqLvl();

        final short rareId1 = br.readShort(8);
        final short rareId2 = br.readShort(8);

        final String name1 = (rareId1 >= 156) ? txtProperties.getRarePrefixById(rareId1 - 156) : "DID_YOU_FORGET_TO_SET_THE_PREFIX_ID_SLIV";
        final String name2 = txtProperties.getRareSuffixById(rareId2 - 1);

        // read the pre- and suffixes in order
        for (int i = 0; i < 3; i++) {
            if (br.readShort(1) == 1) {
                reqLvl = parseRarePrefix(itemBuilder, itemScaffolding, br, reqLvl);
            }
            if (br.readShort(1) == 1) {
                reqLvl = parseRareSuffix(itemBuilder, itemScaffolding, br, reqLvl);
            }
        }

        itemBuilder
                .rareNameId1(rareId1)
                .rareNameId2(rareId2)
                .itemName("%s %s".formatted(name1, name2))
                .reqLvl(reqLvl);
    }

    private int parseRarePrefix(Item.ItemBuilder itemBuilder, ItemScaffolding itemScaffolding, BitReader br, int reqLvl) {
        short prefix = br.readShort(11);
        if (prefix != 0) {
            itemBuilder.addPrefixId(prefix);
            itemScaffolding.setPrefixIdSize(itemScaffolding.getPrefixIdSize() + 1);
            if (prefix == 2047) {
                log.error("Rare prefix (2047) too high! Very likely an adjusted/hacked item that lacks a prefix ingame.");
            } else {
                final MagicAffix magicPrefix = txtProperties.getMagicPrefix(prefix);
                if (magicPrefix.getReqLvl() > reqLvl) {
                    log.debug("new reqLvl {} due to prefix {}", reqLvl, magicPrefix);
                    reqLvl = magicPrefix.getReqLvl();
                }
            }
        }
        return reqLvl;
    }

    private int parseRareSuffix(Item.ItemBuilder itemBuilder, ItemScaffolding itemScaffolding, BitReader br, int reqLvl) {
        short suffix = br.readShort(11);
        if (suffix != 0) {
            itemBuilder.addSuffixId(suffix);
            itemScaffolding.setSuffixIdSize(itemScaffolding.getSuffixIdSize() + 1);
            if (suffix == 2047) {
                log.error("Rare suffix (2047) too high! Very likely an adjusted/hacked item that lacks a suffix ingame.");
            } else {
                final MagicAffix magicSuffix = txtProperties.getMagicSuffix(suffix);
                if (magicSuffix.getReqLvl() > reqLvl) {
                    log.debug("new reqLvl {} due to suffix {}", reqLvl, magicSuffix);
                    reqLvl = magicSuffix.getReqLvl();
                }
            }
        }
        return reqLvl;
    }

    private void parseCrafted(Item.ItemBuilder itemBuilder, ItemScaffolding itemScaffolding, BitReader br) {
        parseRare(itemBuilder, itemScaffolding, br);

        int newRequirementLvl = itemScaffolding.getReqLvl()
                + 10
                + (3 * itemScaffolding.getSuffixIdSize() + itemScaffolding.getPrefixIdSize());
        itemBuilder.reqLvl(newRequirementLvl);
    }

    private void parseUnique(Item.ItemBuilder itemBuilder, BitReader br) {
        short uniqueId = br.readShort(12);
        itemBuilder.uniqueId(uniqueId);
        final UniqueItem uniqueItem = txtProperties.getUniqueNameById(uniqueId);

        if (uniqueItem != null) {
            itemBuilder
                    .itemName(uniqueItem.getName())
                    .reqLvl(uniqueItem.getReqLvl())
                    .treasureClass(txtProperties.getTreasureClass(uniqueItem.getItemName()));
        }
    }

    private void parseSetItem(Item.ItemBuilder itemBuilder, ItemScaffolding itemScaffolding, BitReader br) {
        short setItemId = br.readShort(12);
        itemBuilder.setItemId(setItemId);

        final SetItem setItem = txtProperties.getSetItemById(setItemId);
        if(setItem != null) {
            itemBuilder
                    .itemName(setItem.getName())
                    .setName(setItem.getSetName())
                    .treasureClass(txtProperties.getTreasureClass(itemScaffolding.getItemName()));
        }
    }

    private void parseMagical(Item.ItemBuilder itemBuilder, ItemScaffolding itemScaffolding, BitReader br) {
        short prefix = br.readShort(11);
        String itemName = itemScaffolding.getItemName();
        int reqLvl = itemScaffolding.getReqLvl();
        CharacterType restricted = null;
        if (prefix != 0) {
            itemBuilder.addPrefixId(prefix);
            if (prefix == 2047) {
                log.error("Prefix (2047) too high! Very likely an adjusted/hacked item that lacks a prefix ingame.");
            } else {
                final MagicAffix magicPrefix = txtProperties.getMagicPrefix(prefix);
                itemName = "%s %s".formatted(magicPrefix.getName(), itemName);
                if (magicPrefix.getReqLvl() > reqLvl) {
                    reqLvl = magicPrefix.getReqLvl();
                }
                restricted = magicPrefix.getRestrictedToClass();
            }
        }
        short suffix = br.readShort(11);
        if (suffix != 0) {
            itemBuilder.addSuffixId(suffix);
            if (suffix == 2047) {
                log.error("Suffix (2047) too high! Very likely an adjusted/hacked item that lacks a suffix ingame.");
            } else {
                final MagicAffix magicSuffix = txtProperties.getMagicSuffix(suffix);
                itemName = "%s %s".formatted(itemName, magicSuffix.getName());
                if (magicSuffix.getReqLvl()> reqLvl) {
                    reqLvl = magicSuffix.getReqLvl();
                }
                if (restricted != null) {
                    restricted = magicSuffix.getRestrictedToClass();
                }
            }
        }

        itemBuilder
                .itemName(itemName)
                .reqLvl(reqLvl)
                .restrictedToClass(restricted);

        log.debug("Magic item name adjusted to \"{}\" for prefixId {} and suffixId {}", itemName, prefix, suffix);
    }

    private void parseSuperior(BitReader br) {
        short superiorQuality = br.readShort(3);
        log.debug("superior quality item value: {}", superiorQuality);
    }

    private void parseInferior(Item.ItemBuilder itemBuilder, ItemScaffolding itemScaffolding, BitReader br) {
        short lowQuality = br.readShort(3);

        switch (lowQuality) {
            case 0 -> itemBuilder.itemName("Crude " + itemScaffolding.getItemName());
            case 1 -> itemBuilder.itemName("Cracked " + itemScaffolding.getItemName());
            case 2 -> itemBuilder.itemName("Damaged " + itemScaffolding.getItemName());
            case 3 -> itemBuilder.itemName("Low Quality " + itemScaffolding.getItemName());
            default -> log.error("unknown low quality value: {}", lowQuality);
        }
    }

    private boolean isBitChecked(int bits, int index) {
        return ((bits >>> (32 - index)) & 1) == 1;
    }
}
