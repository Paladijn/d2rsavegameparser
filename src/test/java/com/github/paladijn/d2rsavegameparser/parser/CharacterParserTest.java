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
package com.github.paladijn.d2rsavegameparser.parser;

import com.github.paladijn.d2rsavegameparser.TestCommons;
import com.github.paladijn.d2rsavegameparser.model.D2Character;
import com.github.paladijn.d2rsavegameparser.model.Item;
import com.github.paladijn.d2rsavegameparser.model.ItemProperty;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.nio.ByteBuffer;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.slf4j.LoggerFactory.getLogger;

class CharacterParserTest {
    private static final Logger log = getLogger(CharacterParser.class);

    private final CharacterParser cut = new CharacterParser(false);

    // This is a helper function to debug the contents in case a save game breaks after a patch upgrade or when finding a specific item that we can't parse
    //@Test
    void testCharacterFileForExceptions() {
        final ByteBuffer buffer = TestCommons.getBuffer("1.6.77312/LongestPossible-Cairn.d2s");

        log.info("output: {}", cut.parse(buffer));
    }

    @Test
    void doNotParseOlderVersion() {
        final ByteBuffer buffer = TestCommons.getBuffer("2.4/Dierentuin.d2s");

        final ParseException exception = assertThrows(ParseException.class, () -> cut.parse(buffer));
        assertThat(exception.getMessage()).isEqualTo("Unsupported version: 98");
    }

    @Test
    void throwExceptionOnWrongFileHeader() {
        final ByteBuffer buffer = TestCommons.getBuffer("logback.xml");

        final ParseException exception = assertThrows(ParseException.class, () -> cut.parse(buffer));
        assertThat(exception.getMessage())
                .isEqualTo("Wrong fileHeader 1852793660, this is not a Diablo II saveGame file");
    }

    @Test
    void readNormal() {
        D2Character dierentuin = cut.parse(TestCommons.getBuffer("2.7/Dierentuin.d2s"));

        assertThat(dierentuin.level()).isEqualTo((byte)16);
        assertThat(dierentuin.items()).hasSize(68);
        assertThat(dierentuin.mercenary().items()).hasSize(2);
        assertThat(dierentuin.questDataPerDifficulty().get(0).socketQuestStarted()).isFalse();
        assertThat(dierentuin.waypoints().get(0).act2LutGholein()).isFalse();
    }

    @Test
    void readHell() {
        D2Character lohengrin = cut.parse(TestCommons.getBuffer("2.7/Lohengrin.d2s"));
        assertThat(lohengrin.level()).isEqualTo((byte)76);
        assertThat(lohengrin.questDataPerDifficulty().get(0).socketQuestAvailable()).isTrue();
        assertThat(lohengrin.questDataPerDifficulty().get(1).socketQuestStarted()).isTrue();
        assertThat(lohengrin.questDataPerDifficulty().get(1).resistanceScrollRead()).isTrue();
        assertThat(lohengrin.actProgression()).isEqualTo((byte)11);
        assertThat(lohengrin.items()).hasSize(123);
    }

    @Test
    void readNewChar() {
        D2Character wandelaar = cut.parse(TestCommons.getBuffer("2.7/Wandelaar.d2s"));

        // as this character is new it only has the start attributes to display.
        assertThat(wandelaar.attributes().strength()).isEqualTo(25);
        assertThat(wandelaar.attributes().dexterity()).isEqualTo(20);
        assertThat(wandelaar.attributes().energy()).isEqualTo(15);
        assertThat(wandelaar.attributes().vitality()).isEqualTo(25);
        assertThat(wandelaar.attributes().hp()).isEqualTo(55);
        assertThat(wandelaar.attributes().mana()).isEqualTo(15);
        assertThat(wandelaar.level()).isEqualTo((byte)1);

    }

    @Test
    void deadItems() {
        D2Character deadBody = cut.parse(TestCommons.getBuffer("2.7/itsDeadJim.d2s"));
        assertThat(deadBody.died()).isTrue();
        assertThat(deadBody.deadBodyItems()).hasSize(1); // the staff is the only item on your dead body
        assertThat(deadBody.deadBodyItems().get(0).type()).isEqualTo("staf");
        assertThat(deadBody.items()).hasSize(6); // items still left on your character (pots, scrolls)
    }

