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

import java.util.Arrays;
import java.util.List;

import static io.github.paladijn.d2rsavegameparser.model.SkillTree.BOW;
import static io.github.paladijn.d2rsavegameparser.model.SkillTree.CHAOS;
import static io.github.paladijn.d2rsavegameparser.model.SkillTree.COLD;
import static io.github.paladijn.d2rsavegameparser.model.SkillTree.COMBAT;
import static io.github.paladijn.d2rsavegameparser.model.SkillTree.COMBAT_BARB;
import static io.github.paladijn.d2rsavegameparser.model.SkillTree.CURSES;
import static io.github.paladijn.d2rsavegameparser.model.SkillTree.DEFENSIVE;
import static io.github.paladijn.d2rsavegameparser.model.SkillTree.DEMON;
import static io.github.paladijn.d2rsavegameparser.model.SkillTree.ELDRITCH;
import static io.github.paladijn.d2rsavegameparser.model.SkillTree.ELEMENTAL;
import static io.github.paladijn.d2rsavegameparser.model.SkillTree.FIRE;
import static io.github.paladijn.d2rsavegameparser.model.SkillTree.JAVELIN;
import static io.github.paladijn.d2rsavegameparser.model.SkillTree.MARTIAL;
import static io.github.paladijn.d2rsavegameparser.model.SkillTree.MASTERIES;
import static io.github.paladijn.d2rsavegameparser.model.SkillTree.OFFENSIVE;
import static io.github.paladijn.d2rsavegameparser.model.SkillTree.PASSIVE;
import static io.github.paladijn.d2rsavegameparser.model.SkillTree.POISON_BONE;
import static io.github.paladijn.d2rsavegameparser.model.SkillTree.SHADOW;
import static io.github.paladijn.d2rsavegameparser.model.SkillTree.SHAPESHIFT;
import static io.github.paladijn.d2rsavegameparser.model.SkillTree.SUMMON;
import static io.github.paladijn.d2rsavegameparser.model.SkillTree.SUMMON_DRUID;
import static io.github.paladijn.d2rsavegameparser.model.SkillTree.TRAPS;
import static io.github.paladijn.d2rsavegameparser.model.SkillTree.UNKNOWN;

/**
 * Representation of a skill containing the id used in the save game as well as the {@link CharacterType} and {@link SkillTree} it belongs to.
 * <p>
 * Note: these were auto-generated from the skills.txt file, so if you see any strange names like wearwolf: they match the ones Blizzard supplied.
 */
