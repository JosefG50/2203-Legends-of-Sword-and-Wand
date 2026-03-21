package com.github.domain;

public class Enemy {

    private final int level;
    private final int health;
    private final int attack;
    private final int defense;

    public Enemy(int level, int health, int attack, int defense) {
        if (level <= 0) throw new IllegalArgumentException("Level must be > 0");
        if (health <= 0) throw new IllegalArgumentException("Health must be > 0");
        if (attack <= 0) throw new IllegalArgumentException("Attack must be > 0");
        if (defense <= 0) throw new IllegalArgumentException("Defense must be > 0");

        this.level = level;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
    }

    public int getLevel() { return level; }
    public int getHealth() { return health; }
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
}