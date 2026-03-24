package com.github.campaign_progression.application;

import com.github.campaign_progression.domain.*;
import com.github.campaign_progression.application.dto.*;

import java.util.List;
import java.util.stream.Collectors;

public class EndCampaignUseCase {

    private final PartyService partyService;
    private final InventoryService inventoryService;

    public EndCampaignUseCase(PartyService partyService,
                              InventoryService inventoryService) {
        this.partyService = partyService;
        this.inventoryService = inventoryService;
    }

    public CampaignEndDTO execute() {

        // ✅ Party mapped using HeroInstanceDTO
        List<HeroInstanceDTO> partyDTO = partyService.getParty().stream()
                .map(HeroInstanceDTO::fromDomain)
                .collect(Collectors.toList());

        // ✅ Score inputs
        int totalLevels = partyService.getTotalLevels();
        int goldSpent = inventoryService.getGoldSpent();
        int extraGold = inventoryService.getGold();

        // ✅ Domain score calculation
        Score scoreCalculator = new Score();
        int totalScore = scoreCalculator.calculateScore(goldSpent, totalLevels, extraGold);

        // ✅ Message
        String message = "Campaign complete! Your final results are ready.";

        // ⚠️ CampaignEndDTO must now accept HeroInstanceDTO list instead of HeroSummaryDTO
        return new CampaignEndDTO(partyDTO, totalScore, message);
    }
}