package com.github.campaign_progression.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link HeroState}.
 * Covers EXP/level logic, all HP/mana mutation methods, and getter/setter pairs.
 */
class HeroStateTest {

    private HeroState hero;

    @BeforeEach
    void setUp() {
        hero = new HeroState();
        hero.setName("Aria");
        hero.setLevel(1);
        hero.setCurHp(100);
        hero.setMaxHp(100);
        hero.setCurMana(50);
        hero.setMaxMana(50);
        hero.setAttack(10);
        hero.setDefense(5);
        hero.setCurExp(0);
        hero.setLvlUpExp(100);
        hero.setSpecialization("MAGE");
    }

    // -------------------------------------------------------------------------
    // gainExp() — no level-up
    // -------------------------------------------------------------------------

    @Test
    void gainExp_belowThreshold_increasesExpWithoutLevelUp() {
        int levels = hero.gainExp(50);
        assertEquals(0, levels);
        assertEquals(50, hero.getCurExp());
        assertEquals(1, hero.getLevel());
    }

    @Test
    void gainExp_zeroExp_noChange() {
        int levels = hero.gainExp(0);
        assertEquals(0, levels);
        assertEquals(0, hero.getCurExp());
    }

    @Test
    void gainExp_negativeExp_decreasesCurrentExp() {
        hero.setCurExp(30);
        hero.gainExp(-10);
        assertEquals(20, hero.getCurExp());
    }

    // -------------------------------------------------------------------------
    // gainExp() — exactly at threshold
    // -------------------------------------------------------------------------

    @Test
    void gainExp_exactlyAtThreshold_gainsOneLevel() {
        int levels = hero.gainExp(100);
        assertEquals(1, levels);
        assertEquals(2, hero.getLevel());
        assertEquals(0, hero.getCurExp());
    }

    @Test
    void gainExp_exactlyAtThreshold_scaledLvlUpExp() {
        hero.gainExp(100);
        // 100 * 1.2 = 120
        assertEquals(120, hero.getLvlUpExp());
    }

    // -------------------------------------------------------------------------
    // gainExp() — over threshold (carry-over)
    // -------------------------------------------------------------------------

    @Test
    void gainExp_overThreshold_carryOverExpCorrect() {
        int levels = hero.gainExp(150); // 150 - 100 = 50 carry-over
        assertEquals(1, levels);
        assertEquals(50, hero.getCurExp());
    }

    @Test
    void gainExp_multipleLeveUps_returnsCorrectLevelCount() {
        // Level 1→2 at 100 EXP, level 2→3 at 120 EXP (scaled)
        // 100 + 120 = 220 EXP needed for two level-ups
        int levels = hero.gainExp(220);
        assertEquals(2, levels);
        assertEquals(3, hero.getLevel());
    }

    @Test
    void gainExp_multipleLeveUps_expCarriesOverCorrectly() {
        hero.gainExp(230); // 100 + 120 = 220 for two levels, 10 carry-over
        assertEquals(10, hero.getCurExp());
    }

    // -------------------------------------------------------------------------
    // gainExp() — specialization sub-level increments
    // -------------------------------------------------------------------------

    @ParameterizedTest
    @ValueSource(strings = {"MAGE", "WARRIOR", "ORDER", "CHAOS"})
    void gainExp_levelUp_incrementsCorrectSubLevel(String spec) {
        hero.setSpecialization(spec);
        hero.gainExp(100);

        switch (spec) {
            case "MAGE"    -> assertEquals(1, hero.getMageLvl());
            case "WARRIOR" -> assertEquals(1, hero.getWarriorLvl());
            case "ORDER"   -> assertEquals(1, hero.getOrderLvl());
            case "CHAOS"   -> assertEquals(1, hero.getChaosLvl());
        }
    }

    @Test
    void gainExp_mageSpecialization_doesNotIncrementOtherSubLevels() {
        hero.setSpecialization("MAGE");
        hero.gainExp(100);
        assertEquals(0, hero.getWarriorLvl());
        assertEquals(0, hero.getOrderLvl());
        assertEquals(0, hero.getChaosLvl());
    }

    @Test
    void gainExp_multipleLevelUps_incrementsSubLevelMultipleTimes() {
        hero.setSpecialization("WARRIOR");
        hero.gainExp(220); // two level-ups
        assertEquals(2, hero.getWarriorLvl());
    }

    // -------------------------------------------------------------------------
    // HP mutation methods
    // -------------------------------------------------------------------------

    @Test
    void gainCurHp_increasesHpByAmount() {
        hero.setCurHp(60);
        hero.gainCurHp(20);
        assertEquals(80, hero.getCurHp());
    }

    @Test
    void minusCurHp_decreasesHpByAmount() {
        hero.setCurHp(80);
        hero.minusCurHp(30);
        assertEquals(50, hero.getCurHp());
    }

    @Test
    void minusCurHp_canGoBelowZero() {
        hero.setCurHp(10);
        hero.minusCurHp(50);
        assertEquals(-40, hero.getCurHp());
    }

