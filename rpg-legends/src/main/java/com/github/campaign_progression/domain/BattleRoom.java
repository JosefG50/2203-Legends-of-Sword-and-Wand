package com.github.campaign_progression.domain;

import java.util.List;

/**
 * Represents a battle room in the campaign, containing one or more enemies
 * that the player must defeat. Provides methods to calculate experience points
 * and gold rewards based on the enemies present.
 *
 * <p>A {@code BattleRoom} must be populated with at least one enemy via
 * {@link #addEnemies(List)} before enemy-dependent methods are called.</p>
 */
public class BattleRoom implements Room {

    /** The list of enemies present in this battle room. */
    private List<Enemy> enemies;

    /**
     * Constructs a new {@code BattleRoom} with no enemies assigned.
     * Enemies must be added via {@link #addEnemies(List)} before
     * the room can be used in combat.
     */
    public BattleRoom() {
        enemies = null;
    }

    /**
     * Sets the enemies for this battle room.
     *
     * @param enemies a non-null, non-empty list of {@link Enemy} objects to populate the room
     * @throws IllegalArgumentException if {@code enemies} is {@code null} or empty
     */
    public void addEnemies(List enemies) {
        if (enemies == null || enemies.isEmpty()) {
            throw new IllegalArgumentException("BattleRoom must have at least one enemy");
        }
        this.enemies = enemies;
    }

    /**
     * Returns the list of enemies in this battle room.
     *
     * @return the list of {@link Enemy} objects, or {@code null} if none have been added
     */
    public List<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * Calculates the total experience points rewarded for defeating all enemies in this room.
     * Each enemy contributes {@code level * 50} experience points.
     *
     * 
     *
     * @return the total experience points from all enemies
     * @throws NullPointerException if enemies have not been set via {@link #addEnemies(List)}
     */
    public int getExp() {
        int exp = 0;
        for (Enemy enemy : enemies) {
            exp += enemy.getLevel() * 50;
        }
        return exp;
    }

    /**
     * Calculates the total gold rewarded for defeating all enemies in this room.
     * Each enemy contributes {@code level * 75} gold.
     *
     * @return the total gold from all enemies
     * @throws NullPointerException if enemies have not been set via {@link #addEnemies(List)}
     */
    public int getGold() {
        int gold = 0;
        for (Enemy enemy : enemies) {
            gold += enemy.getLevel() * 75;
        }
        return gold;
    }
}