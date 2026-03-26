package com.github.campaign_progression.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link EnemyFactory}.
 *
 * <p>Because {@code EnemyFactory} uses internal randomness, many tests are
 * run repeatedly (via {@link RepeatedTest}) to increase confidence that
 * invariants hold across different random outcomes.</p>
 */
class EnemyFactoryTest {

    private static final int REPEAT = 50;

    private EnemyFactory factory;

    @BeforeEach
    void setUp() {
        factory = new EnemyFactory();
    }

    // -------------------------------------------------------------------------
    // createEnemies() — invalid input
    // -------------------------------------------------------------------------

    @Test
    void createEnemies_zeroPartyLevel_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> factory.createEnemies(0));
    }

    @Test
    void createEnemies_negativePartyLevel_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> factory.createEnemies(-5));
    }

    @Test
    void createEnemies_invalidInput_exceptionMessageMentionsPartyLevel() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> factory.createEnemies(0));
        assertTrue(ex.getMessage().toLowerCase().contains("level"));
    }

    // -------------------------------------------------------------------------
    // createEnemies() — return value guarantees
    // -------------------------------------------------------------------------

    @Test
    void createEnemies_returnsNonNullList() {
        assertNotNull(factory.createEnemies(10));
    }

    @RepeatedTest(REPEAT)
    void createEnemies_listIsNeverEmpty() {
        List<Enemy> enemies = factory.createEnemies(10);
        assertFalse(enemies.isEmpty());
    }

    @RepeatedTest(REPEAT)
    void createEnemies_enemyCountBetween1And5() {
        List<Enemy> enemies = factory.createEnemies(10);
        int count = enemies.size();
        assertTrue(count >= 1 && count <= 5,
                "Expected 1–5 enemies but got: " + count);
    }

    @RepeatedTest(REPEAT)
    void createEnemies_allEnemiesAreNonNull() {
        List<Enemy> enemies = factory.createEnemies(10);
        enemies.forEach(e -> assertNotNull(e, "Enemy in list should not be null"));
    }

    // -------------------------------------------------------------------------
    // createEnemies() — level budget constraints
    // -------------------------------------------------------------------------

    @RepeatedTest(REPEAT)
    void createEnemies_totalEnemyLevelAtMostPartyLevel() {
        int partyLevel = 20;
        List<Enemy> enemies = factory.createEnemies(partyLevel);
        int totalLevel = enemies.stream().mapToInt(Enemy::getLevel).sum();
        assertTrue(totalLevel <= partyLevel,
                "Total enemy level " + totalLevel + " exceeded party level " + partyLevel);
    }

    @RepeatedTest(REPEAT)
    void createEnemies_totalEnemyLevelAtLeast75PercentOfPartyLevel() {
        int partyLevel = 20;
        int minExpected = (int)(partyLevel * 0.75);
        List<Enemy> enemies = factory.createEnemies(partyLevel);
        int totalLevel = enemies.stream().mapToInt(Enemy::getLevel).sum();
        assertTrue(totalLevel >= minExpected,
                "Total enemy level " + totalLevel + " was below 75% floor of " + minExpected);
    }

    @RepeatedTest(REPEAT)
    void createEnemies_partyLevelOne_totalLevelIsOne() {
        // With partyTotalLevel=1: minTotal=1, maxTotal=1 → always 1 total level
        List<Enemy> enemies = factory.createEnemies(1);
        int totalLevel = enemies.stream().mapToInt(Enemy::getLevel).sum();
        assertEquals(1, totalLevel,
                "With party level 1, total enemy level must be exactly 1");
    }

    // -------------------------------------------------------------------------
    // createEnemies() — individual enemy stat sanity
    // -------------------------------------------------------------------------

    @RepeatedTest(REPEAT)
    void createEnemies_allEnemiesHavePositiveLevel() {
        factory.createEnemies(15).forEach(e ->
                assertTrue(e.getLevel() > 0, "Enemy level must be > 0"));
    }

    @RepeatedTest(REPEAT)
    void createEnemies_allEnemiesHavePositiveHealth() {
        factory.createEnemies(15).forEach(e ->
                assertTrue(e.getHealth() > 0, "Enemy health must be > 0"));
    }

    @RepeatedTest(REPEAT)
    void createEnemies_allEnemiesHavePositiveAttack() {
        factory.createEnemies(15).forEach(e ->
                assertTrue(e.getAttack() > 0, "Enemy attack must be > 0"));
    }

    @RepeatedTest(REPEAT)
    void createEnemies_allEnemiesHavePositiveDefense() {
        factory.createEnemies(15).forEach(e ->
                assertTrue(e.getDefense() > 0, "Enemy defense must be > 0"));
    }

    // -------------------------------------------------------------------------
    // createEnemies() — stat scaling sanity (level 1 vs level 10)
    // -------------------------------------------------------------------------

    @Test
    void createEnemies_higherPartyLevel_producesHigherAverageHealth() {
        // Run many trials and compare averages to smooth out randomness
        double avgHealthLow  = averageHealth(factory.createEnemies(1));
        double avgHealthHigh = averageHealth(factory.createEnemies(50));
        assertTrue(avgHealthHigh > avgHealthLow,
                "Higher level enemies should have more health on average");
    }

    // -------------------------------------------------------------------------
    // createEnemies() — large party level
    // -------------------------------------------------------------------------

    @RepeatedTest(REPEAT)
    void createEnemies_largePartyLevel_doesNotThrow() {
        assertDoesNotThrow(() -> factory.createEnemies(1000));
    }

    @RepeatedTest(REPEAT)
    void createEnemies_largePartyLevel_returnsValidEnemies() {
        List<Enemy> enemies = factory.createEnemies(100);
        assertFalse(enemies.isEmpty());
        enemies.forEach(e -> {
            assertTrue(e.getLevel()   > 0);
            assertTrue(e.getHealth()  > 0);
            assertTrue(e.getAttack()  > 0);
            assertTrue(e.getDefense() > 0);
        });
    }

    // -------------------------------------------------------------------------
    // Helper
    // -------------------------------------------------------------------------

    private double averageHealth(List<Enemy> enemies) {
        return enemies.stream().mapToInt(Enemy::getHealth).average().orElse(0);
    }
}