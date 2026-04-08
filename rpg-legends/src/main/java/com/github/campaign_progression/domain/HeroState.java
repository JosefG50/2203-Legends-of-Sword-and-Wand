package com.github.campaign_progression.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the full mutable state of a hero in the campaign party.
 *
 * Tracks combat stats, progression data, and abilities.
 * This class contains NO game logic — only state.
 *
 * Specializations: MAGE, WARRIOR, ORDER, CHAOS
 */
public class HeroState {

    // ===================== SPECIALIZATION LEVELS =====================
    private int mageLvl;
    private int warriorLvl;
    private int orderLvl;
    private int chaosLvl;

    // ===================== BASIC INFO =====================
    private String name;

    // ===================== COMBAT STATS =====================
    private int curHp;
    private int maxHp;

    private int curMana;
    private int maxMana;

    private int attack;
    private int defense;

    // ===================== PROGRESSION =====================
    private int level;
    private int curExp;
    private int lvlUpExp;

    private String specialization;

    // ===================== ABILITIES =====================
    private List<String> abilities = new ArrayList<>();

    // ===================== GETTERS / SETTERS =====================

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getCurHp() { return curHp; }
    public void setCurHp(int curHp) { this.curHp = curHp; }

    public int getMaxHp() { return maxHp; }
    public void setMaxHp(int maxHp) { this.maxHp = maxHp; }

    public int getCurMana() { return curMana; }
    public void setCurMana(int curMana) { this.curMana = curMana; }

    public int getMaxMana() { return maxMana; }
    public void setMaxMana(int maxMana) { this.maxMana = maxMana; }

    public int getAttack() { return attack; }
    public void setAttack(int attack) { this.attack = attack; }

    public int getDefense() { return defense; }
    public void setDefense(int defense) { this.defense = defense; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public int getCurExp() { return curExp; }
    public void setCurExp(int curExp) { this.curExp = curExp; }

    public int getLvlUpExp() { return lvlUpExp; }
    public void setLvlUpExp(int lvlUpExp) { this.lvlUpExp = lvlUpExp; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public List<String> getAbilities() { return abilities; }
    public void setAbilities(List<String> abilities) { this.abilities = abilities; }

    // ===================== SPECIALIZATION LEVEL GETTERS/SETTERS =====================

    public int getMageLvl() { return mageLvl; }
    public void setMageLvl(int mageLvl) { this.mageLvl = mageLvl; }

    public int getWarriorLvl() { return warriorLvl; }
    public void setWarriorLvl(int warriorLvl) { this.warriorLvl = warriorLvl; }

    public int getOrderLvl() { return orderLvl; }
    public void setOrderLvl(int orderLvl) { this.orderLvl = orderLvl; }

    public int getChaosLvl() { return chaosLvl; }
    public void setChaosLvl(int chaosLvl) { this.chaosLvl = chaosLvl; }
}