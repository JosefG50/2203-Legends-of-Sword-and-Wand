package com.github.campaign_progression.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link Enemy}.
 * Covers constructor validation and all getters, including the {@code getHp()} alias.
 */
class EnemyTest {

    // -------------------------------------------------------------------------
    // Constructor — valid input
    // -------------------------------------------------------------------------

    @Test
    void constructor_validArgs_createsEnemy() {
        assertDoesNotThrow(() -> new Enemy(1, 10, 5, 3));
    }

    @Test
    void constructor_setsLevelCorrectly() {
        Enemy enemy = new Enemy(4, 10, 5, 3);
        assertEquals(4, enemy.getLevel());
    }

    @Test
    void constructor_setsHealthCorrectly() {
        Enemy enemy = new Enemy(1, 50, 5, 3);
        assertEquals(50, enemy.getHealth());
    }

    @Test
    void constructor_setsAttackCorrectly() {
        Enemy enemy = new Enemy(1, 10, 12, 3);
        assertEquals(12, enemy.getAttack());
    }

    @Test
    void constructor_setsDefenseCorrectly() {
        Enemy enemy = new Enemy(1, 10, 5, 7);
        assertEquals(7, enemy.getDefense());
    }

    @Test
    void constructor_minimumValidValues_allOnes() {
        Enemy enemy = new Enemy(1, 1, 1, 1);
        assertEquals(1, enemy.getLevel());
        assertEquals(1, enemy.getHealth());
        assertEquals(1, enemy.getAttack());
        assertEquals(1, enemy.getDefense());
    }

    @Test
    void constructor_largeValues_accepted() {
        Enemy enemy = new Enemy(100, 9999, 500, 300);
        assertEquals(100,  enemy.getLevel());
        assertEquals(9999, enemy.getHealth());
        assertEquals(500,  enemy.getAttack());
        assertEquals(300,  enemy.getDefense());
    }

    // -------------------------------------------------------------------------
    // Constructor — invalid level
    // -------------------------------------------------------------------------

    @Test
    void constructor_levelZero_throwsIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Enemy(0, 10, 5, 3));
        assertTrue(ex.getMessage().contains("Level"));
    }

    @Test
    void constructor_levelNegative_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Enemy(-1, 10, 5, 3));
    }

    // -------------------------------------------------------------------------
    // Constructor — invalid health
    // -------------------------------------------------------------------------

    @Test
    void constructor_healthZero_throwsIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Enemy(1, 0, 5, 3));
        assertTrue(ex.getMessage().contains("Health"));
    }

    @Test
    void constructor_healthNegative_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Enemy(1, -5, 5, 3));
    }

    // -------------------------------------------------------------------------
    // Constructor — invalid attack
    // -------------------------------------------------------------------------

    @Test
    void constructor_attackZero_throwsIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Enemy(1, 10, 0, 3));
        assertTrue(ex.getMessage().contains("Attack"));
    }

    @Test
    void constructor_attackNegative_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Enemy(1, 10, -3, 3));
    }

    // -------------------------------------------------------------------------
    // Constructor — invalid defense
    // -------------------------------------------------------------------------

    @Test
    void constructor_defenseZero_throwsIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> new Enemy(1, 10, 5, 0));
        assertTrue(ex.getMessage().contains("Defense"));
    }

    @Test
    void constructor_defenseNegative_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Enemy(1, 10, 5, -2));
    }

    // -------------------------------------------------------------------------
    // Constructor — parameterized boundary checks
    // -------------------------------------------------------------------------

    /**
     * Verifies that any single zero-or-negative stat causes an exception,
     * while the others remain valid.
     * Format: level, health, attack, defense
     */
    @ParameterizedTest(name = "invalid combo: level={0}, health={1}, attack={2}, defense={3}")
    @CsvSource({
        "0,  10,  5,  3",   // zero level
        "-1, 10,  5,  3",   // negative level
        "1,   0,  5,  3",   // zero health
        "1,  -1,  5,  3",   // negative health
        "1,  10,  0,  3",   // zero attack
        "1,  10, -1,  3",   // negative attack
        "1,  10,  5,  0",   // zero defense
        "1,  10,  5, -1"    // negative defense
    })
    void constructor_invalidStat_throwsIllegalArgumentException(
            int level, int health, int attack, int defense) {
        assertThrows(IllegalArgumentException.class,
                () -> new Enemy(level, health, attack, defense));
    }

    // -------------------------------------------------------------------------
    // getHp() — alias for getHealth()
    // -------------------------------------------------------------------------

    @Test
    void getHp_returnsSameValueAsGetHealth() {
        Enemy enemy = new Enemy(2, 40, 8, 4);
        assertEquals(enemy.getHealth(), enemy.getHp());
    }

    @Test
    void getHp_returnsCorrectHealth() {
        Enemy enemy = new Enemy(1, 25, 5, 5);
        assertEquals(25, enemy.getHp());
    }

    // -------------------------------------------------------------------------
    // Immutability
    // -------------------------------------------------------------------------

    @Test
    void fields_areImmutable_noSettersExist() throws NoSuchMethodException {
        Class<Enemy> clazz = Enemy.class;
        // Verify no setters exist for any field
        assertThrows(NoSuchMethodException.class, () -> clazz.getMethod("setLevel",   int.class));
        assertThrows(NoSuchMethodException.class, () -> clazz.getMethod("setHealth",  int.class));
        assertThrows(NoSuchMethodException.class, () -> clazz.getMethod("setAttack",  int.class));
        assertThrows(NoSuchMethodException.class, () -> clazz.getMethod("setDefense", int.class));
    }
}