    @Test
    void anyaScroll() {
        D2Character scrollActive = cut.parse(TestCommons.getBuffer("2.7/Wandelaar-anya.d2s"));
        assertThat(scrollActive.questDataPerDifficulty().get(0).resistanceScrollRead()).isTrue();
    }

    @Test
    void hasDiedInThePast() {
        D2Character hasDiedInThePast = cut.parse(TestCommons.getBuffer("2.7/Wandelaar-anya.d2s"));
        assertThat(hasDiedInThePast.died()).isTrue();
    }

    @Test
    void readIronGolem() {
        D2Character ironGolemNecro = cut.parse(TestCommons.getBuffer("2.7/DierentuinIG.d2s"));
        Item ironGolem = ironGolemNecro.golemItem();
        assertThat(ironGolem).isNotNull();

        assertThat(ironGolem.itemName()).isEqualTo("Treads of Cthon");
        assertThat(ironGolem.uniqueId()).isEqualTo((short)109);
        assertThat(ironGolem.properties()).hasSize(6);
        assertThat(ironGolem.fingerPrint()).isEqualTo("0x515ac436");
    }

    @Test
    void hasPointsLeft() {
        D2Character pointsLeft = cut.parse(TestCommons.getBuffer("2.7/MuleSetsOne.d2s"));

        assertThat(pointsLeft.attributes().statPointsLeft()).isEqualTo(5);
        assertThat(pointsLeft.attributes().skillPointsLeft()).isEqualTo(8);
    }

    @Test
    void isenhartLightbrand() {
        D2Character isenhart = cut.parse(TestCommons.getBuffer("2.7/MuleSetsOne.d2s"));

        // set items index on char: 12 (helm), 13 (torso), 39 (sword), 40 (shield)
        final Item lightbrand = isenhart.items().get(39);
        List<ItemProperty> lightbrandProperties = lightbrand.properties();
        assertThat(lightbrandProperties).hasSize(3);
        assertThat(lightbrandProperties.get(0).name()).isEqualTo("item_fasterattackrate");
        assertThat(lightbrandProperties.get(0).values()[0]).isEqualTo(20);
        assertThat(lightbrandProperties.get(1).name()).isEqualTo("mindamage");
        assertThat(lightbrandProperties.get(1).values()[0]).isEqualTo(10);
        assertThat(lightbrand.treasureClass()).isEqualTo(15);
        // 2+ item bonus
        assertThat(lightbrandProperties.get(2).name()).isEqualTo("item_tohit_perlevel");
        assertThat(lightbrandProperties.get(2).values()[0]).isEqualTo(10); // This should be 5, but for some reason it's 2*
    }

    @Test
    void isenhartCase() {
        D2Character isenhartCase = cut.parse(TestCommons.getBuffer("2.7/MuleSetsOne.d2s"));

        // set items index on char: 12 (helm), 13 (torso), 39 (sword), 40 (shield)
        final Item torsoCase = isenhartCase.items().get(13);
        List<ItemProperty> caseProperties = torsoCase.properties();
        assertThat(caseProperties).hasSize(3);
        assertThat(caseProperties.get(1).name()).isEqualTo("armorclass");
        assertThat(caseProperties.get(1).values()[0]).isEqualTo(40);
        assertThat(caseProperties.get(2).name()).isEqualTo("magic_damage_reduction");
        assertThat(caseProperties.get(2).values()[0]).isEqualTo(2);
        assertThat(torsoCase.treasureClass()).isEqualTo(18);
        // 2+ item bonus
        assertThat(caseProperties.get(0).name()).isEqualTo("item_armor_perlevel");
        assertThat(caseProperties.get(0).values()[0]).isEqualTo(16); // this should be 2, but for some reason it's 8*
    }

