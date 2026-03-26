package com.github.campaign_progression.domain;

/**
 * Represents an enemy encountered in a {@link BattleRoom}.
 *
 * <p>All fields are immutable and set at construction time. Every stat must be
 * a positive integer — the constructor enforces this with eager validation.</p>
 *
 * <p>Note: {@link #getHp()} is an alias for {@link #getHealth()} and both
 * return the same value. Consider consolidating to one method in a future refactor.</p>
 */
public class Enemy {

    /** The level of this enemy. Determines experience and gold rewards. Must be &gt; 0. */
    private final int level;

    /** The total hit points of this enemy. Must be &gt; 0. */
    private final int health;

    /** The attack stat of this enemy. Must be &gt; 0. */
    private final int attack;

    /** The defense stat of this enemy. Must be &gt; 0. */
    private final int defense;

    /**
     * Constructs a new {@code Enemy} with the given combat stats.
     * All parameters must be strictly positive.
     *
     * @param level   the enemy's level; must be &gt; 0
     * @param health  the enemy's hit points; must be &gt; 0
     * @param attack  the enemy's attack power; must be &gt; 0
     * @param defense the enemy's defense rating; must be &gt; 0
     * @throws IllegalArgumentException if any parameter is zero or negative
     */
    public Enemy(int level, int health, int attack, int defense) {
        if (level   <= 0) throw new IllegalArgumentException("Level must be > 0");
        if (health  <= 0) throw new IllegalArgumentException("Health must be > 0");
        if (attack  <= 0) throw new IllegalArgumentException("Attack must be > 0");
        if (defense <= 0) throw new IllegalArgumentException("Defense must be > 0");
        this.level   = level;
        this.health  = health;
        this.attack  = attack;
        this.defense = defense;
    }

    /**
     * Returns the level of this enemy.
     * Level is used to calculate experience and gold rewards on defeat.
     *
     * @return the enemy's level (always &gt; 0)
     */
    public int getLevel() { return level; }

    /**
     * Returns the total hit points of this enemy.
     *
     * @return the enemy's health (always &gt; 0)
     */
    public int getHealth() { return health; }

    /**
     * Returns the attack stat of this enemy.
     *
     * @return the enemy's attack power (always &gt; 0)
     */
    public int getAttack() { return attack; }

    /**
     * Returns the defense stat of this enemy.
     *
     * @return the enemy's defense rating (always &gt; 0)
     */
    public int getDefense() { return defense; }

    /**
     * Returns the hit points of this enemy. Alias for {@link #getHealth()}.
     *
     * @return the enemy's health (always &gt; 0)
     * @see #getHealth()
     */
    public int getHp() {
        return health;
    }
}