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
import io.github.paladijn.d2rsavegameparser.model.CharacterType;
import io.github.paladijn.d2rsavegameparser.model.D2Character;
import io.github.paladijn.d2rsavegameparser.model.Item;
import io.github.paladijn.d2rsavegameparser.model.ItemContainer;
import io.github.paladijn.d2rsavegameparser.model.ItemLocation;
import io.github.paladijn.d2rsavegameparser.model.ItemProperty;
import io.github.paladijn.d2rsavegameparser.model.Skill;
import io.github.paladijn.d2rsavegameparser.model.SkillType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;

import java.nio.ByteBuffer;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.slf4j.LoggerFactory.getLogger;

class CharacterParserTest {
    private static final Logger log = getLogger(CharacterParserTest.class);

    private final CharacterParser cut = new CharacterParser(true);

    // This is a helper function to debug the contents in case a save game breaks after a patch upgrade or when finding a specific item that we can't parse
    @Test
    void testCharacterFileForExceptions() {
        final ByteBuffer buffer = TestCommons.getBuffer("3.1.91636/AllTheSkills-Demon.d2s");

        final CharacterParser parser = new CharacterParser(true);
        final D2Character parsed = parser.parse(buffer);

        final List<Item> itemsInStash = parsed.items().stream()
                .filter(item -> item.container() == ItemContainer.STASH)
                .toList();
        log.info("output: {}", parsed);
    }

    @Test
    void doNotParseOlderVersion() {
        final ByteBuffer buffer = TestCommons.getBuffer("2.4/Dierentuin.d2s");

        assertThatExceptionOfType(ParseException.class)
                .isThrownBy(() -> cut.parse(buffer))
                .withMessage("Unsupported version: 98");
    }

    @Test
    void throwExceptionOnWrongFileHeader() {
        final ByteBuffer buffer = TestCommons.getBuffer("logback.xml");

        assertThatExceptionOfType(ParseException.class)
                .isThrownBy(() -> cut.parse(buffer))
                .withMessage("Wrong fileHeader 1852793660, this is not a Diablo II saveGame file");
    }

    @Test
    void readNormal() {
        D2Character dierentuin = cut.parse(TestCommons.getBuffer("2.7/Dierentuin.d2s"));

        assertThat(dierentuin.level()).isEqualTo((byte)16);
        assertThat(dierentuin.items()).hasSize(68);
        assertThat(dierentuin.mercenary().items()).hasSize(2);
        assertThat(dierentuin.questDataPerDifficulty().getFirst().socketQuestStarted()).isFalse();
        assertThat(dierentuin.waypoints().getFirst().act2LutGholein()).isFalse();
    }

    @Test
    void readHell() {
        final D2Character lohengrin = cut.parse(TestCommons.getBuffer("2.7/Lohengrin.d2s"));
        assertThat(lohengrin.level()).isEqualTo((byte)76);
        assertThat(lohengrin.questDataPerDifficulty().getFirst().socketQuestRewardAvailable()).isTrue();
        assertThat(lohengrin.questDataPerDifficulty().get(1).socketQuestStarted()).isTrue();
        assertThat(lohengrin.questDataPerDifficulty().get(1).resistanceScrollRead()).isTrue();
        assertThat(lohengrin.actProgression()).isEqualTo((byte)11);
        assertThat(lohengrin.items()).hasSize(123);

        // and the merc is alive
        assertThat(lohengrin.mercenary().alive()).isTrue();
    }

    @Test
    void readNewChar() {
        final D2Character instant = cut.parse(TestCommons.getBuffer("3.1.91636/instant.d2s"));

        // as this character is new it only has the start attributes to display.
        assertThat(instant.attributes().strength()).isEqualTo(15);
        assertThat(instant.attributes().dexterity()).isEqualTo(20);
        assertThat(instant.attributes().energy()).isEqualTo(20);
        assertThat(instant.attributes().vitality()).isEqualTo(25);
        assertThat(instant.attributes().hp()).isEqualTo(55);
        assertThat(instant.attributes().mana()).isEqualTo(20);
        assertThat(instant.level()).isEqualTo((byte)1);

        assertThat(instant.skills()).isEmpty();
    }

