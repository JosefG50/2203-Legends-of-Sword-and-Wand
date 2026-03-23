package com.github.campaign_progression.domain;

import java.util.List;

public class BattleRoom implements Room {

    private List<Enemy> enemies;

    public BattleRoom (){
        enemies = null;
    }

    public void addEnemies(List enemies){
        if (enemies == null || enemies.isEmpty()) {
            throw new IllegalArgumentException("BattleRoom must have at least one enemy");
        }
        this.enemies = enemies;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    //TODO: add this to application layer
    public int getExp(){
        int exp = 0;
        for (Enemy enemy: enemies){
            exp += enemy.getLevel() * 50;
        }
        return exp;

    }
    public int getGold(){
        int gold = 0;
        for (Enemy enemy: enemies){
            gold += enemy.getLevel() * 75;
        }
        return gold;
    }
}