public enum SkillType {
    ATTACK(0, 0, CharacterType.NONE, UNKNOWN),
    KICK(1, 0, CharacterType.NONE, UNKNOWN),
    THROW(2, 0, CharacterType.NONE, UNKNOWN),
    UNSUMMON(3, 0, CharacterType.NONE, UNKNOWN),
    LEFT_HAND_THROW(4, 0, CharacterType.NONE, UNKNOWN),
    LEFT_HAND_SWING(5, 0, CharacterType.NONE, UNKNOWN),
    // --- Amazon ---
    MAGIC_ARROW(6, 0, CharacterType.AMAZON, BOW),
    FIRE_ARROW(7, 1, CharacterType.AMAZON, BOW),
    INNER_SIGHT(8, 2, CharacterType.AMAZON, PASSIVE),
    CRITICAL_STRIKE(9, 3, CharacterType.AMAZON, PASSIVE),
    JAB(10, 4, CharacterType.AMAZON, JAVELIN),
    COLD_ARROW(11, 5, CharacterType.AMAZON, BOW),
    MULTIPLE_SHOT(12, 6, CharacterType.AMAZON, BOW),
    DODGE(13, 7, CharacterType.AMAZON, PASSIVE),
    POWER_STRIKE(14, 8, CharacterType.AMAZON, JAVELIN),
    POISON_JAVELIN(15, 9, CharacterType.AMAZON, JAVELIN),
    EXPLODING_ARROW(16, 10, CharacterType.AMAZON, BOW),
    SLOW_MISSILES(17, 11, CharacterType.AMAZON, PASSIVE),
    AVOID(18, 12, CharacterType.AMAZON, PASSIVE),
    IMPALE(19, 13, CharacterType.AMAZON, JAVELIN),
    LIGHTNING_BOLT(20, 14, CharacterType.AMAZON, JAVELIN),
    ICE_ARROW(21, 15, CharacterType.AMAZON, BOW),
    GUIDED_ARROW(22, 16, CharacterType.AMAZON, BOW),
    PENETRATE(23,17, CharacterType.AMAZON, PASSIVE),
    CHARGED_STRIKE(24, 18, CharacterType.AMAZON, JAVELIN),
    PLAGUE_JAVELIN(25, 19, CharacterType.AMAZON, JAVELIN),
    STRAFE(26, 20, CharacterType.AMAZON, BOW),
    IMMOLATION_ARROW(27, 21, CharacterType.AMAZON, BOW),
    DOPPLEZON(28, 22, CharacterType.AMAZON, PASSIVE),
    EVADE(29, 23, CharacterType.AMAZON, PASSIVE),
    FEND(30, 24, CharacterType.AMAZON, JAVELIN),
    FREEZING_ARROW(31, 25, CharacterType.AMAZON, BOW),
    VALKYRIE(32, 26, CharacterType.AMAZON, PASSIVE),
    PIERCE(33, 27, CharacterType.AMAZON, PASSIVE),
    LIGHTNING_STRIKE(34, 28, CharacterType.AMAZON, JAVELIN),
    LIGHTNING_FURY(35, 29, CharacterType.AMAZON, JAVELIN),
    // --- Sorceress ---
    FIRE_BOLT(36, 0, CharacterType.SORCERESS, FIRE),
    WARMTH(37, 1, CharacterType.SORCERESS, FIRE),
    CHARGED_BOLT(38, 2, CharacterType.SORCERESS, SkillTree.LIGHTNING),
    ICE_BOLT(39, 3, CharacterType.SORCERESS, COLD),
    FROZEN_ARMOR(40, 4, CharacterType.SORCERESS, COLD),
    INFERNO(41, 5, CharacterType.SORCERESS, FIRE),
    STATIC_FIELD(42, 6, CharacterType.SORCERESS, SkillTree.LIGHTNING),
    TELEKINESIS(43, 7, CharacterType.SORCERESS, SkillTree.LIGHTNING),
    FROST_NOVA(44, 8, CharacterType.SORCERESS, COLD),
    ICE_BLAST(45, 9, CharacterType.SORCERESS, COLD),
    BLAZE(46, 10, CharacterType.SORCERESS, FIRE),
    FIRE_BALL(47, 11, CharacterType.SORCERESS, FIRE),
    NOVA(48, 12, CharacterType.SORCERESS, SkillTree.LIGHTNING),
    LIGHTNING(49, 13, CharacterType.SORCERESS, SkillTree.LIGHTNING),
    SHIVER_ARMOR(50, 14, CharacterType.SORCERESS, COLD),
    FIRE_WALL(51, 15, CharacterType.SORCERESS, FIRE),
    ENCHANT(52, 16, CharacterType.SORCERESS, FIRE),
    CHAIN_LIGHTNING(53, 17, CharacterType.SORCERESS, SkillTree.LIGHTNING),
    TELEPORT(54, 18, CharacterType.SORCERESS, SkillTree.LIGHTNING),
    GLACIAL_SPIKE(55, 19, CharacterType.SORCERESS, COLD),
    METEOR(56, 20, CharacterType.SORCERESS, FIRE),
    THUNDER_STORM(57, 21, CharacterType.SORCERESS, SkillTree.LIGHTNING),
    ENERGY_SHIELD(58, 22, CharacterType.SORCERESS, SkillTree.LIGHTNING),
    BLIZZARD(59, 23, CharacterType.SORCERESS, COLD),
    CHILLING_ARMOR(60, 24, CharacterType.SORCERESS, COLD),
    FIRE_MASTERY(61, 25, CharacterType.SORCERESS, FIRE),
    HYDRA(62, 26, CharacterType.SORCERESS, FIRE),
    LIGHTNING_MASTERY(63, 27, CharacterType.SORCERESS, SkillTree.LIGHTNING),
    FROZEN_ORB(64, 28, CharacterType.SORCERESS, COLD),
    COLD_MASTERY(65, 29, CharacterType.SORCERESS, COLD),
    // --- Necromancer ---
    AMPLIFY_DAMAGE(66, 0, CharacterType.NECROMANCER, CURSES),
    TEETH(67, 1, CharacterType.NECROMANCER, POISON_BONE),
    BONE_ARMOR(68, 2, CharacterType.NECROMANCER, POISON_BONE),
    SKELETON_MASTERY(69, 3, CharacterType.NECROMANCER, SUMMON),
    RAISE_SKELETON(70, 4, CharacterType.NECROMANCER, SUMMON),
    DIM_VISION(71, 5, CharacterType.NECROMANCER, CURSES),
    WEAKEN(72, 6, CharacterType.NECROMANCER, CURSES),
    POISON_DAGGER(73, 7, CharacterType.NECROMANCER, POISON_BONE),
    CORPSE_EXPLOSION(74, 8, CharacterType.NECROMANCER, POISON_BONE),
    CLAY_GOLEM(75, 9, CharacterType.NECROMANCER, SUMMON),
    IRON_MAIDEN(76, 10, CharacterType.NECROMANCER, CURSES),
    TERROR(77, 11, CharacterType.NECROMANCER, CURSES),
    BONE_WALL(78, 12, CharacterType.NECROMANCER, POISON_BONE),
    GOLEM_MASTERY(79, 13, CharacterType.NECROMANCER, SUMMON),
    RAISE_SKELETAL_MAGE(80, 14, CharacterType.NECROMANCER, SUMMON),
    CONFUSE(81, 15, CharacterType.NECROMANCER, CURSES),
    LIFE_TAP(82, 16, CharacterType.NECROMANCER, CURSES),
    POISON_EXPLOSION(83, 17, CharacterType.NECROMANCER, POISON_BONE),
    BONE_SPEAR(84, 18, CharacterType.NECROMANCER, POISON_BONE),
    BLOODGOLEM(85, 19, CharacterType.NECROMANCER, SUMMON),
    ATTRACT(86, 20, CharacterType.NECROMANCER, CURSES),
    DECREPIFY(87, 21, CharacterType.NECROMANCER, CURSES),
    BONE_PRISON(88, 22, CharacterType.NECROMANCER, POISON_BONE),
    SUMMON_RESIST(89, 23, CharacterType.NECROMANCER, SUMMON),
    IRONGOLEM(90, 24, CharacterType.NECROMANCER, SUMMON),
    LOWER_RESIST(91, 25, CharacterType.NECROMANCER, CURSES),
    POISON_NOVA(92, 26, CharacterType.NECROMANCER, POISON_BONE),
    BONE_SPIRIT(93, 27, CharacterType.NECROMANCER, POISON_BONE),
    FIREGOLEM(94, 28, CharacterType.NECROMANCER, SUMMON),
    REVIVE(95, 29, CharacterType.NECROMANCER, SUMMON),
    // --- Paladin ---
    SACRIFICE(96, 0, CharacterType.PALADIN, COMBAT),
    SMITE(97, 1, CharacterType.PALADIN, COMBAT),
    MIGHT(98, 2, CharacterType.PALADIN, OFFENSIVE),
    PRAYER(99, 3, CharacterType.PALADIN, DEFENSIVE),
    RESIST_FIRE(100, 4, CharacterType.PALADIN, DEFENSIVE),
    HOLY_BOLT(101, 5, CharacterType.PALADIN, COMBAT),
    HOLY_FIRE(102, 6, CharacterType.PALADIN, OFFENSIVE),
    THORNS(103, 7, CharacterType.PALADIN, OFFENSIVE),
    DEFIANCE(104, 8, CharacterType.PALADIN, DEFENSIVE),
    RESIST_COLD(105, 9, CharacterType.PALADIN, DEFENSIVE),
    ZEAL(106, 10, CharacterType.PALADIN, COMBAT),
    CHARGE(107, 11, CharacterType.PALADIN, COMBAT),
    BLESSED_AIM(108, 12, CharacterType.PALADIN, OFFENSIVE),
    CLEANSING(109, 13, CharacterType.PALADIN, DEFENSIVE),
    RESIST_LIGHTNING(110, 14, CharacterType.PALADIN, DEFENSIVE),
    VENGEANCE(111, 15, CharacterType.PALADIN, COMBAT),
    BLESSED_HAMMER(112, 16, CharacterType.PALADIN, COMBAT),
    CONCENTRATION(113, 17, CharacterType.PALADIN, OFFENSIVE),
    HOLY_FREEZE(114, 18, CharacterType.PALADIN, OFFENSIVE),
    VIGOR(115, 19, CharacterType.PALADIN, DEFENSIVE),
    CONVERSION(116, 20, CharacterType.PALADIN, COMBAT),
    HOLY_SHIELD(117, 21, CharacterType.PALADIN, COMBAT),
    HOLY_SHOCK(118, 22, CharacterType.PALADIN, OFFENSIVE),
    SANCTUARY(119, 23, CharacterType.PALADIN, OFFENSIVE),
    MEDITATION(120, 24, CharacterType.PALADIN, DEFENSIVE),
    FIST_OF_THE_HEAVENS(121, 25, CharacterType.PALADIN, COMBAT),
    FANATICISM(122, 26, CharacterType.PALADIN, OFFENSIVE),
    CONVICTION(123, 27, CharacterType.PALADIN, OFFENSIVE),
    REDEMPTION(124, 28, CharacterType.PALADIN, DEFENSIVE),
    SALVATION(125, 29, CharacterType.PALADIN, DEFENSIVE),
    // --- Barbie ---
    BASH(126, 0, CharacterType.BARBARIAN, COMBAT_BARB),
    BLADE_MASTERY(127, 1, CharacterType.BARBARIAN, MASTERIES),
    AXE_MASTERY(128, 2, CharacterType.BARBARIAN, MASTERIES),
    MACE_MASTERY(129, 3, CharacterType.BARBARIAN, MASTERIES),
    HOWL(130, 4, CharacterType.BARBARIAN, SkillTree.SHOUT),
    FIND_POTION(131, 5, CharacterType.BARBARIAN, SkillTree.SHOUT),
    LEAP(132, 6, CharacterType.BARBARIAN, COMBAT_BARB),
    DOUBLE_SWING(133, 7, CharacterType.BARBARIAN, COMBAT_BARB),
    POLE_ARM_MASTERY(134, 8, CharacterType.BARBARIAN, MASTERIES),
    THROWING_MASTERY(135, 9, CharacterType.BARBARIAN, MASTERIES),
    SPEAR_MASTERY(136, 10, CharacterType.BARBARIAN, MASTERIES),
    TAUNT(137, 11, CharacterType.BARBARIAN, SkillTree.SHOUT),
    SHOUT(138, 12, CharacterType.BARBARIAN, SkillTree.SHOUT),
    STUN(139, 13, CharacterType.BARBARIAN, COMBAT_BARB),
    DOUBLE_THROW(140, 14, CharacterType.BARBARIAN, COMBAT_BARB),
    INCREASED_STAMINA(141, 15, CharacterType.BARBARIAN, MASTERIES),
    FIND_ITEM(142, 16, CharacterType.BARBARIAN, SkillTree.SHOUT),
    LEAP_ATTACK(143, 17, CharacterType.BARBARIAN, COMBAT_BARB),
    CONCENTRATE(144, 18, CharacterType.BARBARIAN, COMBAT_BARB),
    IRON_SKIN(145, 19, CharacterType.BARBARIAN, MASTERIES),
    BATTLE_CRY(146, 20, CharacterType.BARBARIAN, SkillTree.SHOUT),
    FRENZY(147, 21, CharacterType.BARBARIAN, COMBAT_BARB),
    INCREASED_SPEED(148, 22, CharacterType.BARBARIAN, MASTERIES),
    BATTLE_ORDERS(149, 23, CharacterType.BARBARIAN, MASTERIES),
    GRIM_WARD(150, 24, CharacterType.BARBARIAN, SkillTree.SHOUT),
    WHIRLWIND(151, 25, CharacterType.BARBARIAN, COMBAT_BARB),
    BERSERK(152, 26, CharacterType.BARBARIAN, COMBAT_BARB),
    NATURAL_RESISTANCE(153, 27, CharacterType.BARBARIAN, MASTERIES),
    WAR_CRY(154, 28, CharacterType.BARBARIAN, SkillTree.SHOUT),
    BATTLE_COMMAND(155, 29, CharacterType.BARBARIAN, SkillTree.SHOUT),
    // --- Druid ---
    RAVEN(221, 0, CharacterType.DRUID, SUMMON_DRUID),
    PLAGUE_POPPY(222, 1, CharacterType.DRUID, SUMMON_DRUID),
    WEARWOLF(223, 2, CharacterType.DRUID, SHAPESHIFT),
    SHAPE_SHIFTING(224, 3, CharacterType.DRUID, SHAPESHIFT),
    FIRESTORM(225, 4, CharacterType.DRUID, ELEMENTAL),
    OAK_SAGE(226, 5, CharacterType.DRUID, SUMMON_DRUID),
    SUMMON_SPIRIT_WOLF(227, 6, CharacterType.DRUID, SUMMON_DRUID),
    WEARBEAR(228, 7, CharacterType.DRUID, SHAPESHIFT),
    MOLTEN_BOULDER(229, 8, CharacterType.DRUID, ELEMENTAL),
    ARCTIC_BLAST(230, 9, CharacterType.DRUID, ELEMENTAL),
    CYCLE_OF_LIFE(231, 10, CharacterType.DRUID, SUMMON_DRUID),
    FERAL_RAGE(232, 11, CharacterType.DRUID, SHAPESHIFT),
    MAUL(233, 12, CharacterType.DRUID, SHAPESHIFT),
    ERUPTION(234, 13, CharacterType.DRUID, ELEMENTAL),
    CYCLONE_ARMOR(235, 14, CharacterType.DRUID, ELEMENTAL),
    HEART_OF_WOLVERINE(236, 15, CharacterType.DRUID, SUMMON_DRUID),
    SUMMON_FENRIS(237, 16, CharacterType.DRUID, SUMMON_DRUID),
    RABIES(238, 17, CharacterType.DRUID, SHAPESHIFT),
    FIRE_CLAWS(239, 18, CharacterType.DRUID, SHAPESHIFT),
    TWISTER(240, 19, CharacterType.DRUID, ELEMENTAL),
    VINES(241, 20, CharacterType.DRUID, SUMMON_DRUID),
    HUNGER(242, 21, CharacterType.DRUID, SHAPESHIFT),
    SHOCK_WAVE(243, 22, CharacterType.DRUID, SHAPESHIFT),
    VOLCANO(244, 23, CharacterType.DRUID, ELEMENTAL),
    TORNADO(245, 24, CharacterType.DRUID, ELEMENTAL),
    SPIRIT_OF_BARBS(246, 25, CharacterType.DRUID, SUMMON_DRUID),
    SUMMON_GRIZZLY(247, 26, CharacterType.DRUID, SUMMON_DRUID),
    FURY(248, 27, CharacterType.DRUID, SHAPESHIFT),
    ARMAGEDDON(249, 28, CharacterType.DRUID, ELEMENTAL),
    HURRICANE(250, 29, CharacterType.DRUID, ELEMENTAL),
    // --- assassin ---
    FIRE_TRAUMA(251, 0, CharacterType.ASSASSIN, TRAPS),
    CLAW_MASTERY(252, 1, CharacterType.ASSASSIN, SHADOW),
    PSYCHIC_HAMMER(253, 2, CharacterType.ASSASSIN, SHADOW),
    TIGER_STRIKE(254, 3, CharacterType.ASSASSIN, MARTIAL),
    DRAGON_TALON(255, 4, CharacterType.ASSASSIN, MARTIAL),
    SHOCK_FIELD(256, 5, CharacterType.ASSASSIN, TRAPS),
    BLADE_SENTINEL(257, 6, CharacterType.ASSASSIN, TRAPS),
    QUICKNESS(258, 7, CharacterType.ASSASSIN, SHADOW),
    FISTS_OF_FIRE(259, 8, CharacterType.ASSASSIN, MARTIAL),
    DRAGON_CLAW(260, 9, CharacterType.ASSASSIN, MARTIAL),
    CHARGED_BOLT_SENTRY(261, 10, CharacterType.ASSASSIN, TRAPS),
    WAKE_OF_FIRE_SENTRY(262, 11, CharacterType.ASSASSIN, TRAPS),
    WEAPON_BLOCK(263, 12, CharacterType.ASSASSIN, SHADOW),
    CLOAK_OF_SHADOWS(264, 13, CharacterType.ASSASSIN, SHADOW),
    COBRA_STRIKE(265, 14, CharacterType.ASSASSIN, MARTIAL),
    BLADE_FURY(266, 15, CharacterType.ASSASSIN, TRAPS),
    FADE(267, 16, CharacterType.ASSASSIN, SHADOW),
    SHADOW_WARRIOR(268, 17, CharacterType.ASSASSIN, SHADOW),
    CLAWS_OF_THUNDER(269, 18, CharacterType.ASSASSIN, MARTIAL),
    DRAGON_TAIL(270, 19, CharacterType.ASSASSIN, MARTIAL),
    LIGHTNING_SENTRY(271, 20, CharacterType.ASSASSIN, TRAPS),
    INFERNO_SENTRY(272, 21, CharacterType.ASSASSIN, TRAPS),
    MIND_BLAST(273, 22, CharacterType.ASSASSIN, SHADOW),
    BLADES_OF_ICE(274, 23, CharacterType.ASSASSIN, MARTIAL),
    DRAGON_FLIGHT(275, 24, CharacterType.ASSASSIN, MARTIAL),
    DEATH_SENTRY(276, 25, CharacterType.ASSASSIN, TRAPS),
    BLADE_SHIELD(277, 26, CharacterType.ASSASSIN, TRAPS),
    VENOM(278, 27, CharacterType.ASSASSIN, SHADOW),
    SHADOW_MASTER(279, 28, CharacterType.ASSASSIN, SHADOW),
    ROYAL_STRIKE(280, 29, CharacterType.ASSASSIN, MARTIAL),
    // --- Warlock ---
    // TODO check the ID values
    SUMMON_GOATMAN(281, 0, CharacterType.WARLOCK, DEMON),
    DEMONIC_MASTERY(282, 1, CharacterType.WARLOCK, DEMON),
    DEATH_MARK(283, 2, CharacterType.WARLOCK, DEMON),
    SUMMON_TAINTED(284, 3, CharacterType.WARLOCK, DEMON),
    SUMMON_DEFILER(285, 4, CharacterType.WARLOCK, DEMON),
    BLOOD_OATH(286, 5, CharacterType.WARLOCK, DEMON),
    ENGORGE(287, 6,  CharacterType.WARLOCK, DEMON),
    BLOOD_BOIL(288, 7, CharacterType.WARLOCK, DEMON),
    CONSUME(289, 8,  CharacterType.WARLOCK, DEMON),
    BIND_DEMON(290, 9,  CharacterType.WARLOCK, DEMON),

