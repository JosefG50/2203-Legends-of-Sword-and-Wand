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

    public List<HeroInstanceDTO> getPartySummary() { return partySummary; }
    public int getTotalScore() { return totalScore; }
    public String getMessage() { return message; }
}