    @Test
    void isenhartHorns() {
        D2Character isenhartHorns = cut.parse(TestCommons.getBuffer("2.7/MuleSetsOne.d2s"));

        // set items index on char: 12 (helm), 13 (torso), 39 (sword), 40 (shield)
        final Item horns = isenhartHorns.items().get(12);
        List<ItemProperty> hornsProperties = horns.properties();
        assertThat(hornsProperties).hasSize(6);
        assertThat(hornsProperties.get(0).name()).isEqualTo("dexterity");
        assertThat(hornsProperties.get(0).values()[0]).isEqualTo(6);
        assertThat(hornsProperties.get(5).name()).isEqualTo("normal_damage_reduction");
        assertThat(hornsProperties.get(5).values()[0]).isEqualTo(2);
        assertThat(horns.treasureClass()).isEqualTo(15);
        // 2+ item bonus
        assertThat(hornsProperties.get(1).name()).isEqualTo("coldresist");
        assertThat(hornsProperties.get(1).values()[0]).isEqualTo(8);
        assertThat(hornsProperties.get(2).name()).isEqualTo("lightresist");
        assertThat(hornsProperties.get(2).values()[0]).isEqualTo(8);
        assertThat(hornsProperties.get(3).name()).isEqualTo("fireresist");
        assertThat(hornsProperties.get(3).values()[0]).isEqualTo(8);
        assertThat(hornsProperties.get(4).name()).isEqualTo("poisonresist");
        assertThat(hornsProperties.get(4).values()[0]).isEqualTo(8);
    }

    @Test
    void isenhartParry() {
        D2Character isenhart = cut.parse(TestCommons.getBuffer("2.7/MuleSetsOne.d2s"));

        // set items index on char: 12 (helm), 13 (torso), 39 (sword), 40 (shield)
        final Item parry = isenhart.items().get(40);
        List<ItemProperty> parryProperties = parry.properties();
        assertThat(parryProperties).hasSize(6);
        assertThat(parryProperties.get(0).name()).isEqualTo("armorclass");
        assertThat(parryProperties.get(0).values()[0]).isEqualTo(40);
        assertThat(parryProperties.get(5).name()).isEqualTo("item_attackertakeslightdamage");
        assertThat(parryProperties.get(5).values()[0]).isEqualTo(4);
        assertThat(parry.treasureClass()).isEqualTo(30);
        // 2+ item bonus
        assertThat(parryProperties.get(1).name()).isEqualTo("coldresist");
        assertThat(parryProperties.get(1).values()[0]).isEqualTo(8);
        assertThat(parryProperties.get(2).name()).isEqualTo("lightresist");
        assertThat(parryProperties.get(2).values()[0]).isEqualTo(8);
        assertThat(parryProperties.get(3).name()).isEqualTo("fireresist");
        assertThat(parryProperties.get(3).values()[0]).isEqualTo(8);
        assertThat(parryProperties.get(4).name()).isEqualTo("poisonresist");
        assertThat(parryProperties.get(4).values()[0]).isEqualTo(8);
    }

    @Test
    void readFullIsenhartSet() {
        D2Character isenhart = cut.parse(TestCommons.getBuffer("2.7/MuleSetsOne.d2s"));

        List<ItemProperty> benefits = isenhart.equippedSetBenefits();
        assertThat(benefits).hasSize(10);
        // partial bonus, 2 items
        assertThat(benefits.get(0).name()).isEqualTo("strength");
        assertThat(benefits.get(0).values()[0]).isEqualTo(10);
        assertThat(benefits.get(0).qualityFlag()).isEqualTo(22);
        // partial bonus, 3 items
        assertThat(benefits.get(1).name()).isEqualTo("dexterity");
        assertThat(benefits.get(1).values()[0]).isEqualTo(10);
        assertThat(benefits.get(1).qualityFlag()).isEqualTo(23);
        // full set bonus: 35% AR, 5% Lifesteal, 10% res all, 30% block chance, 20% frw
        assertThat(benefits.get(2).name()).isEqualTo("lifedrainmindam");
        assertThat(benefits.get(2).values()[0]).isEqualTo(5);
        assertThat(benefits.get(2).qualityFlag()).isEqualTo(26);
        assertThat(benefits.get(3).name()).isEqualTo("fireresist");
        assertThat(benefits.get(3).values()[0]).isEqualTo(10);
        assertThat(benefits.get(3).qualityFlag()).isEqualTo(26);
        assertThat(benefits.get(4).name()).isEqualTo("lightresist");
        assertThat(benefits.get(4).values()[0]).isEqualTo(10);
        assertThat(benefits.get(4).qualityFlag()).isEqualTo(26);
        assertThat(benefits.get(5).name()).isEqualTo("coldresist");
        assertThat(benefits.get(5).values()[0]).isEqualTo(10);
        assertThat(benefits.get(5).qualityFlag()).isEqualTo(26);
        assertThat(benefits.get(6).name()).isEqualTo("poisonresist");
        assertThat(benefits.get(6).values()[0]).isEqualTo(10);
        assertThat(benefits.get(6).qualityFlag()).isEqualTo(26);
        assertThat(benefits.get(7).name()).isEqualTo("item_tohit_percent");
        assertThat(benefits.get(7).values()[0]).isEqualTo(35);
        assertThat(benefits.get(7).qualityFlag()).isEqualTo(26);
        assertThat(benefits.get(8).name()).isEqualTo("toblock");
        assertThat(benefits.get(8).values()[0]).isEqualTo(30);
        assertThat(benefits.get(8).qualityFlag()).isEqualTo(26);
        assertThat(benefits.get(9).name()).isEqualTo("item_fastermovevelocity");
        assertThat(benefits.get(9).values()[0]).isEqualTo(20);
        assertThat(benefits.get(9).qualityFlag()).isEqualTo(26);
    }

