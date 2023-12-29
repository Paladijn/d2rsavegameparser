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
package com.github.paladijn.d2rsavegameparser.model;

import java.util.Arrays;
import java.util.List;

import static com.github.paladijn.d2rsavegameparser.model.CharacterType.AMAZON;
import static com.github.paladijn.d2rsavegameparser.model.CharacterType.ASSASSIN;
import static com.github.paladijn.d2rsavegameparser.model.CharacterType.BARBARIAN;
import static com.github.paladijn.d2rsavegameparser.model.CharacterType.DRUID;
import static com.github.paladijn.d2rsavegameparser.model.CharacterType.NECROMANCER;
import static com.github.paladijn.d2rsavegameparser.model.CharacterType.NONE;
import static com.github.paladijn.d2rsavegameparser.model.CharacterType.PALADIN;
import static com.github.paladijn.d2rsavegameparser.model.CharacterType.SORCERESS;
import static com.github.paladijn.d2rsavegameparser.model.SkillTree.BOW;
import static com.github.paladijn.d2rsavegameparser.model.SkillTree.COLD;
import static com.github.paladijn.d2rsavegameparser.model.SkillTree.COMBAT;
import static com.github.paladijn.d2rsavegameparser.model.SkillTree.COMBAT_BARB;
import static com.github.paladijn.d2rsavegameparser.model.SkillTree.CURSES;
import static com.github.paladijn.d2rsavegameparser.model.SkillTree.DEFENSIVE;
import static com.github.paladijn.d2rsavegameparser.model.SkillTree.ELEMENTAL;
import static com.github.paladijn.d2rsavegameparser.model.SkillTree.FIRE;
import static com.github.paladijn.d2rsavegameparser.model.SkillTree.JAVELIN;
import static com.github.paladijn.d2rsavegameparser.model.SkillTree.MARTIAL;
import static com.github.paladijn.d2rsavegameparser.model.SkillTree.MASTERIES;
import static com.github.paladijn.d2rsavegameparser.model.SkillTree.OFFENSIVE;
import static com.github.paladijn.d2rsavegameparser.model.SkillTree.PASSIVE;
import static com.github.paladijn.d2rsavegameparser.model.SkillTree.POISON_BONE;
import static com.github.paladijn.d2rsavegameparser.model.SkillTree.SHADOW;
import static com.github.paladijn.d2rsavegameparser.model.SkillTree.SHAPESHIFT;
import static com.github.paladijn.d2rsavegameparser.model.SkillTree.SUMMON;
import static com.github.paladijn.d2rsavegameparser.model.SkillTree.SUMMON_DRUID;
import static com.github.paladijn.d2rsavegameparser.model.SkillTree.TRAPS;
import static com.github.paladijn.d2rsavegameparser.model.SkillTree.UNKNOWN;

/**
 * Representation of a skill containing the id used in the save game as well as the {@link CharacterType} and {@link SkillTree} it belongs to.
 * <p>
 * Note: these were auto-generated from the skills.txt file, so if you see any strange names like wearwolf: they match the ones Blizzard supplied.
 */
