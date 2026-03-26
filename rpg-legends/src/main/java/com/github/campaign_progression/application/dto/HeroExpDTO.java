package com.github.campaign_progression.application.dto;

/**
 * DTO representing a hero's experience gain after distributing EXP.
 */
public class HeroExpDTO {

    private final String heroName;
    private final int expGained;
    private final int newTotalExp;

    public HeroExpDTO(String heroName, int expGained, int newTotalExp) {
        this.heroName = heroName;
        this.expGained = expGained;
        this.newTotalExp = newTotalExp;
    }

    public String getHeroName() {
        return heroName;
    }

    public int getExpGained() {
        return expGained;
    }

    public int getNewTotalExp() {
        return newTotalExp;
    }
}