    @Test
    void setCurHp_setsDirectly() {
        hero.setCurHp(42);
        assertEquals(42, hero.getCurHp());
    }

    // -------------------------------------------------------------------------
    // Mana mutation methods
    // -------------------------------------------------------------------------

    @Test
    void gainCurMana_increasesManaByAmount() {
        hero.setCurMana(20);
        hero.gainCurMana(15);
        assertEquals(35, hero.getCurMana());
    }

    @Test
    void minusCurMana_decreasesManaByAmount() {
        hero.setCurMana(40);
        hero.minusCurMana(10);
        assertEquals(30, hero.getCurMana());
    }

    @Test
    void minusCurMana_canGoBelowZero() {
        hero.setCurMana(5);
        hero.minusCurMana(20);
        assertEquals(-15, hero.getCurMana());
    }

    @Test
    void setCurMana_setsDirectly() {
        hero.setCurMana(99);
        assertEquals(99, hero.getCurMana());
    }

    // -------------------------------------------------------------------------
    // setLevel() — direct level set
    // -------------------------------------------------------------------------

    @Test
    void setLevel_setsLevelDirectly() {
        hero.setLevel(10);
        assertEquals(10, hero.getLevel());
    }

    @Test
    void setLevel_returnsAssignedValue() {
        int result = hero.setLevel(7);
        assertEquals(7, result);
    }

    @Test
    void setLevel_doesNotChangeLvlUpExp() {
        hero.setLvlUpExp(100);
        hero.setLevel(10);
        assertEquals(100, hero.getLvlUpExp());
    }

    // -------------------------------------------------------------------------
    // setCurExp() — direct EXP set (gameplay-unsafe path)
    // -------------------------------------------------------------------------

    @Test
    void setCurExp_setsExpDirectly() {
        hero.setCurExp(999);
        assertEquals(999, hero.getCurExp());
    }

    @Test
    void setCurExp_doesNotTriggerLevelUp() {
        hero.setLvlUpExp(100);
        hero.setCurExp(500); // would trigger multiple level-ups via gainExp
        assertEquals(1, hero.getLevel()); // level unchanged
    }

    // -------------------------------------------------------------------------
    // Sub-level getters/setters
    // -------------------------------------------------------------------------

    @Test
    void setMageLvl_andGetMageLvl() {
        hero.setMageLvl(3);
        assertEquals(3, hero.getMageLvl());
    }

    @Test
    void setWarriorLvl_andGetWarriorLvl() {
        hero.setWarriorLvl(5);
        assertEquals(5, hero.getWarriorLvl());
    }

    @Test
    void setOrderLvl_andGetOrderLvl() {
        hero.setOrderLvl(2);
        assertEquals(2, hero.getOrderLvl());
    }

    @Test
    void setChaosLvl_andGetChaosLvl() {
        hero.setChaosLvl(4);
        assertEquals(4, hero.getChaosLvl());
    }

    // -------------------------------------------------------------------------
    // Remaining getters/setters
    // -------------------------------------------------------------------------

    @Test
    void setName_andGetName() {
        hero.setName("Zara");
        assertEquals("Zara", hero.getName());
    }

    @Test
    void setMaxHp_andGetMaxHp() {
        hero.setMaxHp(200);
        assertEquals(200, hero.getMaxHp());
    }

    @Test
    void setMaxMana_andGetMaxMana() {
        hero.setMaxMana(80);
        assertEquals(80, hero.getMaxMana());
    }

    @Test
    void setAttack_andGetAttack() {
        hero.setAttack(15);
        assertEquals(15, hero.getAttack());
    }

    @Test
    void setDefense_andGetDefense() {
        hero.setDefense(8);
        assertEquals(8, hero.getDefense());
    }

    @Test
    void setSpecialization_andGetSpecialization() {
        hero.setSpecialization("CHAOS");
        assertEquals("CHAOS", hero.getSpecialization());
    }

    @Test
    void setLvlUpExp_andGetLvlUpExp() {
        hero.setLvlUpExp(250);
        assertEquals(250, hero.getLvlUpExp());
    }

    // -------------------------------------------------------------------------
    // Ability list
    // -------------------------------------------------------------------------

    @Test
    void abilityList_initiallyEmpty() {
        HeroState fresh = new HeroState();
        assertNotNull(fresh.getAbility());
        assertTrue(fresh.getAbility().isEmpty());
    }

    @Test
    void setAbility_replacesAbilityList() {
        ArrayList<String> abilities = new ArrayList<>();
        abilities.add("FIREBALL");
        abilities.add("ICE_SPIKE");
        hero.setAbility(abilities);
        assertEquals(2, hero.getAbility().size());
        assertTrue(hero.getAbility().contains("FIREBALL"));
    }

    @Test
    void getAbility_returnsMutableList() {
        hero.getAbility().add("SHIELD_BASH");
        assertEquals(1, hero.getAbility().size());
    }
}