    @Test
    void deadItems() {
        D2Character deadBody = cut.parse(TestCommons.getBuffer("2.7/itsDeadJim.d2s"));
        assertThat(deadBody.died()).isTrue();
        assertThat(deadBody.deadBodyItems()).hasSize(1); // the staff is the only item on your dead body
        assertThat(deadBody.deadBodyItems().getFirst().type()).isEqualTo("staf");
        assertThat(deadBody.items()).hasSize(6); // items still left on your character (pots, scrolls)
        // no merc as this is a new character
        assertThat(deadBody.mercenary()).isNull();
    }

    @Test
    void deadWithMerc() {
        D2Character deadBody = cut.parse(TestCommons.getBuffer("1.6.84219/DeadWithMerc.d2s"));
        assertThat(deadBody.died()).isTrue();
        assertThat(deadBody.deadBodyItems()).hasSize(4);
        assertThat(deadBody.deadBodyItems().getFirst().type()).isEqualTo("helm");
        assertThat(deadBody.items()).hasSize(15); // items still left on your character (pots, scrolls, inventory)

        // and validate the Merc items were parsed correctly as well, despite being dead too.
        assertThat(deadBody.mercenary().alive()).isFalse();
        assertThat(deadBody.mercenary().items()).hasSize(2);
        // with a socketed bow
        assertThat(deadBody.mercenary().items().getLast().socketedItems()).hasSize(3);
        assertThat(deadBody.mercenary().items().getLast().properties()).hasSize(10);
    }

    @Test
    void anyaScroll() {
        D2Character scrollActive = cut.parse(TestCommons.getBuffer("2.7/Wandelaar-anya.d2s"));
        assertThat(scrollActive.questDataPerDifficulty().getFirst().resistanceScrollRead()).isTrue();
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
        assertThat(lightbrandProperties.getFirst().name()).isEqualTo("item_fasterattackrate");
        assertThat(lightbrandProperties.getFirst().values()[0]).isEqualTo(20);
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
        assertThat(caseProperties.getFirst().name()).isEqualTo("item_armor_perlevel");
        assertThat(caseProperties.getFirst().values()[0]).isEqualTo(16); // this should be 2, but for some reason it's 8*
    }

