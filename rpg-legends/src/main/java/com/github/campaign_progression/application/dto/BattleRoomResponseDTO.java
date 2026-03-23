package com.github.application.dto;

import java.util.List;

public class BattleRoomResponseDTO {

    private List<EnemyDTO> enemies;

    public BattleRoomResponseDTO(List<EnemyDTO> enemies) {
        this.enemies = enemies;
    }

    public List<EnemyDTO> getEnemies() {
        return enemies;
    }

    public static class EnemyDTO {
        private int level;
        private int hp;
        private int attack;
        private int defense;

        public EnemyDTO(int level, int hp, int attack, int defense) {
            this.level = level;
            this.hp = hp;
            this.attack = attack;
            this.defense = defense;
        }

        public int getLevel() { return level; }
        public int getHp() { return hp; }
        public int getAttack() { return attack; }
        public int getDefense() { return defense; }
    }
}