    @Test
    void testKhalim() {
        D2Character khalim = cut.parse(TestCommons.getBuffer("2.8/Sparkles-khalimLos.d2s"));

        List<Item> questItems = khalim.items().stream()
                .filter(item -> item.itemName().startsWith("Khalim"))
                .toList();
        assertThat(questItems).hasSize(4);
    }

    @Test
    void testBird() {
        D2Character sparkles = cut.parse(TestCommons.getBuffer("2.8/Sparkles-crash-bird.d2s"));

        List<Item> questItems = sparkles.items().stream()
                .filter(item -> item.itemName().startsWith("The Golden Bird"))
                .toList();
        assertThat(questItems).hasSize(1);
    }

    @Test
    void testStatue() {
        D2Character sparkles = cut.parse(TestCommons.getBuffer("2.8/Sparkles-crash-statue.d2s"));

        List<Item> questItems = sparkles.items().stream()
                .filter(item -> item.itemName().startsWith("A Jade Figurine"))
                .toList();
        assertThat(questItems).hasSize(1);
    }

    @Test
    void testInifuss() {
        D2Character sparkles = cut.parse(TestCommons.getBuffer("2.8/Sparkles-scroll_of_inifuss.d2s"));

        List<Item> questItems = sparkles.items().stream()
                .filter(item -> item.itemName().startsWith("Scroll of Inifuss"))
                .toList();
        assertThat(questItems).hasSize(1);
    }

    @Test
    void testCairstoneMap() {
        D2Character sparkles = cut.parse(TestCommons.getBuffer("2.8/Fjoerich-cairstone_map.d2s"));

        List<Item> questItems = sparkles.items().stream()
                .filter(item -> item.itemName().startsWith("Key to the Cairn Stones"))
                .toList();
        assertThat(questItems).hasSize(1);
    }

    @Test
    void testCairstoneMapIn77312() {
        D2Character sparkles = cut.parse(TestCommons.getBuffer("1.6.77312/LongestPossible-Cairn.d2s"));

        List<Item> questItems = sparkles.items().stream()
                .filter(item -> item.itemName().startsWith("Key to the Cairn Stones"))
                .toList();
        assertThat(questItems).hasSize(1);
    }

    @Test
    void testStone() {
        D2Character sparkles = cut.parse(TestCommons.getBuffer("2.8/Sparkles-mephistostone.d2s"));

        List<Item> questItems = sparkles.items().stream()
                .filter(item -> item.itemName().startsWith("Mephisto's Soulstone"))
                .toList();
        assertThat(questItems).hasSize(1);
    }

    @Test
    void testPotion() {
        D2Character sparkles = cut.parse(TestCommons.getBuffer("2.8/Sparkles-potion.d2s"));

        List<Item> questItems = sparkles.items().stream()
                .filter(item -> item.itemName().startsWith("Potion of Life"))
                .toList();
        assertThat(questItems).hasSize(1);
    }

    @Test
    void longestPossiblePersonalization() {
        D2Character longestPossible = cut.parse(TestCommons.getBuffer("1.6.77312/LongestPossible.d2s"));
        List<Item> stealth = longestPossible.items().stream()
                .filter(Item::isPersonalized)
                .toList();

        assertThat(stealth).hasSize(1);
        assertThat(stealth.get(0).itemName()).isEqualTo("Stealth");
        assertThat(stealth.get(0).personalizedName()).isEqualTo("LongestPossible");
    }
}