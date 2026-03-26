package com.github.campaign_progression.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Factory responsible for generating a randomized group of {@link Enemy} instances
 * scaled to the player's party level.
 *
 * <p>Enemy count, total level budget, and individual stat values are all randomized
 * within defined ranges to ensure varied but balanced encounters. The scaling formulas
 * in {@link #createScaledEnemy(int)} are intended to be tuned during game balancing.</p>
 *
 * <p>Typical usage:</p>
 * <pre>{@code
 * EnemyFactory factory = new EnemyFactory();
 * List<Enemy> enemies = factory.createEnemies(partyTotalLevel);
 * }</pre>
 */
public class EnemyFactory {

    /** Source of randomness used for enemy count, level distribution, and stat variance. */
    private final Random random = new Random();

    /**
     * Generates a randomized list of {@link Enemy} instances scaled to the given party level.
     *
     * <p>The number of enemies is randomly chosen between 1 and 5 (inclusive). The combined
     * level of all enemies falls between 75% and 100% of {@code partyTotalLevel}, distributed
     * across the group via {@link #distributeLevels(int, int)}. Each enemy's stats are then
     * derived from their individual level via {@link #createScaledEnemy(int)}.</p>
     *
     * @param partyTotalLevel the sum of all party members' levels; must be &gt; 0
     * @return a non-empty list of scaled {@link Enemy} objects (1–5 enemies)
     * @throws IllegalArgumentException if {@code partyTotalLevel} is zero or negative
     */
    public List<Enemy> createEnemies(int partyTotalLevel) {
        if (partyTotalLevel <= 0) {
            throw new IllegalArgumentException("Party level must be positive");
        }

        int enemyCount = random.nextInt(5) + 1; // 1–5 enemies

        // Determine total enemy level range
        int minTotal = Math.max(1, (int)(partyTotalLevel * 0.75));
        int maxTotal = partyTotalLevel;
        int enemyTotalLevel = random.nextInt(maxTotal - minTotal + 1) + minTotal;

        // Distribute levels across enemies
        List<Integer> levels = distributeLevels(enemyTotalLevel, enemyCount);

        List<Enemy> enemies = new ArrayList<>();
        for (int lvl : levels) {
            enemies.add(createScaledEnemy(lvl));
        }
        return enemies;
    }

    /**
     * Distributes a total level budget across {@code count} enemies, assigning each
     * a level of at least 1. The last enemy receives the remaining level budget.
     *
     * <p>Each intermediate enemy is assigned a random level between 1 and
     * {@code (remaining budget - remaining slots)}, ensuring all subsequent enemies
     * can still receive at least 1 level.</p>
     *
     * @param totalLevel the total level points to distribute; must be &gt;= {@code count}
     * @param count      the number of enemies to distribute levels across; must be &gt; 0
     * @return a list of {@code count} positive integer levels summing to {@code totalLevel}
     */
    private List<Integer> distributeLevels(int totalLevel, int count) {
        List<Integer> levels = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int remainingSlots = count - i;
            int maxForThis = totalLevel - (remainingSlots - 1);
            int lvl = (i == count - 1)
                    ? totalLevel
                    : random.nextInt(maxForThis) + 1;
            levels.add(lvl);
            totalLevel -= lvl;
        }
        return levels;
    }

    /**
     * Creates a single {@link Enemy} with stats scaled to the given level.
     *
     * <p>Stat formulas (subject to balance tuning):</p>
     * <ul>
     *   <li><b>Health:</b>  {@code 50 + level * 10 + rand(0–9)}</li>
     *   <li><b>Attack:</b>  {@code  5 + level *  2 + rand(0–2)}</li>
     *   <li><b>Defense:</b> {@code  3 + level *  2 + rand(0–2)}</li>
     * </ul>
     *
     * @param level the level of the enemy to create; must be &gt; 0
     * @return a new {@link Enemy} with stats derived from the given level
     */
    private Enemy createScaledEnemy(int level) {
        // You can tweak these formulas later — this is your balance core
        int health  = 50 + level * 10 + random.nextInt(10);
        int attack  =  5 + level *  2 + random.nextInt(3);
        int defense =  3 + level *  2 + random.nextInt(3);
        return new Enemy(level, health, attack, defense);
    }
}