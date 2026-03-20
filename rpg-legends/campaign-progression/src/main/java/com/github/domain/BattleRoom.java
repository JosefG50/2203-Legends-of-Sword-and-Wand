package com.github.domain;

public class BattleRoom implements Room{
    private List<Enemy> enemies;
    private List<Entity> turnOrder;

    
    public int getExp(){
        int exp = 0;
        for (Enemy enemy: enemies){
            exp += enemy.getExp();
        }
        return exp;
    }
    public int getGold(){
        int gold = 0;
        for (Enemy enemy: enemies){
            gold += enemy.getGold();
        }
        return gold;
    }
    public List getTurnOrder(){
        
    }
    
}

- enemies: List<enemy>
    - turnOrder: List<entity>

    + getExp(): int
    + getGold(): int
    + getTurnOrder(): List
    + getEnemies(): List

    + setTurnOrder(allies: List, enemies: List)
    + createEnemies(allies: List)
    + isOver(): boolean