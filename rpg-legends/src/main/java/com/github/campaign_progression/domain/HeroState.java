package com.github.campaign_progression.domain;

import java.util.ArrayList;

/**
 * Represents the full mutable state of a hero in the campaign party.
 *
 * <p>Tracks all combat stats (HP, mana, attack, defense), progression data
 * (level, EXP, specialization sub-levels), and the hero's learned abilities.
 * This class is intentionally stateful — it is mutated directly during gameplay
 * by the party and combat systems.</p>
 *
 * <p><b>Specializations:</b> {@code "MAGE"}, {@code "WARRIOR"}, {@code "ORDER"}, {@code "CHAOS"}.
 * The active specialization determines which sub-level is incremented on level-up.</p>
 *
 * <p><b>Note:</b> {@link #setCurExp(int)} is marked as unsafe for gameplay use —
 * prefer {@link #gainExp(int)} to properly handle level-up logic.</p>
 */
public class HeroState {

    /** The display name of this hero. */
    private String name;

    /** The hero's current hit points. May be modified during combat. */
    private int curHp;

    /** The hero's maximum hit points. */
    private int maxHp;

    /**
     * The hero's active specialization. Controls which sub-level increments on level-up.
     * Valid values: {@code "MAGE"}, {@code "WARRIOR"}, {@code "ORDER"}, {@code "CHAOS"}.
     */
    private String specialization;

    /** The hero's current mana. May be modified during combat. */
    private int curMana;

    /** The hero's maximum mana. */
    private int maxMana;

    /** The hero's overall level. Incremented by {@link #increaseLevel()}. */
    private int level;

    /** The hero's mage sub-level. Incremented when specialization is {@code "MAGE"}. */
    private int mageLvl;

    /** The hero's warrior sub-level. Incremented when specialization is {@code "WARRIOR"}. */
    private int warriorLvl;

    /** The hero's order sub-level. Incremented when specialization is {@code "ORDER"}. */
    private int orderLvl;

    /** The hero's chaos sub-level. Incremented when specialization is {@code "CHAOS"}. */
    private int chaosLvl;

    /** The hero's current accumulated experience points toward the next level. */
    private int curExp;

    /**
     * The amount of experience required to reach the next level.
     * Scales by 1.2× each time the hero levels up.
     */
    private int lvlUpExp;

    /** The hero's defense stat, used to reduce incoming damage. */
    private int defense;

    /** The hero's attack stat, used to calculate outgoing damage. */
    private int attack;

    /**
     * The list of ability identifiers this hero has learned.
     * Abilities are stored as string keys (e.g. {@code "FIREBALL"}, {@code "SHIELD_BASH"}).
     */
    ArrayList<String> ability = new ArrayList<>();

    // ===================== EXP / LEVEL LOGIC =====================

    /**
     * Awards the hero the given amount of experience points, triggering one or more
     * level-ups if the EXP threshold is reached or exceeded.
     *
     * <p>Each level-up calls {@link #increaseLevel()}, which increments the overall level,
     * the appropriate specialization sub-level, and scales {@link #lvlUpExp} by 1.2×.
     * Excess EXP carries over correctly across multiple level-ups in a single call.</p>
     *
     * @param expGained the amount of experience to award; should be &gt; 0 for meaningful effect
     * @return the number of levels gained as a result of this EXP award (0 or more)
     */
    public int gainExp(int expGained) {
        int levelsGained = 0;
        curExp += expGained;
        while (curExp >= lvlUpExp) {
            curExp -= lvlUpExp;
            levelsGained++;
            increaseLevel();
        }
        return levelsGained;
    }

    /**
     * Increments the hero's overall level and the appropriate specialization sub-level,
     * then scales the next level-up EXP requirement by 1.2×.
     *
     * <p>This method handles only level counter and scaling logic — stat increases
     * (e.g. HP, attack) are intentionally left to the caller or a separate system.</p>
     *
     * <p><b>TODO:</b> Allow the player to choose their specialization increment rather
     * than using the currently assigned specialization automatically.</p>
     */
    private void increaseLevel() {
        level++;
        // TODO: Change to let user decide
        switch (specialization) {
            case "MAGE":    mageLvl++;    break;
            case "WARRIOR": warriorLvl++; break;
            case "ORDER":   orderLvl++;   break;
            case "CHAOS":   chaosLvl++;   break;
        }
        // Scale next level requirement (simple + fair)
        lvlUpExp = (int)(lvlUpExp * 1.2);
    }

    // ===================== GETTERS / SETTERS =====================

    /**
     * Returns the hero's display name.
     *
     * @return the hero's name
     */
    public String getName() { return name; }

    /**
     * Sets the hero's display name.
     *
     * @param name the new name to assign
     */
    public void setName(String name) { this.name = name; }

    /**
     * Returns the hero's current overall level.
     *
     * @return the current level
     */
    public int getLevel() { return level; }

    /**
     * Directly sets the hero's level and returns the new value.
     *
     * <p><b>Note:</b> This bypasses all level-up logic (EXP scaling, sub-level increments).
     * Prefer {@link #gainExp(int)} for normal level progression.</p>
     *
     * @param level the level to assign directly
     * @return the newly assigned level
     */
    public int setLevel(int level) { return this.level = level; }

    /**
     * Returns the hero's current hit points.
     *
     * @return current HP
     */
    public int getCurHp() { return curHp; }

    /**
     * Sets the hero's current hit points directly.
     *
     * @param curHp the new current HP value
     */
    public void setCurHp(int curHp) { this.curHp = curHp; }

