package com.github.application.dto;

import com.github.domain.HeroState;
import java.util.List;

public class CampaignEndDTO {
    private final List<HeroSummaryDTO> partySummary;
    private final int totalScore;
    private final String message;

    public CampaignEndDTO(List<HeroSummaryDTO> partySummary, int totalScore, String message) {
        this.partySummary = partySummary;
        this.totalScore = totalScore;
        this.message = message;
    }

    public List<HeroSummaryDTO> getPartySummary() {
        return partySummary;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public String getMessage() {
        return message;
    }

    public static class HeroSummaryDTO {
        private final String name;
        private final String specialization;
        private final int level;
        private final int hp;
        private final int mana;

        public HeroSummaryDTO(String name, String specialization, int level, int hp, int mana) {
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