    @Test
    void isenhartHorns() {
        D2Character isenhartHorns = cut.parse(TestCommons.getBuffer("2.7/MuleSetsOne.d2s"));

        // set items index on char: 12 (helm), 13 (torso), 39 (sword), 40 (shield)
        final Item horns = isenhartHorns.items().get(12);
        List<ItemProperty> hornsProperties = horns.properties();
        assertThat(hornsProperties).hasSize(6);
        assertThat(hornsProperties.getFirst().name()).isEqualTo("dexterity");
        assertThat(hornsProperties.getFirst().values()[0]).isEqualTo(6);
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
        assertThat(parryProperties.getFirst().name()).isEqualTo("armorclass");
        assertThat(parryProperties.getFirst().values()[0]).isEqualTo(40);
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
        assertThat(benefits.getFirst().name()).isEqualTo("strength");
        assertThat(benefits.getFirst().values()[0]).isEqualTo(10);
        assertThat(benefits.getFirst().qualityFlag()).isEqualTo(22);
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

    @ParameterizedTest
    @CsvSource({
            "2.8/Sparkles-crash-bird.d2s, The Golden Bird",
            "2.8/Sparkles-crash-statue.d2s, A Jade Figurine",
            "2.8/Sparkles-scroll_of_inifuss.d2s, Scroll of Inifuss",
            "2.8/Fjoerich-cairstone_map.d2s, Key to the Cairn Stones",
            "1.6.77312/LongestPossible-Cairn.d2s, Key to the Cairn Stones",
            "2.8/Sparkles-mephistostone.d2s, Mephisto's Soulstone",
            "2.8/Sparkles-potion.d2s, Potion of Life"
    })
    void testSingularQuestItem(String saveGameLocation, String itemName) {
        D2Character d2Character = cut.parse(TestCommons.getBuffer(saveGameLocation));

        List<Item> questItems = d2Character.items().stream()
                .filter(item -> item.itemName().startsWith(itemName))
                .toList();

        log.info("verifying there is one item with name {} in {}", itemName, saveGameLocation);

        assertThat(questItems).hasSize(1);
    }

    @Test
    void longestPossiblePersonalization() {
        D2Character longestPossible = cut.parse(TestCommons.getBuffer("1.6.77312/LongestPossible.d2s"));
        List<Item> stealth = longestPossible.items().stream()
                .filter(Item::isPersonalized)
                .toList();

        assertThat(stealth).hasSize(1);
        assertThat(stealth.getFirst().itemName()).isEqualTo("Stealth");
        assertThat(stealth.getFirst().personalizedName()).isEqualTo("LongestPossible");
    }

    @Test
    void maxStacksOnMisc() {
        final D2Character longestPossible = cut.parse(TestCommons.getBuffer("1.6.77312/LongestPossible.d2s"));
        final List<Item> stackables = longestPossible.items().stream()
                .filter(item -> item.maxStacks() > 0)
                .toList();

        assertThat(stackables).hasSize(3);
        assertThat(stackables.getFirst().itemName()).isEqualTo("Key");
        assertThat(stackables.getLast().itemName()).isEqualTo("Tome of Town Portal");
    }

    @Test
    void testPaladinPassiveBonusesFromSkills() {
        D2Character d2Character = cut.parse(TestCommons.getBuffer("1.6.77312/Fjoerich-max-res.d2s"));

        List<Skill> skillsWithBenefits = d2Character.skills().stream().filter(skill -> !skill.passiveBonuses().isEmpty()).toList();

        assertThat(skillsWithBenefits).hasSize(3);
        assertThat(skillsWithBenefits.getFirst().passiveBonuses().getFirst().name()).isEqualTo("maxfireresist");
        assertThat(skillsWithBenefits.getFirst().passiveBonuses().getFirst().values()[0]).isEqualTo(10);

        assertThat(skillsWithBenefits.get(1).passiveBonuses().getFirst().name()).isEqualTo("maxcoldresist");
        assertThat(skillsWithBenefits.get(1).passiveBonuses().getFirst().values()[0]).isZero();

        assertThat(skillsWithBenefits.getLast().passiveBonuses().getFirst().name()).isEqualTo("maxlightresist");
        assertThat(skillsWithBenefits.getLast().passiveBonuses().getFirst().values()[0]).isEqualTo(4);
    }

    @Test
    void testNaturalResistanceBonuses() {
        D2Character d2Character = cut.parse(TestCommons.getBuffer("1.6.77312/Schreeuw.d2s"));

        List<Skill> skillsWithBenefits = d2Character.skills().stream().filter(skill -> !skill.passiveBonuses().isEmpty()).toList();
        assertThat(skillsWithBenefits).hasSize(1);
        assertThat(skillsWithBenefits.getFirst().skillType()).isEqualTo(SkillType.NATURAL_RESISTANCE);
        assertThat(skillsWithBenefits.getFirst().level()).isEqualTo((byte) 1); // base level, +2 from items

        final List<ItemProperty> naturalRes = skillsWithBenefits.getFirst().passiveBonuses();
        assertThat(naturalRes).hasSize(4);
        assertThat(naturalRes.getFirst().name()).isEqualTo("fireresist");
        assertThat(naturalRes.getFirst().values()[0]).isEqualTo(28);

        assertThat(naturalRes.get(1).name()).isEqualTo("lightresist");
        assertThat(naturalRes.get(1).values()[0]).isEqualTo(28);

        assertThat(naturalRes.get(2).name()).isEqualTo("coldresist");
        assertThat(naturalRes.get(2).values()[0]).isEqualTo(28);

        assertThat(naturalRes.get(3).name()).isEqualTo("poisonresist");
        assertThat(naturalRes.get(3).values()[0]).isEqualTo(28);
    }

    @Test
    void shouldParseIncorrectPrefix() {
        D2Character d2Character = cut.parse(TestCommons.getBuffer("1.6.80273/Assassin.d2s"));

        List<Item> brokenJewels = d2Character.items().stream().filter(item -> item.itemName().equals("Jewel")).toList();

        assertThat(brokenJewels).hasSize(6);
        assertThat(brokenJewels.getFirst().prefixIds().getFirst()).isEqualTo((short)2047);
        assertThat(brokenJewels.getFirst().suffixIds().getFirst()).isEqualTo((short)2047);
    }

    @Test
    void shouldParseClassicCharacter() {
        D2Character d2Character = cut.parse(TestCommons.getBuffer("1.6.84219/Classic.d2s"));

        assertThat(d2Character.attributes().hp()).isEqualTo(72);
        assertThat(d2Character.attributes().maxHP()).isEqualTo(74);
        assertThat(d2Character.items()).hasSize(20);
        assertThat(d2Character.golemItem()).isNull();
    }

    @Test
    void shouldParseClassicDeadChar() {
        D2Character d2Character = cut.parse(TestCommons.getBuffer("1.6.84219/RIPClassic.d2s"));

        assertThat(d2Character.died()).isTrue();
        assertThat(d2Character.deadBodyItems()).hasSize(3);
    }

    @Test
    void shouldIgnoreDoubleEquippedSetItems() {
        final D2Character fourHsarus = cut.parse(TestCommons.getBuffer("2.8/Sparkles-above75percent.d2s"));

        assertThat(fourHsarus.equippedSetBenefits()).hasSize(4); // we received four benefits from the fully equipped set

        // the iron fist is equipped twice
        assertThat(fourHsarus.items().stream()
                .filter(item -> item.location() == ItemLocation.EQUIPPED && item.itemName().equals("Hsarus' Iron Fist"))
                .count()).isEqualTo(2);
    }

    @Test
    void verifyMapSeed() {
        final D2Character wandelaar = cut.parse(TestCommons.getBuffer("1.6.84219/RIPClassic.d2s"));

        assertThat(wandelaar.mapId()).isEqualTo(1453926620L);
    }

    @Test
    void testNewWarlock() {
        final D2Character newLock = cut.parse(TestCommons.getBuffer("3.1.91636/Nieuw.d2s"));

        assertThat(newLock.characterType()).isEqualTo(CharacterType.WARLOCK);
        assertThat(newLock.riseOfTheWarlock()).isTrue().as("The character should be marked as a Rise of the Warlock one.");
        assertThat(newLock.items()).hasSize(7); // 4 pots, 2 scrolls and a dagger

        final Item dagger = newLock.items().getLast();
        assertThat(dagger.itemName()).isEqualTo("Dagger");
        assertThat(dagger.properties()).hasSize(1);

        final ItemProperty starterSkill = dagger.properties().getFirst();
        assertThat(starterSkill.name()).isEqualTo("item_singleskill");
        assertThat(starterSkill.values()[0]).isEqualTo(SkillType.MIASMA_BOLT.getId());
    }

    @Test
    void demonSkills() {
        final D2Character newLock = cut.parse(TestCommons.getBuffer("3.1.91636/AllTheSkills-Demon.d2s"));

        assertThat(newLock.characterType()).isEqualTo(CharacterType.WARLOCK);
        assertThat(newLock.skills()).hasSize(30);

        assertThat(newLock.skills().getFirst()).isEqualTo(new Skill(SkillType.SUMMON_GOATMAN, (byte)2, List.of()));
        assertThat(newLock.skills().get(1)).isEqualTo(new Skill(SkillType.DEMONIC_MASTERY, (byte)1, List.of()));
        assertThat(newLock.skills().get(2)).isEqualTo(new Skill(SkillType.DEATH_MARK, (byte)4, List.of()));
        assertThat(newLock.skills().get(3)).isEqualTo(new Skill(SkillType.SUMMON_TAINTED, (byte)5, List.of()));
        assertThat(newLock.skills().get(4)).isEqualTo(new Skill(SkillType.SUMMON_DEFILER, (byte)7, List.of()));
        assertThat(newLock.skills().get(5)).isEqualTo(new Skill(SkillType.BLOOD_OATH, (byte)3, List.of()));
        assertThat(newLock.skills().get(6)).isEqualTo(new Skill(SkillType.ENGORGE, (byte)4, List.of()));
        assertThat(newLock.skills().get(7)).isEqualTo(new Skill(SkillType.BLOOD_BOIL, (byte)6, List.of()));
        assertThat(newLock.skills().get(8)).isEqualTo(new Skill(SkillType.CONSUME, (byte)1, List.of()));
        assertThat(newLock.skills().get(9)).isEqualTo(new Skill(SkillType.BIND_DEMON, (byte)2, List.of()));
    }

    @Test
    void eldritchSkills() {
        final D2Character newLock = cut.parse(TestCommons.getBuffer("3.1.91636/AllTheSkills-Eldritch.d2s"));

        assertThat(newLock.characterType()).isEqualTo(CharacterType.WARLOCK);
        assertThat(newLock.skills()).hasSize(30);

        assertThat(newLock.skills().get(10)).isEqualTo(new Skill(SkillType.LEVITATION_MASTERY, (byte)1, List.of()));
        assertThat(newLock.skills().get(11)).isEqualTo(new Skill(SkillType.ELDRITCH_BLAST, (byte)3, List.of()));
        assertThat(newLock.skills().get(12)).isEqualTo(new Skill(SkillType.HEX_BANE, (byte)2, List.of()));
        assertThat(newLock.skills().get(13)).isEqualTo(new Skill(SkillType.HEX_SIPHON, (byte)2, List.of()));
        assertThat(newLock.skills().get(14)).isEqualTo(new Skill(SkillType.PSYCHIC_WARD, (byte)7, List.of()));
        assertThat(newLock.skills().get(15)).isEqualTo(new Skill(SkillType.ECHOING_STRIKE, (byte)4, List.of()));
        assertThat(newLock.skills().get(16)).isEqualTo(new Skill(SkillType.HEX_PURGE, (byte)5, List.of()));
        assertThat(newLock.skills().get(17)).isEqualTo(new Skill(SkillType.BLADE_WARP, (byte)6, List.of()));
        assertThat(newLock.skills().get(18)).isEqualTo(new Skill(SkillType.CLEAVE, (byte)3, List.of()));
        assertThat(newLock.skills().get(19)).isEqualTo(new Skill(SkillType.MIRRORED_BLADES, (byte)1, List.of()));
    }

    @Test
    void chaosSkills() {
        final D2Character newLock = cut.parse(TestCommons.getBuffer("3.1.91636/AllTheSkills-Chaos.d2s"));

        assertThat(newLock.characterType()).isEqualTo(CharacterType.WARLOCK);
        assertThat(newLock.skills()).hasSize(30);

        assertThat(newLock.skills().get(20)).isEqualTo(new Skill(SkillType.SIGIL_LETHARGY, (byte)3, List.of()));
        assertThat(newLock.skills().get(21)).isEqualTo(new Skill(SkillType.RING_OF_FIRE, (byte)2, List.of()));
        assertThat(newLock.skills().get(22)).isEqualTo(new Skill(SkillType.MIASMA_BOLT, (byte)1, List.of()));
        assertThat(newLock.skills().get(23)).isEqualTo(new Skill(SkillType.SIGIL_RANCOR, (byte)4, List.of()));
        assertThat(newLock.skills().get(24)).isEqualTo(new Skill(SkillType.ENHANCED_ENTROPY, (byte)3, List.of()));
        assertThat(newLock.skills().get(25)).isEqualTo(new Skill(SkillType.FLAME_WAVE, (byte)6, List.of()));
        assertThat(newLock.skills().get(26)).isEqualTo(new Skill(SkillType.MIASMA_CHAIN, (byte)5, List.of()));
        assertThat(newLock.skills().get(27)).isEqualTo(new Skill(SkillType.SIGIL_DEATH, (byte)7, List.of()));
        assertThat(newLock.skills().get(28)).isEqualTo(new Skill(SkillType.APOCALYPSE, (byte)2, List.of()));
        assertThat(newLock.skills().get(29)).isEqualTo(new Skill(SkillType.ABYSS, (byte)1, List.of()));
    }
}
