package com.github.paladijn.d2rsavegameparser.model;


import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SkillTypeTest {

    @Test
    void validateCorrectOffsetsForPaladin() {
        final List<SkillType> paladinSkills = SkillType.getSkillListForCharacter(CharacterType.PALADIN);
        assertThat(paladinSkills.getFirst().getOffset()).isZero();
        assertThat(paladinSkills.getFirst().getId()).isEqualTo((byte)96);

        assertThat(paladinSkills.get(16).getOffset()).isEqualTo(16);
        assertThat(paladinSkills.get(16).getId()).isEqualTo((byte)112);

        assertThat(paladinSkills.getLast().getOffset()).isEqualTo(29);
        assertThat(paladinSkills.getLast().getId()).isEqualTo((byte)125);

        for(int offset = 0; offset < 30; offset++) {
            assertThat(paladinSkills.get(offset).getOffset()).isEqualTo(offset);
        }
    }

    // We'll only check the other classes for their offsets
    @Test
    void validateCorrectOffsetsForAmazon() {
        final List<SkillType> amazonSkills = SkillType.getSkillListForCharacter(CharacterType.AMAZON);

        for(int offset = 0; offset < 30; offset++) {
            assertThat(amazonSkills.get(offset).getOffset()).isEqualTo(offset);
        }
    }

    @Test
    void validateCorrectOffsetsForSorceress() {
        final List<SkillType> sorceressSkills = SkillType.getSkillListForCharacter(CharacterType.SORCERESS);

        for(int offset = 0; offset < 30; offset++) {
            assertThat(sorceressSkills.get(offset).getOffset()).isEqualTo(offset);
        }
    }

    @Test
    void validateCorrectOffsetsForNecromancer() {
        final List<SkillType> necromancerSkills = SkillType.getSkillListForCharacter(CharacterType.NECROMANCER);

        for(int offset = 0; offset < 30; offset++) {
            assertThat(necromancerSkills.get(offset).getOffset()).isEqualTo(offset);
        }
    }

    @Test
    void validateCorrectOffsetsForBaba() {
        final List<SkillType> barbarianSkills = SkillType.getSkillListForCharacter(CharacterType.BARBARIAN);

        for(int offset = 0; offset < 30; offset++) {
            assertThat(barbarianSkills.get(offset).getOffset()).isEqualTo(offset);
        }
    }

    @Test
    void validateCorrectOffsetsForDruid() {
        final List<SkillType> druidSkills = SkillType.getSkillListForCharacter(CharacterType.DRUID);

        for(int offset = 0; offset < 30; offset++) {
            assertThat(druidSkills.get(offset).getOffset()).isEqualTo(offset);
        }
    }

    @Test
    void validateCorrectOffsetsForAssassin() {
        final List<SkillType> assassinSkills = SkillType.getSkillListForCharacter(CharacterType.ASSASSIN);

        for(int offset = 0; offset < 30; offset++) {
            assertThat(assassinSkills.get(offset).getOffset()).isEqualTo(offset);
        }
    }
}