    /**
     * Increases the hero's current HP by the given amount.
     *
     * @param amount the amount of HP to restore; positive values increase HP
     */
    public void gainCurHp(int amount) { curHp += amount; }

    /**
     * Decreases the hero's current HP by the given amount.
     *
     * @param amount the amount of HP to subtract; positive values reduce HP
     */
    public void minusCurHp(int amount) { curHp -= amount; }

    /**
     * Returns the hero's maximum hit points.
     *
     * @return max HP
     */
    public int getMaxHp() { return maxHp; }

    /**
     * Sets the hero's maximum hit points.
     *
     * @param maxHp the new maximum HP value
     */
    public void setMaxHp(int maxHp) { this.maxHp = maxHp; }

    /**
     * Returns the hero's current specialization.
     *
     * @return one of {@code "MAGE"}, {@code "WARRIOR"}, {@code "ORDER"}, {@code "CHAOS"}
     */
    public String getSpecialization() { return specialization; }

    /**
     * Sets the hero's active specialization. Determines which sub-level
     * is incremented on level-up.
     *
     * @param specialization one of {@code "MAGE"}, {@code "WARRIOR"}, {@code "ORDER"}, {@code "CHAOS"}
     */
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    /**
     * Returns the hero's current mana.
     *
     * @return current mana
     */
    public int getCurMana() { return curMana; }

    /**
     * Sets the hero's current mana directly.
     *
     * @param curMana the new current mana value
     */
    public void setCurMana(int curMana) { this.curMana = curMana; }

    /**
     * Increases the hero's current mana by the given amount.
     *
     * @param amount the amount of mana to restore
     */
    public void gainCurMana(int amount) { curMana += amount; }

    /**
     * Decreases the hero's current mana by the given amount.
     *
     * @param amount the amount of mana to consume
     */
    public void minusCurMana(int amount) { curMana -= amount; }

    /**
     * Returns the hero's maximum mana.
     *
     * @return max mana
     */
    public int getMaxMana() { return maxMana; }

    /**
     * Sets the hero's maximum mana.
     *
     * @param maxMana the new maximum mana value
     */
    public void setMaxMana(int maxMana) { this.maxMana = maxMana; }

    /**
     * Returns the hero's mage sub-level.
     *
     * @return mage sub-level
     */
    public int getMageLvl() { return mageLvl; }

    /**
     * Sets the hero's mage sub-level directly.
     *
     * @param mageLvl the new mage sub-level
     */
    public void setMageLvl(int mageLvl) { this.mageLvl = mageLvl; }

    /**
     * Returns the hero's warrior sub-level.
     *
     * @return warrior sub-level
     */
    public int getWarriorLvl() { return warriorLvl; }

    /**
     * Sets the hero's warrior sub-level directly.
     *
     * @param warriorLvl the new warrior sub-level
     */
    public void setWarriorLvl(int warriorLvl) { this.warriorLvl = warriorLvl; }

    /**
     * Returns the hero's order sub-level.
     *
     * @return order sub-level
     */
    public int getOrderLvl() { return orderLvl; }

    /**
     * Sets the hero's order sub-level directly.
     *
     * @param orderLvl the new order sub-level
     */
    public void setOrderLvl(int orderLvl) { this.orderLvl = orderLvl; }

    /**
     * Returns the hero's chaos sub-level.
     *
     * @return chaos sub-level
     */
    public int getChaosLvl() { return chaosLvl; }

    /**
     * Sets the hero's chaos sub-level directly.
     *
     * @param chaosLvl the new chaos sub-level
     */
    public void setChaosLvl(int chaosLvl) { this.chaosLvl = chaosLvl; }

    /**
     * Returns the hero's current accumulated experience points.
     *
     * @return current EXP (progress toward next level)
     */
    public int getCurExp() { return curExp; }

    /**
     * Directly sets the hero's current EXP.
     *
     * <p><b>⚠️ Avoid using this in gameplay.</b> This bypasses level-up logic entirely.
     * Use {@link #gainExp(int)} instead to correctly handle levelling and EXP carry-over.</p>
     *
     * @param curExp the raw EXP value to assign
     */
    public void setCurExp(int curExp) { this.curExp = curExp; }

    /**
     * Returns the EXP required to reach the next level.
     *
     * @return the current level-up EXP threshold
     */
    public int getLvlUpExp() { return lvlUpExp; }

    /**
     * Sets the EXP threshold required for the next level-up.
     *
     * @param lvlUpExp the new EXP threshold
     */
    public void setLvlUpExp(int lvlUpExp) { this.lvlUpExp = lvlUpExp; }

    /**
     * Returns the hero's defense stat.
     *
     * @return defense value
     */
    public int getDefense() { return defense; }

    /**
     * Sets the hero's defense stat.
     *
     * @param defense the new defense value
     */
    public void setDefense(int defense) { this.defense = defense; }

    /**
     * Returns the hero's attack stat.
     *
     * @return attack value
     */
    public int getAttack() { return attack; }

    /**
     * Sets the hero's attack stat.
     *
     * @param attack the new attack value
     */
    public void setAttack(int attack) { this.attack = attack; }

    /**
     * Returns the list of ability identifiers this hero has learned.
     *
     * @return a mutable {@link ArrayList} of ability string keys
     */
    public ArrayList<String> getAbility() { return ability; }

    /**
     * Replaces the hero's entire ability list.
     *
     * @param ability the new list of ability string keys
     */
    public void setAbility(ArrayList<String> ability) { this.ability = ability; }
}