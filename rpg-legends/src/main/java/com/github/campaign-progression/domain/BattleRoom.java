package com.github.domain;

import java.util.List;

public class BattleRoom implements Room {

    private final List<Enemy> enemies;

    public BattleRoom(List<Enemy> enemies) {
        if (enemies == null || enemies.isEmpty()) {
            throw new IllegalArgumentException("BattleRoom must have at least one enemy");
        }
        this.enemies = enemies;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }
}