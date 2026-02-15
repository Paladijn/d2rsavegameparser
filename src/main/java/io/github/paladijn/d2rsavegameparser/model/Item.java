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

import io.github.paladijn.d2rsavegameparser.internal.parser.ReverseItemPropertyOrderComparator;

import java.util.ArrayList;
import java.util.List;

/**
 * Representation of a Diablo II item on a character or mercenary.
 *
 * @author Paladijn
 */
public record Item(boolean isIdentified, boolean isSocketed, boolean isEar,
                   boolean isSimple, boolean isEthereal, boolean isPersonalized, boolean isRuneword, boolean isThrown, boolean isTwoHanded,
                   int version, short x, short y, String code, String type, String type2, ItemType itemType,
                   short cntSockets, short cntFilledSockets, String fingerPrint, String guid, short level,
                   short pictureId, List<Short> prefixIds, List<Short> suffixIds, short setItemId, short uniqueId, short rareNameId1,
                   short rareNameId2, String itemName, String setName, String personalizedName, int baseDefense, short maxDurability,
                   short durability, short stacks, int maxStacks, int reqStr, int reqDex, int reqLvl, CharacterType restrictedToClass,
                   List<ItemProperty> properties, List<Item> socketedItems, ItemLocation location, ItemQuality quality, ItemPosition position,
                   ItemContainer container, int treasureClass, short tomeId, int invWidth, int invHeight) {

    public Item {
        try {
            prefixIds.addAll(List.of());
            throw new IllegalArgumentException("prefixIds should be an immutable list");
        } catch (UnsupportedOperationException _) { /* expected behaviour */ }

        try {
            suffixIds.addAll(List.of());
            throw new IllegalArgumentException("suffixIds should be an immutable list");
        } catch (UnsupportedOperationException _) { /* expected behaviour */ }

        try {
            properties.addAll(List.of());
            throw new IllegalArgumentException("properties should be an immutable list");
        } catch (UnsupportedOperationException _) { /* expected behaviour */ }

        try {
            socketedItems.addAll(List.of());
            throw new IllegalArgumentException("socketedItems should be an immutable list");
        } catch (UnsupportedOperationException _) { /* expected behaviour */ }
    }

    /**
     * Indicator whether this item is a charm or not
     *
     * @param code the {@link Item#code}
     * @return true/false if it is
     */
    public static boolean isCharm(String code) {
        return "cm1".equals(code)
                || "cm2".equals(code)
                || "cm3".equals(code);
    }

    /**
     * Indicator whether this item is a Rune or not
     *
     * @param type the {@link Item#type}
     * @return true/false if it is
     */
    public static boolean isRune(String type) {
        return "rune".equals(type);
    }

    /**
     * Indicator whether this item is a tome of town portal/identify or not
     *
     * @param code the {@link Item#code}
     * @return true/false if it is
     */
    public static boolean isTome(String code) {
        return "tbk".equals(code) || "ibk".equals(code);
    }

    /**
     * Indicator whether this item is a scroll of town portal/identify or not
     *
     * @param code the {@link Item#code}
     * @return true/false if it is
     */
    public static boolean isScroll(String code) {
        return "tsc".equals(code) || "isc".equals(code);
    }

    /**
     * Indicator whether this item is a jewel or not
     *
     * @param code the {@link Item#code}
     * @return true/false if it is
     */
    public static boolean isJewel(String code) {
        return "jew".equals(code);
    }

    /**
     * Indicator whether this item is a gem or not
     *
     * @param type  the {@link Item#type}
     * @param type2 the {@link Item#type2}
     * @return true/false if it is
     */
    public static boolean isGem(String type, String type2) {
        return type.startsWith("gem") && type2.startsWith("gem");
    }

    /**
     * Indicator whether this item has a class restriction on a specific {@link CharacterType}
     *
     * @param yourClass the class to check for a restriction
     * @return true in case the item cannot be used by the supplied yourClass parameter.
     */
    public boolean isClassRestricted(CharacterType yourClass) {
        return restrictedToClass != null
                && restrictedToClass != yourClass;
    }

    public static boolean isRotWCollectible(String type, String type2, String code) {
        // these are stored separately in the material stashes and require an extra 0 to be parsed in some cases.
        return isRune(type)
                || isGem(type, type2)
                || type.equals("rpot")
                || code.equals("pk1") || code.equals("pk2") || code.equals("pk3") // keys
                || code.equals("dhn") || code.equals("bey") || code.equals("mbr") // uber collectibles
                || code.equals("toa") || code.equals("tes") || code.equals("ceh") || code.equals("bet") || code.equals("fed") // token of absolution
                || code.startsWith("xa") // Worldstone shard
                || code.startsWith("ua") // ancient summon material
                || code.startsWith("um") // ancient upgrade material
                ;
    }

    /**
     * Indicator whether this item is a helmet or torso type. This differs from {@link #itemType} since that also includes shield types.
     *
     * @param type the {@link Item#type}
     * @return true/false if it is
     */
    public static boolean isHelmetOrTorso(String type) {
        return "tors".equals(type)
                || "helm".equals(type)
                || "phlm".equals(type)
                || "pelt".equals(type)
                || "cloa".equals(type)
                || "circ".equals(type);
    }

    /**
     * Builder class for constructing instances of {@link Item}.
     * Allows for fluent construction of a Diablo II {@link Item} with various customization options.
     */
    public static final class ItemBuilder {
        private boolean isIdentified;
        private boolean isSocketed;
        private boolean isEar;
        private boolean isSimple;
        private boolean isEthereal;
        private boolean isPersonalized;
        private boolean isRuneword;
        private boolean isThrown;
        private boolean isTwoHanded;
        private int version;
        private short x;
        private short y;
        private String code;
        private String type;
        private String type2;
        private ItemType itemType;
        private short cntSockets;
        private short cntFilledSockets;
        private String fingerPrint;
        private String guid;
        private short level;
        private short pictureId;
        private final List<Short> prefixIds = new ArrayList<>();
        private final List<Short> suffixIds = new ArrayList<>();
        private short setItemId;
        private short uniqueId;
        private short rareNameId1;
        private short rareNameId2;
        private String itemName;
        private String setName;
        private String personalizedName;
        private int baseDefense;
        private short maxDurability;
        private short durability;
        private short stacks;
        private int maxStacks;
        private int reqStr;
        private int reqDex;
        private int reqLvl;
        private int invWidth;
        private int invHeight;
        private CharacterType restrictedToClass;

        private final List<ItemProperty> properties = new ArrayList<>();
        private final List<Item> socketedItems = new ArrayList<>();

        private ItemLocation location;
        private ItemQuality quality = ItemQuality.NONE;
        private ItemPosition position;
        private ItemContainer container;

        private int treasureClass;

        private short tomeId;

        /**
         * Sets the identification status of the item.
         *
         * @param identified True if the item is identified, false otherwise.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder identified(boolean identified) {
            this.isIdentified = identified;
            return this;
        }

        /**
         * Sets the socketed status of the item.
         *
         * @param socketed True if the item is socketed, false otherwise.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder socketed(boolean socketed) {
            this.isSocketed = socketed;
            return this;
        }

        /**
         * Sets the ear status of the item.
         *
         * @param isEar True if the item is an ear, false otherwise.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder ear(boolean isEar) {
            this.isEar = isEar;
            return this;
        }

        /**
         * Sets the simplicity status of the item.
         *
         * @param simple True if the item is simple, false otherwise.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder simple(boolean simple) {
            this.isSimple = simple;
            return this;
        }

        /**
         * Sets the ethereal status of the item.
         *
         * @param ethereal True if the item is ethereal, false otherwise.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder ethereal(boolean ethereal) {
            this.isEthereal = ethereal;
            return this;
        }

        /**
         * Sets the personalized status of the item.
         *
         * @param personalized True if the item is personalized, false otherwise.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder personalized(boolean personalized) {
            this.isPersonalized = personalized;
            return this;
        }

        /**
         * Sets the runeword status of the item.
         *
         * @param isRuneword True if the item is a runeword, false otherwise.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder runeword(boolean isRuneword) {
            this.isRuneword = isRuneword;
            return this;
        }

        /**
         * Sets the thrown status of the item.
         *
         * @param thrown True if the item can be thrown, false otherwise.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder thrown(boolean thrown) {
            this.isThrown = thrown;
            return this;
        }

        /**
         * Sets whether this item is two-handed or not.
         *
         * @param isTwoHanded True if the item is two-handed, false otherwise.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder twohanded(boolean isTwoHanded) {
            this.isTwoHanded = isTwoHanded;
            return this;
        }

        /**
         * Sets the version of the item.
         *
         * @param version The version number of the item.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder version(int version) {
            this.version = version;
            return this;
        }

        /**
         * Sets the X-coordinate of the item.
         *
         * @param x The X-coordinate value.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder x(short x) {
            this.x = x;
            return this;
        }

        /**
         * Sets the Y-coordinate of the item.
         *
         * @param y The Y-coordinate value.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder y(short y) {
            this.y = y;
            return this;
        }

        /**
         * Sets the code of the item.
         *
         * @param code The code string representing the item.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder code(String code) {
            this.code = code;
            return this;
        }

        /**
         * Sets the type of the item.
         *
         * @param type The type string representing the item.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder type(String type) {
            this.type = type;
            return this;
        }

        /**
         * Sets the second type of the item.
         *
         * @param type2 The second type string representing the item.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder type2(String type2) {
            this.type2 = type2;
            return this;
        }

        /**
         * Sets the item type of the item.
         *
         * @param itemType The {@link ItemType} enum representing the item type.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder itemType(ItemType itemType) {
            this.itemType = itemType;
            return this;
        }

        /**
         * Sets the count of sockets in the item.
         *
         * @param cntSockets The count of sockets in the item.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder cntSockets(short cntSockets) {
            this.cntSockets = cntSockets;
            return this;
        }

        /**
         * Sets the count of filled sockets in the item.
         *
         * @param cntFilledSockets The count of filled sockets in the item.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder cntFilledSockets(short cntFilledSockets) {
            this.cntFilledSockets = cntFilledSockets;
            return this;
        }

        /**
         * Sets the fingerprint of the item.
         *
         * @param fingerPrint The fingerprint string representing the item.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder fingerPrint(String fingerPrint) {
            this.fingerPrint = fingerPrint;
            return this;
        }

        /**
         * Sets the GUID (Globally Unique Identifier) of the item.
         *
         * @param guid The GUID string representing the item.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder guid(String guid) {
            this.guid = guid;
            return this;
        }

        /**
         * Sets the level of the item.
         *
         * @param level The level of the item.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder level(short level) {
            this.level = level;
            return this;
        }

        /**
         * Sets the picture ID of the item.
         *
         * @param pictureId The picture ID of the item.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder pictureId(short pictureId) {
            this.pictureId = pictureId;
            return this;
        }

        /**
         * Adds a prefix ID to the item.
         *
         * @param prefixId The prefix ID to be added.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder addPrefixId(Short prefixId) {
            this.prefixIds.add(prefixId);
            return this;
        }

        /**
         * Adds a suffix ID to the item.
         *
         * @param suffixId The suffix ID to be added.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder addSuffixId(Short suffixId) {
            this.suffixIds.add(suffixId);
            return this;
        }

        /**
         * Sets the item set itemID.
         *
         * @param setItemId The set item ID to be set.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder setItemId(short setItemId) {
            this.setItemId = setItemId;
            return this;
        }

        /**
         * Sets the unique ID of the item.
         *
         * @param uniqueId The unique ID to be set.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder uniqueId(short uniqueId) {
            this.uniqueId = uniqueId;
            return this;
        }

        /**
         * Sets the first rare name ID of the item.
         *
         * @param rareNameId1 The first rare name ID to be set.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder rareNameId1(short rareNameId1) {
            this.rareNameId1 = rareNameId1;
            return this;
        }

        /**
         * Sets the second rare name ID of the item.
         *
         * @param rareNameId2 The second rare name ID to be set.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder rareNameId2(short rareNameId2) {
            this.rareNameId2 = rareNameId2;
            return this;
        }

        /**
         * Sets the name of the item.
         *
         * @param itemName The name of the item.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder itemName(String itemName) {
            this.itemName = itemName;
            return this;
        }

        /**
         * Sets the name of the set to which the item belongs.
         *
         * @param setName The name of the set.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder setName(String setName) {
            this.setName = setName;
            return this;
        }

        /**
         * Sets the personalized name of the item.
         *
         * @param personalizedName The personalized name of the item.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder personalizedName(String personalizedName) {
            this.personalizedName = personalizedName;
            return this;
        }

        /**
         * Sets the base defense of the item.
         *
         * @param baseDefense The base defense value.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder baseDefense(int baseDefense) {
            this.baseDefense = baseDefense;
            return this;
        }

        /**
         * Sets the maximum durability of the item.
         *
         * @param maxDurability The maximum durability value.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder maxDurability(short maxDurability) {
            this.maxDurability = maxDurability;
            return this;
        }

        /**
         * Sets the current durability of the item.
         *
         * @param durability The current durability value.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder durability(short durability) {
            this.durability = durability;
            return this;
        }

        /**
         * Sets the number of stacks for the item.
         *
         * @param stacks The number of stacks.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder stacks(short stacks) {
            this.stacks = stacks;
            return this;
        }

        /**
         * Sets the number of maximum stacks for the item.
         *
         * @param max The maximum number of stacks.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder maxStacks(int max) {
            this.maxStacks = max;
            return this;
        }

        /**
         * Sets the strength requirement for the item.
         *
         * @param reqStr The strength requirement value.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder reqStr(int reqStr) {
            this.reqStr = reqStr;
            return this;
        }

        /**
         * Sets the dexterity requirement for the item.
         *
         * @param reqDex The dexterity requirement value.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder reqDex(int reqDex) {
            this.reqDex = reqDex;
            return this;
        }

        /**
         * Sets the level requirement for the item.
         *
         * @param reqLvl The level requirement value.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder reqLvl(int reqLvl) {
            this.reqLvl = reqLvl;
            return this;
        }

        /**
         * Sets the inventory width this item uses
         *
         * @param invWidth The inventory width
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder invWidth(int invWidth) {
            this.invWidth = invWidth;
            return this;
        }

        /**
         * Sets the inventory height this item uses
         *
         * @param invHeight The inventory height
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder invHeight(int invHeight) {
            this.invHeight = invHeight;
            return this;
        }

        /**
         * Sets the character class restriction for the item.
         *
         * @param restrictedToClass The character class restriction.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder restrictedToClass(CharacterType restrictedToClass) {
            this.restrictedToClass = restrictedToClass;
            return this;
        }

        /**
         * Adds a property to the item.
         *
         * @param property The property to be added.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder addProperty(ItemProperty property) {
            this.properties.add(property);
            return this;
        }

        /**
         * Adds a list of properties to the item.
         *
         * @param properties The list of properties to be added.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder addProperties(List<ItemProperty> properties) {
            this.properties.addAll(properties);
            return this;
        }

        /**
         * Adds a socketed item to the item.
         * Will update the {@link #reqLvl} in case the reqLvl of the socketed item is higher than the current value.
         *
         * @param socketedItem The socketed {@link Item} to be added.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder addSocketedItem(Item socketedItem) {
            this.reqLvl = Math.max(socketedItem.reqLvl, this.reqLvl);
            this.socketedItems.add(socketedItem);
            return this;
        }

        /**
         * Sets the location of the item.
         *
         * @param location The location of the item.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder location(ItemLocation location) {
            this.location = location;
            return this;
        }

        /**
         * Sets the quality of the item.
         *
         * @param quality The quality of the item.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder quality(ItemQuality quality) {
            this.quality = quality;
            return this;
        }

        /**
         * Sets the position of the item.
         *
         * @param position The position of the item.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder position(ItemPosition position) {
            this.position = position;
            return this;
        }

        /**
         * Sets the container of the item.
         *
         * @param container The container of the item.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder container(ItemContainer container) {
            this.container = container;
            return this;
        }

        /**
         * Sets the treasure class of the item.
         *
         * @param treasureClass The treasure class of the item.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder treasureClass(int treasureClass) {
            this.treasureClass = treasureClass;
            return this;
        }

        /**
         * Sets the tomeId of a tome of Identify or Town portal.
         *
         * @param tomeId The id of the tome.
         * @return The current ItemBuilder instance.
         */
        public ItemBuilder tomeId(short tomeId) {
            this.tomeId = tomeId;
            return this;
        }

        /**
         * Build the {@link Item} instance with the configured values. This will also sort the properties in the correct display order.
         *
         * @return The constructed {@link Item} instance.
         */
        public Item build() {
            // ensure the properties are stored in the correct order (high -> low) for display purposes
            this.properties.sort(new ReverseItemPropertyOrderComparator());

            return new Item(isIdentified, isSocketed, isEar, isSimple, isEthereal, isPersonalized, isRuneword, isThrown, isTwoHanded,
                    version, x, y, code, type, type2, itemType, cntSockets, cntFilledSockets, fingerPrint,
                    guid, level, pictureId, List.copyOf(prefixIds), List.copyOf(suffixIds),
                    setItemId, uniqueId, rareNameId1, rareNameId2, itemName, setName, personalizedName, baseDefense, maxDurability,
                    durability, stacks, maxStacks, reqStr, reqDex, reqLvl, restrictedToClass, List.copyOf(properties),
                    List.copyOf(socketedItems), location, quality, position, container, treasureClass, tomeId, invWidth, invHeight);
        }
    }
}