    LEVITATION_MASTERY(291, 10, CharacterType.WARLOCK, ELDRITCH),
    CLEAVE(292, 11, CharacterType.WARLOCK, ELDRITCH),
    HEX_BANE(293, 12, CharacterType.WARLOCK, ELDRITCH),
    HEX_SIPHON(294, 13,  CharacterType.WARLOCK, ELDRITCH),
    PSYCHIC_WARD(295, 14, CharacterType.WARLOCK, ELDRITCH),
    ECHOING_STRIKE(296, 15, CharacterType.WARLOCK, ELDRITCH),
    HEX_PURGE(297, 16, CharacterType.WARLOCK, ELDRITCH),
    BLADE_WARP(298, 17, CharacterType.WARLOCK, ELDRITCH),
    ELDRITCH_BLAST(299, 18,  CharacterType.WARLOCK, ELDRITCH),
    MIRRORED_BLADES(300, 19,  CharacterType.WARLOCK, ELDRITCH),

    SIGIL_LETHARGY(301, 20, CharacterType.WARLOCK, CHAOS),
    RING_OF_FIRE(302, 21, CharacterType.WARLOCK, CHAOS),
    MIASMA_BOLT(303, 22, CharacterType.WARLOCK, CHAOS),
    SIGIL_RANCOR(304, 23, CharacterType.WARLOCK, CHAOS),
    ENHANCED_ENTROPHY(305, 24,  CharacterType.WARLOCK, CHAOS),
    FLAME_WAVE(306, 25, CharacterType.WARLOCK, CHAOS),
    MIASMA_CHAIN(307, 26, CharacterType.WARLOCK, CHAOS),
    SIGIL_DEATH(308, 27,  CharacterType.WARLOCK, CHAOS),
    APOCALYPSE(309, 28,  CharacterType.WARLOCK, CHAOS),
    ABYSS(310, 29,  CharacterType.WARLOCK, CHAOS);

    private final int id;

    private final int offset;

    private final CharacterType characterType;

    private final SkillTree skillTree;

    SkillType(int id, int offset, CharacterType characterType, SkillTree skillTree) {
        this.id = id;
        this.offset = offset;
        this.characterType = characterType;
        this.skillTree = skillTree;
    }

    /**
     * Get the {@link SkillTree} by index id
     * @param id The id of the {@link SkillTree} you're looking for
     * @return The {@link SkillTree} element if available, or {@link SkillTree#UNKNOWN} if not found
     */
    public static SkillType findSkillById(int id) {
        for(SkillType skillType: SkillType.values()) {
            if (skillType.id == id) {
                return skillType;
            }
        }
        return UNSUMMON;
    }

    public int getId() {
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
