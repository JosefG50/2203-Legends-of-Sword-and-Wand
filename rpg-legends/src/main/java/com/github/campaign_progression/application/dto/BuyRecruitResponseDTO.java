package com.github.campaign_progression.application.dto;

public class BuyRecruitResponseDTO {

    private final boolean success;
    private final String message;
    private final String heroName;
    private final String specialization;
    private final int level;

    public BuyRecruitResponseDTO(
            boolean success,
            String message,
            String heroName,
            String specialization,
            int level
    ) {
        this.success = success;
        this.message = message;
        this.heroName = heroName;
        this.specialization = specialization;
        this.level = level;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getHeroName() {
        return heroName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public int getLevel() {
        return level;
    }
}