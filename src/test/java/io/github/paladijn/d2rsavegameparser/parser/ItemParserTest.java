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

import io.github.paladijn.d2rsavegameparser.TestCommons;
import io.github.paladijn.d2rsavegameparser.internal.parser.BitReader;
import io.github.paladijn.d2rsavegameparser.model.Item;
import io.github.paladijn.d2rsavegameparser.model.ItemContainer;
import io.github.paladijn.d2rsavegameparser.model.ItemLocation;
import io.github.paladijn.d2rsavegameparser.model.ItemPosition;
import io.github.paladijn.d2rsavegameparser.model.ItemProperty;
import io.github.paladijn.d2rsavegameparser.model.ItemQuality;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ItemParserTest {
    private final ItemParser cut = new ItemParser(true);

    @Test
    void greaterHealthPot() {
        final byte[] bytes = new byte[]{16, 0, -96, 8, 21, 36, 0, -49, -81, 0};
        Item pot = cut.parseItem(new BitReader(bytes));

        assertThat(pot.isSimple()).isTrue();
        assertThat(pot.itemName()).isEqualTo("Greater Healing Potion");
        assertThat(pot.type()).isEqualTo("hpot");
        assertThat(pot.code()).isEqualTo("hp4");
        assertThat(pot.location()).isEqualTo(ItemLocation.BELT);
    }

    @Test
    void superManaPotion() {
        final byte[] bytes = new byte[]{16, 0, -96, 0, 5, -44, -60, 78, -76, 0};
        Item pot = cut.parseItem(new BitReader(bytes));

        assertThat(pot.isSimple()).isTrue();
        assertThat(pot.itemName()).isEqualTo("Super Mana Potion");
        assertThat(pot.type()).isEqualTo("mpot");
        assertThat(pot.code()).isEqualTo("mp5");
        assertThat(pot.location()).isEqualTo(ItemLocation.STORED);
    }

    @Test
    void rejuv() {
        final byte[] bytes = new byte[]{16, 0, -96, 0, 21, 12, -32, -20, 40};
        Item pot = cut.parseItem(new BitReader(bytes));

        assertThat(pot.isSimple()).isTrue();
        assertThat(pot.itemName()).isEqualTo("Rejuvenation Potion");
        assertThat(pot.type()).isEqualTo("rpot");
        assertThat(pot.code()).isEqualTo("rvs");
        assertThat(pot.location()).isEqualTo(ItemLocation.BELT);
    }

    @Test
    void twitchThroe () {
        byte[] bytes = {16, 0, -128, 0, -51, 12, -128, 12, 12, -35, -49, -27, 35, -106, 67, 10, -88, 0, 100, 3, 0, 21, 1, 42, 10, -39, -121, 17, 116, 65, -115, 65, -3, 7};
        Item unique = cut.parseItem(new BitReader(bytes));

        assertThat(unique)
                .extracting(Item::isSimple, Item::uniqueId, Item::itemName, Item::code, Item::reqLvl, Item::reqStr)
                .containsExactly(false, (short)82, "Twitchthroe", "stu", 16, 27);

        assertThat(unique.properties()).hasSize(6);
        assertThat(unique.properties().get(2).name()).isEqualTo("toblock");
    }

    @Test
    void rareGloves() {
        final byte[] bytes = {16, 0, -128, 0, -115, 42, -64, -84, 27, 10, 28, 65, -50, 111, 70, 109, 87, 73, 84, 69, -36, -4, 72, -71, 52, 11, 11, 72, 16, -128, 16, -29, -124, 53, 30, 3, 40, 123, 93, 80, -68, 4, 0, -12, 31};
        Item result = cut.parseItem(new BitReader(bytes));

        assertThat(result.quality()).isEqualTo(ItemQuality.RARE);
        assertThat(result.location()).isEqualTo(ItemLocation.EQUIPPED);
        assertThat(result.position()).isEqualTo(ItemPosition.GLOVES);
        assertThat(result.fingerPrint()).isEqualTo("0xe7208e05");
        assertThat(result.itemName()).isEqualTo("Loath clutches");
        assertThat(result.durability()).isEqualTo((short)4);
        assertThat(result.maxDurability()).isEqualTo((short)18);
        assertThat(result.reqStr()).isEqualTo(45);
        assertThat(result.reqLvl()).isEqualTo(35);

        assertThat(result.properties()).hasSize(6);
        assertThat(result.properties().getFirst().values()[0]).isEqualTo(2);
        assertThat(result.properties().getFirst().values()[1]).isEqualTo(2);
        assertThat(result.properties().getFirst().name()).isEqualTo("item_addskill_tab");
        assertThat(result.properties().get(1).values()[0]).isEqualTo(20);
        assertThat(result.properties().get(1).name()).isEqualTo("item_fasterattackrate");
        assertThat(result.properties().get(2).values()[0]).isEqualTo(3);
        assertThat(result.properties().get(2).name()).isEqualTo("lifedrainmindam");
        assertThat(result.properties().get(3).values()[0]).isEqualTo(49);
        assertThat(result.properties().get(3).name()).isEqualTo("item_armor_percent");
        assertThat(result.properties().get(4).values()[0]).isEqualTo(14);
        assertThat(result.properties().get(4).name()).isEqualTo("fireresist");
        assertThat(result.properties().get(5).values()[0]).isEqualTo(23);
        assertThat(result.properties().get(5).name()).isEqualTo("item_magicbonus");
    }

    @Test
    void ancientsPledge() {
        byte[] bytes = {16, 8, -128, 4, 77, 21, 96, -66, 109, -53, -61, -90, 19, -76, -120, 23, -40, -128, -78, 1, 20, 19, 115, 34, 90, -118, 104, 43, -94, -75, -120, -10, 31, -126, -116, 19, -43, 82, 84, 91, 49, -81, 69, 53, 57, -54, 127, 16, 0, -96, 0, 53, 0, -32, 124, 35, 1, 16, 0, -96, 0, 53, 4, -32, 124, -69, 0, 16, 0, -96, 0, 53, 8, -32, 124, -5, 0};
        Item result = cut.parseItem(new BitReader(bytes));

        assertThat(result.isRuneword()).isTrue();
        assertThat(result.isSocketed()).isTrue();
        assertThat(result.code()).isEqualTo("pa3");
        assertThat(result.quality()).isEqualTo(ItemQuality.NORMAL);
        assertThat(result.cntFilledSockets()).isEqualTo((short)3);
        assertThat(result.itemName()).isEqualTo("Ancients' Pledge");
        assertThat(result.location()).isEqualTo(ItemLocation.EQUIPPED);
        assertThat(result.position()).isEqualTo(ItemPosition.LEFT_HAND);

        assertThat(result.properties()).hasSize(13);
        // This is an interesting one as the Paladin shield gives 9% res all, which is then combined with the rune properties to form the runeword.
        // However, part of the resists come from the runes themselves, except for the cold damage (which would require Thul) and thus the runeword applies +43 cold res, and +13 res on fire, lightning and poison.
        // Due to this there are two properties for the cold resistance and three for the other resistances as they get an extra boost from the Rune.
        assertThat(result.properties().get(1).values()[0]).isEqualTo(9);
        assertThat(result.properties().get(1).name()).isEqualTo("coldresist");
        assertThat(result.properties().get(2).values()[0]).isEqualTo(43);
        assertThat(result.properties().get(2).name()).isEqualTo("coldresist");

        assertThat(result.properties().get(3).values()[0]).isEqualTo(9);
        assertThat(result.properties().get(3).name()).isEqualTo("lightresist");
        assertThat(result.properties().get(4).values()[0]).isEqualTo(13);
        assertThat(result.properties().get(4).name()).isEqualTo("lightresist");
        assertThat(result.properties().get(5).values()[0]).isEqualTo(35);
        assertThat(result.properties().get(5).values()[1]).isEqualTo(35);
        assertThat(result.properties().get(5).name()).isEqualTo("lightresist");
    }

    @Test
    @Disabled("This breaks on 105, we should grab a standard of heroes from a character file")
    void standardOfHeroes() {
        byte[] bytes = {16, 0, -128, 0, 5, 84, -106, -52, 24, 2, -52, 108, 72, -57, -57, -1, -5, 15};
        Item result = cut.parseItem(new BitReader(bytes));

        assertThat(result.type()).isEqualTo("ques");
        assertThat(result.itemName()).isEqualTo("Standard of Heroes");
        assertThat(result.location()).isEqualTo(ItemLocation.STORED);
        assertThat(result.container()).isEqualTo(ItemContainer.STASH);
    }

    @Test
    void khalimsEye() {
        byte[] bytes = {16, 0, -96, 0, 5, -112, 100, 115, 64, 21};
        Item result = cut.parseItem(new BitReader(bytes));

        assertThat(result.type()).isEqualTo("ques");
        assertThat(result.itemName()).isEqualTo("Khalim's Eye");
        assertThat(result.location()).isEqualTo(ItemLocation.STORED);
        assertThat(result.container()).isEqualTo(ItemContainer.INVENTORY);
    }

    @Test
    void iniFuss() {
        final byte[] bytes = {16, 0, -96, 0, 5, 76, 68, 37, 10, 0};

        Item result = cut.parseItem(new BitReader(bytes));

        assertThat(result.type()).isEqualTo("ques");
        assertThat(result.itemName()).isEqualTo("Scroll of Inifuss");
        assertThat(result.location()).isEqualTo(ItemLocation.STORED);
        assertThat(result.container()).isEqualTo(ItemContainer.INVENTORY);
        assertThat(result.x()).isEqualTo((short)1);
        assertThat(result.y()).isEqualTo((short)3);
    }

    @Test
    void iniFussNightmare() {
        final byte[] bytes = {16, 0, -96, 0, 5, 0, 80, 37, 42, 0};

        Item result = cut.parseItem(new BitReader(bytes));

        assertThat(result.type()).isEqualTo("ques");
        assertThat(result.itemName()).isEqualTo("Scroll of Inifuss");
        assertThat(result.location()).isEqualTo(ItemLocation.STORED);
        assertThat(result.container()).isEqualTo(ItemContainer.HORADRIC_CUBE);
        assertThat(result.x()).isEqualTo((short)0);
        assertThat(result.y()).isEqualTo((short)0);
    }

    @Test
    void soulstone() {
        final byte[] bytes = {16, 32, -96, 0, 5, 100, -42, 18, 5, 0, 0, -128, 0, 5};

        final BitReader br = new BitReader(bytes);
        final Item result = cut.parseItem(br);

        assertThat(result.type()).isEqualTo("ques");
        assertThat(result.itemName()).isEqualTo("Mephisto's Soulstone");
        assertThat(br.getPositionInBits()).isEqualTo(9 * 8);
    }

    void cairnStones() {
        final byte[] bytes = {16, 32, -96, 0, 5, 24, 68, -91, 49, 0};

    }

    //Horadric Malus :: byte[] bytes = {16, 0, -128, 0, 5, 20, 4, -113, 54, -120, 17, -100, 18, 39, -114, -1, 119, 19, -61, 127};

    @Test
    void personalizedSpirit() {
        byte[] bytes = {16, 8, -128, 5, 13, 17, -64, 41, -59, -112, 78, 29, -23, -90, -80, 9, -59, -12, -122, 86, -26, 118, 38, -105, -26, 6, 0, 4, 4, -3, 31, 96, 75, 80, 8, 66, -33, -104, 101, -102, -40, 31, 77, 50, -4, 7, 16, 0, -96, 0, 53, 0, -32, 124, -5, 0, 16, 0, -96, 0, 53, 4, -32, 124, -66, 3, 16, 0, -96, 0, 53, 8, -32, 124, -69, 0, 16, 0, -96, 0, 53, 12, -32, 124, 62, 1};
        Item result = cut.parseItem(new BitReader(bytes));

        assertThat(result.isPersonalized()).isTrue();
        assertThat(result.personalizedName()).isEqualTo("Lohengrin");
        assertThat(result.itemName()).isEqualTo("Spirit");
    }

    @Test
    void superiorWithRunes() {
        byte[] bytes = {16, 8, -128, 0, 13, 17, -128, 4, 42, 45, 22, -69, 7, 74, -122, 10, 66, 34, 19, 4, 88, -14, -7, 15, 16, 0, -96, 0, 53, 0, -32, 124, 35, 1, 16, 0, -96, 0, 53, 4, -32, 124, -5, 0};
        Item result = cut.parseItem(new BitReader(bytes));

        assertThat(result.quality()).isEqualTo(ItemQuality.SUPERIOR);
        assertThat(result.socketedItems()).hasSize(2);

        assertThat(result.properties()).hasSize(7); // 2 for the superior stats, 2 + 3 for ral + tal
        assertThat(result.properties().getFirst().name()).isEqualTo("tohit");
        assertThat(result.properties().getFirst().values()[0]).isEqualTo(2);
        assertThat(result.properties().get(5).name()).isEqualTo("item_maxdurability_percent");
        assertThat(result.properties().get(5).values()[0]).isEqualTo(11);
        assertThat(result.maxDurability()).isEqualTo((short)65);
        assertThat(Math.floor((result.maxDurability() / 100.0) * (100 + result.properties().get(5).values()[0]))) // add the extra durability
                .isEqualTo(72);
        assertThat(result.durability()).isEqualTo((short)72);
    }

    @Test
    @Disabled("This breaks on 105, we should grab a new item from a character file")
    void crude() {
        byte[] bytes = {16, 0, -128, 0, 5, 20, 68, -68, 25, -94, -46, -32, -4, 2, 1, 52, 96, 32, -64, 127};
        Item result = cut.parseItem(new BitReader(bytes));

        assertThat(result.quality()).isEqualTo(ItemQuality.INFERIOR);
        assertThat(result.itemName()).isEqualTo("Crude Cap");
        assertThat(result.maxDurability()).isEqualTo((short)3);
        assertThat(result.durability()).isEqualTo((short)1);
    }

    @Test
    @Disabled("This breaks on 105, we should grab a new item from a character file")
    void crackedKatar() {
        byte[] bytes = {16, 0, -128, 0, 5, 32, 68, 50, 39, 48, -42, -6, 84, 7, -126, 120, 56, -16, 31};
        Item result = cut.parseItem(new BitReader(bytes));

        assertThat(result.quality()).isEqualTo(ItemQuality.INFERIOR);
        assertThat(result.itemName()).isEqualTo("Cracked Katar");
        assertThat(result.maxDurability()).isEqualTo((short)15);
        assertThat(result.durability()).isEqualTo((short)7);
    }

    @Test
    @Disabled("This breaks on 105, we should grab a new item from a character file")
    void lowQuality() {
        byte[] bytes = {16, 0, -128, 0, 5, 12, -28, -86, 9, -76, 47, -86, -16, -127, 96, 22, 48, 16, -32, 63};
        Item result = cut.parseItem(new BitReader(bytes));

        assertThat(result.quality()).isEqualTo(ItemQuality.INFERIOR);
        assertThat(result.itemName()).isEqualTo("Low Quality Boots");
        assertThat(result.maxDurability()).isEqualTo((short)3);
        assertThat(result.durability()).isEqualTo((short)1);
        assertThat(result.baseDefense()).isEqualTo(1);
    }

    @Test
    void crackedScepterWithSkills() {
        byte[] bytes = {16, 0, -128, 0, 5, 0, 4, -48, 12, -53, -127, 67, 81, -106, 32, 46, 30, -84, 97, -109, -1};
        Item result = cut.parseItem(new BitReader(bytes));

        assertThat(result.quality()).isEqualTo(ItemQuality.INFERIOR);
        assertThat(result.itemName()).isEqualTo("Cracked War Scepter");
        assertThat(result.maxDurability()).isEqualTo((short)23);
        assertThat(result.properties().getLast())
                .extracting(ItemProperty::index, ItemProperty::name)
                .containsExactlyInAnyOrder(107, "item_singleskill"); // we just check if the properties were parsed.
    }

    @Test
    void etherealWandWithSkills() {
        byte[] bytes = {16, 0, -64, 0, 5, 8, 68, 1, 27, 56, 81, -61, 63, 45, 2, 4, 4, 107, -104, 108, 77, -109, -84, 113, -110, -1};
        Item result = cut.parseItem(new BitReader(bytes));

        assertThat(result.quality()).isEqualTo(ItemQuality.NORMAL); // Ethereal items are 'normal', so no lowered durability/requirements.
        assertThat(result.isEthereal()).isTrue();
        assertThat(result.properties()).hasSize(3); // validate the three skills were read.
    }

    @Test
    void identifyScroll() {
        byte[] bytes = {16, 0, -96, 8, 5, -28, -28, 71, 34};
        Item result = cut.parseItem(new BitReader(bytes));

        assertThat(result.code()).isEqualTo("isc");
        assertThat(result.itemName()).isEqualTo("Scroll of Identify");
        assertThat(result.x()).isEqualTo((short)3);
        assertThat(result.y()).isEqualTo((short)9);
        assertThat(result.location()).isEqualTo(ItemLocation.STORED);
        assertThat(result.container()).isEqualTo(ItemContainer.INVENTORY);
    }

    @Test
    void tpScroll() {
        byte[] bytes = {16, 0, -96, 8, 5, 36, -60, -112, 8};
        Item result = cut.parseItem(new BitReader(bytes));

        assertThat(result.code()).isEqualTo("tsc");
        assertThat(result.itemName()).isEqualTo("Scroll of Town Portal");
        assertThat(result.x()).isZero();
        assertThat(result.y()).isEqualTo((short)9);
        assertThat(result.location()).isEqualTo(ItemLocation.STORED);
        assertThat(result.container()).isEqualTo(ItemContainer.INVENTORY);
    }

    @Test
    void gemmedWithRunes() {
        byte[] bytes = {16, 40, -128, 0, 77, 4, -128, -92, -103, 20, 61, 11, -53, 25, -126, 9, 72, 72, -112, -1, 16, 0, -96, 0, 53, 0, -32, 124, 35, 1, 16, 0, -96, 0, 53, 4, -32, 124, -5, 0};
        Item gemmedSkullCap = cut.parseItem(new BitReader(bytes));

        assertThat(gemmedSkullCap.itemName()).isEqualTo("Gemmed Skull Cap");
        assertThat(gemmedSkullCap.socketedItems()).hasSize(2);
        assertThat(gemmedSkullCap.socketedItems().getFirst().itemName()).isEqualTo("Ral Rune");
        assertThat(gemmedSkullCap.socketedItems().getLast().itemName()).isEqualTo("Tal Rune");
        assertThat(gemmedSkullCap.reqLvl()).isEqualTo(19);
    }

    @Test
    void correctlyNameBeastWrap() {
        byte[] bytes = {16, 0, -128, 0, -51, 12, -128, 12, 12, 1, 42, 119, 47, 21, -125, 83, 105, 97, -50, -31, 35, -26, 68, -67, 112, 1, -56, 7, 0, 33, 7, 74, 64, 104, 32, 34, 72, -15, -20, 63};
        Item beastWrap = cut.parseItem(new BitReader(bytes));

        assertThat(beastWrap.itemName()).isEqualTo("Beast wrap");
    }

    @Test
    void addSocketedJewelPropertiesToItem() {
        byte[] bytes = {16, 8, -128, 0, -51, 46, 0, 33, 74, -39, -53, 67, 37, 99, -61, 83, 38, 105, 82, -24, 88, -98, -94, 98, -66, 2, 5, 5, 73, 1, 111, 5, -68, 46, -88, 52, 40, 102, -122, 13, 42, 52, -1, 1, 16, 0, -128, 0, 53, 0, 0, -35, 0, -126, 77, 109, -42, -100, -54, -126, 39, 95, -88, -12, 74, -68, -40, 8, 14, -96, -8, 32, -125, 19, -16, 82, 72, 91, 33, -83, -123, -76, -1, 16, 0, -128, 0, 53, 4, 0, -35, 0, -126, 117, 43, 15, -72, -54, -126, 39, 95, -88, -12, 74, -68, -40, 8, 14, -96, -8, 32, -125, 19, -46, 82, -64, 91, 33, -83, -123, -76, -1};
        Item ravenGoad = cut.parseItem(new BitReader(bytes));

        assertThat(ravenGoad.properties()).hasSize(17);
    }

    @Test
    void solRune() {
        byte[] bytes = {16, 0, -96, 0, 51, 4, -32, 124, -104, 0};
        Item solRune = cut.parseItem(new BitReader(bytes));

        assertThat(solRune.properties()).hasSize(3);
        assertThat(solRune.properties().getFirst().name()).isEqualTo("mindamage");
    }

    @Test
    void ithRune() {
        byte[] bytes = {16, 0, -96, 0, 51, 0, -32, 124, -17, 3};
        Item ithRune = cut.parseItem(new BitReader(bytes));

        assertThat(ithRune.properties()).hasSize(3);
        assertThat(ithRune.properties().getFirst().name()).isEqualTo("maxdamage");
    }

    @Test
    @Disabled("This breaks on 105, as the stacks value changed")
    void tomeOfTP() {
        final byte[] bytes = {16, 0, -128, 0, 5, 0, -60, -88, 20, 62, 71, 61, -36, 18, 2, -112, -63, 127};
        final Item tomeOfTP = cut.parseItem(new BitReader(bytes));

        assertThat(tomeOfTP.stacks()).isEqualTo((short)12);
    }

    @Test
    @Disabled("This breaks on 105, as the stacks value changed")
    void tomeOfIdentify() {
        byte[] bytes = {16, 0, -128, 0, 5, 4, -28, -89, 82, 120, 62, -39, -70, 52, 8, -63, 1, -1, 1};
        final Item tomeOfID = cut.parseItem(new BitReader(bytes));

        assertThat(tomeOfID.stacks()).isEqualTo((short)3);
    }


    @Test
    void rotwManaPotOnBoundary() {
        final byte[] bytes = {16, 0, -96, 0, 5, -100, -60, 78, 38, 0, 16, 0};
        final BitReader br = new BitReader(bytes);
        cut.parseItem(br);

        assertThat(br.getPositionInBits()).isEqualTo(80); // 10 characters read.
    }

    @Test
    void rotwManaPotFollowedByDoubleZero() {
        final byte[] bytes = {16, 0, -96, 0, 21, 8, -64, -50, 79, 0, 0, 0, -128};
        final BitReader br = new BitReader(bytes);
        cut.parseItem(br);

        assertThat(br.getPositionInBits()).isEqualTo(80); // 10 characters read.
    }

    @Test
    void listBits() {
        //Quilted Armor of Balance ::
        byte[] bytes = {16, 0, -128, 0, -51, 12, 96, 19, -2, -62, 81, 59, -4, -71, -127, 0, -128, 65, 76, -128, -126, -62, 24, -49, 127};

        IO.println(TestCommons.getBitDetails(bytes));

        IO.println(TestCommons.getConcatenatedBits(bytes));

        // new style
        byte[] newBytes = {16, 0, -128, 0, -51, 12, 96, 19, -2, -62, 81, 59, -4, -71, -127, 0, -128, 65, 76, -128, -126, -126, 49, -98, -1, 0};

        IO.println(TestCommons.getBitDetails(newBytes));

        IO.println(TestCommons.getConcatenatedBits(newBytes));
    }
}
