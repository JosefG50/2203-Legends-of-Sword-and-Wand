package com.github.campaign_progression.application.dto;
import com.github.campaign_progression.domain.HeroState;


public class HeroInstanceDTO {

    private String name;
    private String specialization;
    private int level;

    private int curHp;
    private int maxHp;

    private int curMana;
    private int maxMana;

    // Constructors
    public HeroInstanceDTO() {}

    public HeroInstanceDTO(
            String name,
            String specialization,
            int level,
            int curHp,
            int maxHp,
            int curMana,
            int maxMana
    ) {
        this.name = name;
        this.specialization = specialization;
        this.level = level;
        this.curHp = curHp;
        this.maxHp = maxHp;
        this.curMana = curMana;
        this.maxMana = maxMana;
    }
    

    // Getters & Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public int getCurHp() { return curHp; }
    public void setCurHp(int curHp) { this.curHp = curHp; }

    public int getMaxHp() { return maxHp; }
    public void setMaxHp(int maxHp) { this.maxHp = maxHp; }

    public int getCurMana() { return curMana; }
    public void setCurMana(int curMana) { this.curMana = curMana; }

    public int getMaxMana() { return maxMana; }
    public void setMaxMana(int maxMana) { this.maxMana = maxMana; }

    public static HeroInstanceDTO fromDomain(HeroState hero) {
    if (hero == null) {
        throw new IllegalArgumentException("Hero cannot be null");
    }

    return new HeroInstanceDTO(
            hero.getName(),
            hero.getSpecialization(),
            hero.getLevel(),
            hero.getCurHp(),
            hero.getMaxHp(),
            hero.getCurMana(),
            hero.getMaxMana()
    );
}
}