public enum SkillType {
    ATTACK(0, 0, NONE, UNKNOWN),
    KICK(1, 0, NONE, UNKNOWN),
    THROW(2, 0, NONE, UNKNOWN),
    UNSUMMON(3, 0, NONE, UNKNOWN),
    LEFT_HAND_THROW(4, 0, NONE, UNKNOWN),
    LEFT_HAND_SWING(5, 0, NONE, UNKNOWN),
    // --- Amazon ---
    MAGIC_ARROW(6, 0, AMAZON, BOW),
    FIRE_ARROW(7, 1, AMAZON, BOW),
    INNER_SIGHT(8, 2, AMAZON, PASSIVE),
    CRITICAL_STRIKE(9, 3, AMAZON, PASSIVE),
    JAB(10, 4, AMAZON, JAVELIN),
    COLD_ARROW(11, 5, AMAZON, BOW),
    MULTIPLE_SHOT(12, 6, AMAZON, BOW),
    DODGE(13, 7, AMAZON, PASSIVE),
    POWER_STRIKE(14, 8, AMAZON, JAVELIN),
    POISON_JAVELIN(15, 9, AMAZON, JAVELIN),
    EXPLODING_ARROW(16, 10, AMAZON, BOW),
    SLOW_MISSILES(17, 11, AMAZON, PASSIVE),
    AVOID(18, 12, AMAZON, PASSIVE),
    IMPALE(19, 13, AMAZON, JAVELIN),
    LIGHTNING_BOLT(20, 14, AMAZON, JAVELIN),
    ICE_ARROW(21, 15, AMAZON, BOW),
    GUIDED_ARROW(22, 16, AMAZON, BOW),
    PENETRATE(23,17, AMAZON, PASSIVE),
    CHARGED_STRIKE(24, 18, AMAZON, JAVELIN),
    PLAGUE_JAVELIN(25, 19, AMAZON, JAVELIN),
    STRAFE(26, 20, AMAZON, BOW),
    IMMOLATION_ARROW(27, 21, AMAZON, BOW),
    DOPPLEZON(28, 22, AMAZON, PASSIVE),
    EVADE(29, 23, AMAZON, PASSIVE),
    FEND(30, 24, AMAZON, JAVELIN),
    FREEZING_ARROW(31, 25, AMAZON, BOW),
    VALKYRIE(32, 26, AMAZON, PASSIVE),
    PIERCE(33, 27, AMAZON, PASSIVE),
    LIGHTNING_STRIKE(34, 28, AMAZON, JAVELIN),
    LIGHTNING_FURY(35, 29, AMAZON, JAVELIN),
    // --- Sorceress ---
    FIRE_BOLT(36, 0, SORCERESS, FIRE),
    WARMTH(37, 1, SORCERESS, FIRE),
    CHARGED_BOLT(38, 2, SORCERESS, SkillTree.LIGHTNING),
    ICE_BOLT(39, 3, SORCERESS, COLD),
    FROZEN_ARMOR(40, 4, SORCERESS, COLD),
    INFERNO(41, 5, SORCERESS, FIRE),
    STATIC_FIELD(42, 6, SORCERESS, SkillTree.LIGHTNING),
    TELEKINESIS(43, 7, SORCERESS, SkillTree.LIGHTNING),
    FROST_NOVA(44, 8, SORCERESS, COLD),
    ICE_BLAST(45, 9, SORCERESS, COLD),
    BLAZE(46, 10, SORCERESS, FIRE),
    FIRE_BALL(47, 11, SORCERESS, FIRE),
    NOVA(48, 12, SORCERESS, SkillTree.LIGHTNING),
    LIGHTNING(49, 13, SORCERESS, SkillTree.LIGHTNING),
    SHIVER_ARMOR(50, 14, SORCERESS, COLD),
    FIRE_WALL(51, 15, SORCERESS, FIRE),
    ENCHANT(52, 16, SORCERESS, FIRE),
    CHAIN_LIGHTNING(53, 17, SORCERESS, SkillTree.LIGHTNING),
    TELEPORT(54, 18, SORCERESS, SkillTree.LIGHTNING),
    GLACIAL_SPIKE(55, 19, SORCERESS, COLD),
    METEOR(56, 20, SORCERESS, FIRE),
    THUNDER_STORM(57, 21, SORCERESS, SkillTree.LIGHTNING),
    ENERGY_SHIELD(58, 22, SORCERESS, SkillTree.LIGHTNING),
    BLIZZARD(59, 23, SORCERESS, COLD),
    CHILLING_ARMOR(60, 24, SORCERESS, COLD),
    FIRE_MASTERY(61, 25, SORCERESS, FIRE),
    HYDRA(62, 26, SORCERESS, FIRE),
    LIGHTNING_MASTERY(63, 27, SORCERESS, SkillTree.LIGHTNING),
    FROZEN_ORB(64, 28, SORCERESS, COLD),
    COLD_MASTERY(65, 29, SORCERESS, COLD),
    // --- Necromancer ---
    AMPLIFY_DAMAGE(66, 0, NECROMANCER, CURSES),
    TEETH(67, 1, NECROMANCER, POISON_BONE),
    BONE_ARMOR(68, 2, NECROMANCER, POISON_BONE),
    SKELETON_MASTERY(69, 3, NECROMANCER, SUMMON),
    RAISE_SKELETON(70, 4, NECROMANCER, SUMMON),
    DIM_VISION(71, 5, NECROMANCER, CURSES),
    WEAKEN(72, 6, NECROMANCER, CURSES),
    POISON_DAGGER(73, 7, NECROMANCER, POISON_BONE),
    CORPSE_EXPLOSION(74, 8, NECROMANCER, POISON_BONE),
    CLAY_GOLEM(75, 9, NECROMANCER, SUMMON),
    IRON_MAIDEN(76, 10, NECROMANCER, CURSES),
    TERROR(77, 11, NECROMANCER, CURSES),
    BONE_WALL(78, 12, NECROMANCER, POISON_BONE),
    GOLEM_MASTERY(79, 13, NECROMANCER, SUMMON),
    RAISE_SKELETAL_MAGE(80, 14, NECROMANCER, SUMMON),
    CONFUSE(81, 15, NECROMANCER, CURSES),
    LIFE_TAP(82, 16, NECROMANCER, CURSES),
    POISON_EXPLOSION(83, 17, NECROMANCER, POISON_BONE),
    BONE_SPEAR(84, 18, NECROMANCER, POISON_BONE),
    BLOODGOLEM(85, 19, NECROMANCER, SUMMON),
    ATTRACT(86, 20, NECROMANCER, CURSES),
    DECREPIFY(87, 21, NECROMANCER, CURSES),
    BONE_PRISON(88, 22, NECROMANCER, POISON_BONE),
    SUMMON_RESIST(89, 23, NECROMANCER, SUMMON),
    IRONGOLEM(90, 24, NECROMANCER, SUMMON),
    LOWER_RESIST(91, 25, NECROMANCER, CURSES),
    POISON_NOVA(92, 26, NECROMANCER, POISON_BONE),
    BONE_SPIRIT(93, 27, NECROMANCER, POISON_BONE),
    FIREGOLEM(94, 28, NECROMANCER, SUMMON),
    REVIVE(95, 29, NECROMANCER, SUMMON),
    // --- Paladin ---
    SACRIFICE(96, 0, PALADIN, COMBAT),
    SMITE(97, 1, PALADIN, COMBAT),
    MIGHT(98, 2, PALADIN, OFFENSIVE),
    PRAYER(99, 3, PALADIN, DEFENSIVE),
    RESIST_FIRE(100, 4, PALADIN, DEFENSIVE),
    HOLY_BOLT(101, 5, PALADIN, COMBAT),
    HOLY_FIRE(102, 6, PALADIN, OFFENSIVE),
    THORNS(103, 7, PALADIN, OFFENSIVE),
    DEFIANCE(104, 8, PALADIN, DEFENSIVE),
    RESIST_COLD(105, 9, PALADIN, DEFENSIVE),
    ZEAL(106, 10, PALADIN, COMBAT),
    CHARGE(107, 11, PALADIN, COMBAT),
    BLESSED_AIM(108, 12, PALADIN, OFFENSIVE),
    CLEANSING(109, 13, PALADIN, DEFENSIVE),
    RESIST_LIGHTNING(110, 14, PALADIN, DEFENSIVE),
    VENGEANCE(111, 15, PALADIN, COMBAT),
    BLESSED_HAMMER(112, 16, PALADIN, COMBAT),
    CONCENTRATION(113, 17, PALADIN, OFFENSIVE),
    HOLY_FREEZE(114, 18, PALADIN, OFFENSIVE),
    VIGOR(115, 19, PALADIN, DEFENSIVE),
    CONVERSION(116, 20, PALADIN, COMBAT),
    HOLY_SHIELD(117, 21, PALADIN, COMBAT),
    HOLY_SHOCK(118, 22, PALADIN, OFFENSIVE),
    SANCTUARY(119, 23, PALADIN, OFFENSIVE),
    MEDITATION(120, 24, PALADIN, DEFENSIVE),
    FIST_OF_THE_HEAVENS(121, 25, PALADIN, COMBAT),
    FANATICISM(122, 26, PALADIN, OFFENSIVE),
    CONVICTION(123, 27, PALADIN, OFFENSIVE),
    REDEMPTION(124, 28, PALADIN, DEFENSIVE),
    SALVATION(125, 29, PALADIN, DEFENSIVE),
    // --- Barbie ---
    BASH(126, 0, BARBARIAN, COMBAT_BARB),
    BLADE_MASTERY(127, 1, BARBARIAN, MASTERIES),
    AXE_MASTERY(128, 2, BARBARIAN, MASTERIES),
    MACE_MASTERY(129, 3, BARBARIAN, MASTERIES),
    HOWL(130, 4, BARBARIAN, SkillTree.SHOUT),
    FIND_POTION(131, 5, BARBARIAN, SkillTree.SHOUT),
    LEAP(132, 6, BARBARIAN, COMBAT_BARB),
    DOUBLE_SWING(133, 7, BARBARIAN, COMBAT_BARB),
    POLE_ARM_MASTERY(134, 8, BARBARIAN, MASTERIES),
    THROWING_MASTERY(135, 9, BARBARIAN, MASTERIES),
    SPEAR_MASTERY(136, 10, BARBARIAN, MASTERIES),
    TAUNT(137, 11, BARBARIAN, SkillTree.SHOUT),
    SHOUT(138, 12, BARBARIAN, SkillTree.SHOUT),
    STUN(139, 13, BARBARIAN, COMBAT_BARB),
    DOUBLE_THROW(140, 14, BARBARIAN, COMBAT_BARB),
    INCREASED_STAMINA(141, 15, BARBARIAN, MASTERIES),
    FIND_ITEM(142, 16, BARBARIAN, SkillTree.SHOUT),
    LEAP_ATTACK(143, 17, BARBARIAN, COMBAT_BARB),
    CONCENTRATE(144, 18, BARBARIAN, COMBAT_BARB),
    IRON_SKIN(145, 19, BARBARIAN, MASTERIES),
    BATTLE_CRY(146, 20, BARBARIAN, SkillTree.SHOUT),
    FRENZY(147, 21, BARBARIAN, COMBAT_BARB),
    INCREASED_SPEED(148, 22, BARBARIAN, MASTERIES),
    BATTLE_ORDERS(149, 23, BARBARIAN, MASTERIES),
    GRIM_WARD(150, 24, BARBARIAN, SkillTree.SHOUT),
    WHIRLWIND(151, 25, BARBARIAN, COMBAT_BARB),
    BERSERK(152, 26, BARBARIAN, COMBAT_BARB),
    NATURAL_RESISTANCE(153, 27, BARBARIAN, MASTERIES),
    WAR_CRY(154, 28, BARBARIAN, SkillTree.SHOUT),
    BATTLE_COMMAND(155, 29, BARBARIAN, SkillTree.SHOUT),
    // --- Druid ---
    RAVEN(221, 0, DRUID, SUMMON_DRUID),
    PLAGUE_POPPY(222, 1, DRUID, SUMMON_DRUID),
    WEARWOLF(223, 2, DRUID, SHAPESHIFT),
    SHAPE_SHIFTING(224, 3, DRUID, SHAPESHIFT),
    FIRESTORM(225, 4, DRUID, ELEMENTAL),
    OAK_SAGE(226, 5, DRUID, SUMMON_DRUID),
    SUMMON_SPIRIT_WOLF(227, 6, DRUID, SUMMON_DRUID),
    WEARBEAR(228, 7, DRUID, SHAPESHIFT),
    MOLTEN_BOULDER(229, 8, DRUID, ELEMENTAL),
    ARCTIC_BLAST(230, 9, DRUID, ELEMENTAL),
    CYCLE_OF_LIFE(231, 10, DRUID, SUMMON_DRUID),
    FERAL_RAGE(232, 11, DRUID, SHAPESHIFT),
    MAUL(233, 12, DRUID, SHAPESHIFT),
    ERUPTION(234, 13, DRUID, ELEMENTAL),
    CYCLONE_ARMOR(235, 14, DRUID, ELEMENTAL),
    HEART_OF_WOLVERINE(236, 15, DRUID, SUMMON_DRUID),
    SUMMON_FENRIS(237, 16, DRUID, SUMMON_DRUID),
    RABIES(238, 17, DRUID, SHAPESHIFT),
    FIRE_CLAWS(239, 18, DRUID, SHAPESHIFT),
    TWISTER(240, 19, DRUID, ELEMENTAL),
    VINES(241, 20, DRUID, SUMMON_DRUID),
    HUNGER(242, 21, DRUID, SHAPESHIFT),
    SHOCK_WAVE(243, 22, DRUID, SHAPESHIFT),
    VOLCANO(244, 23, DRUID, ELEMENTAL),
    TORNADO(245, 24, DRUID, ELEMENTAL),
    SPIRIT_OF_BARBS(246, 25, DRUID, SUMMON_DRUID),
    SUMMON_GRIZZLY(247, 26, DRUID, SUMMON_DRUID),
    FURY(248, 27, DRUID, SHAPESHIFT),
    ARMAGEDDON(249, 28, DRUID, ELEMENTAL),
    HURRICANE(250, 29, DRUID, ELEMENTAL),
    // --- assassin ---
    FIRE_TRAUMA(251, 0, ASSASSIN, TRAPS),
    CLAW_MASTERY(252, 1, ASSASSIN, SHADOW),
    PSYCHIC_HAMMER(253, 2, ASSASSIN, SHADOW),
    TIGER_STRIKE(254, 3, ASSASSIN, MARTIAL),
    DRAGON_TALON(255, 4, ASSASSIN, MARTIAL),
    SHOCK_FIELD(256, 5, ASSASSIN, TRAPS),
    BLADE_SENTINEL(257, 6, ASSASSIN, TRAPS),
    QUICKNESS(258, 7, ASSASSIN, SHADOW),
    FISTS_OF_FIRE(259, 8, ASSASSIN, MARTIAL),
    DRAGON_CLAW(260, 9, ASSASSIN, MARTIAL),
    CHARGED_BOLT_SENTRY(261, 10, ASSASSIN, TRAPS),
    WAKE_OF_FIRE_SENTRY(262, 11, ASSASSIN, TRAPS),
    WEAPON_BLOCK(263, 12, ASSASSIN, SHADOW),
    CLOAK_OF_SHADOWS(264, 13, ASSASSIN, SHADOW),
    COBRA_STRIKE(265, 14, ASSASSIN, MARTIAL),
    BLADE_FURY(266, 15, ASSASSIN, TRAPS),
    FADE(267, 16, ASSASSIN, SHADOW),
    SHADOW_WARRIOR(268, 17, ASSASSIN, SHADOW),
    CLAWS_OF_THUNDER(269, 18, ASSASSIN, MARTIAL),
    DRAGON_TAIL(270, 19, ASSASSIN, MARTIAL),
    LIGHTNING_SENTRY(271, 20, ASSASSIN, TRAPS),
    INFERNO_SENTRY(272, 21, ASSASSIN, TRAPS),
    MIND_BLAST(273, 22, ASSASSIN, SHADOW),
    BLADES_OF_ICE(274, 23, ASSASSIN, MARTIAL),
    DRAGON_FLIGHT(275, 24, ASSASSIN, MARTIAL),
    DEATH_SENTRY(276, 25, ASSASSIN, TRAPS),
    BLADE_SHIELD(277, 26, ASSASSIN, TRAPS),
    VENOM(278, 27, ASSASSIN, SHADOW),
    SHADOW_MASTER(279, 28, ASSASSIN, SHADOW),
    ROYAL_STRIKE(280, 29, ASSASSIN, MARTIAL);

    private final byte id;

    private final int offset;

    private final CharacterType characterType;

    private final SkillTree skillTree;

    SkillType(int id, int offset, CharacterType characterType, SkillTree skillTree) {
        this.id = (byte) id;
        this.offset = offset;
        this.characterType = characterType;
        this.skillTree = skillTree;
    }

    public byte getId() {
        return id;
    }

    public int getOffset() {
        return offset;
    }

    public CharacterType getCharacterType() {
        return characterType;
    }

    public SkillTree getSkillTree() {
        return skillTree;
    }

    public static List<SkillType> getSkillListForCharacter(CharacterType characterType) {
        return Arrays.stream(values()).filter(st -> st.characterType == characterType).toList();
    }
}
