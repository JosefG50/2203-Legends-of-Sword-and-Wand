package com.github.campaign_progression.application.dto;

import java.util.List;

public class CampaignEndDTO {
    private final List<HeroInstanceDTO> partySummary;
    private final int totalScore;
    private final String message;

    public CampaignEndDTO(List<HeroInstanceDTO> partyDTO, int totalScore, String message) {
        this.partySummary = partyDTO;
        this.totalScore = totalScore;
        this.message = message;
    }

    public List<HeroInstanceDTO> getPartySummary() {
        return partySummary;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public String getMessage() {
        return message;
    }

    public static class HeroInstanceDTO {
        private final String name;
        private final String specialization;
        private final int level;
        private final int hp;
        private final int mana;

        public HeroInstanceDTO(String name, String specialization, int level, int hp, int mana) {
            this.name = name;
            this.specialization = specialization;
            this.level = level;
            this.hp = hp;
            this.mana = mana;
        }

        public String getName() { return name; }
        public String getSpecialization() { return specialization; }
        public int getLevel() { return level; }
        public int getHp() { return hp; }
        public int getMana() { return mana; }
    }
}