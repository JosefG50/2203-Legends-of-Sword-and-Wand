package com.github.campaign_progression.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnemyFactory {

    private final Random random = new Random();

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

    // Split total level into N enemies
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

    // Scale stats based on level
    private Enemy createScaledEnemy(int level) {

        // You can tweak these formulas later — this is your balance core
        int health = 50 + level * 10 + random.nextInt(10);
        int attack = 5 + level * 2 + random.nextInt(3);
        int defense = 3 + level * 2 + random.nextInt(3);

        return new Enemy(level, health, attack, defense);
    }
}