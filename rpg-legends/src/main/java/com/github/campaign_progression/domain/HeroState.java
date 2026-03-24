package com.github.campaign_progression.domain;

import java.util.ArrayList;

public class HeroState {
    private String name;
    private int curHp;
    private int maxHp;
    private String specialization;
    private int curMana;
    private int maxMana;

    private int level;
    private int mageLvl;
    private int warriorLvl;
    private int orderLvl;
    private int chaosLvl;
    
    private int curExp;
    private int lvlUpExp;
    private int defense;
    private int attack;
    ArrayList<String> ability = new ArrayList<>();

    

    // ===================== EXP / LEVEL LOGIC =====================

    /**
     * Adds EXP and returns how many levels were gained
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
     * Handles ONLY level counters + scaling
     * (no stat logic here)
     */
    private void increaseLevel() {
        level++;
        // TO DO: Change to let user decide
        switch (specialization) {
            case "MAGE":
                mageLvl++;
                break;
            case "WARRIOR":
                warriorLvl++;
                break;
            case "ORDER":
                orderLvl++;
                break;
            case "CHAOS":
                chaosLvl++;
                break;
        }

        // Scale next level requirement (simple + fair)
        lvlUpExp = (int) (lvlUpExp * 1.2);
    }

    // ===================== GETTERS / SETTERS =====================

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getLevel() { return level; }
    public int setlevel(int level) { return this.level = level; }

    public int getCurHp() { return curHp; }
    public void setCurHp(int curHp) { this.curHp = curHp; }

    public int getMaxHp() { return maxHp; }
    public void setMaxHp(int maxHp) { this.maxHp = maxHp; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public int getCurMana() { return curMana; }
    public void setCurMana(int curMana) { this.curMana = curMana; }

    public int getMaxMana() { return maxMana; }
    public void setMaxMana(int maxMana) { this.maxMana = maxMana; }

    public int getMageLvl() { return mageLvl; }
    public void setMageLvl(int mageLvl) { this.mageLvl = mageLvl; }

    public int getWarriorLvl() { return warriorLvl; }
    public void setWarriorLvl(int warriorLvl) { this.warriorLvl = warriorLvl; }

    public int getOrderLvl() { return orderLvl; }
    public void setOrderLvl(int orderLvl) { this.orderLvl = orderLvl; }

    public int getChaosLvl() { return chaosLvl; }
    public void setChaosLvl(int chaosLvl) { this.chaosLvl = chaosLvl; }

    public int getCurExp() { return curExp; }

    // ⚠️ Avoid using this in gameplay
    public void setCurExp(int curExp) { this.curExp = curExp; }

    public int getLvlUpExp() { return lvlUpExp; }
    public void setLvlUpExp(int lvlUpExp) { this.lvlUpExp = lvlUpExp; }

    public int getDefense() { return defense; }
    public void setDefense(int defense) { this.defense = defense; }

    public int getAttack() { return attack; }
    public void setAttack(int attack) { this.attack = attack; }

    public ArrayList<String> getAbility() { return ability; }
    public void setAbility(ArrayList<String> ability) { this.ability = ability; }
}