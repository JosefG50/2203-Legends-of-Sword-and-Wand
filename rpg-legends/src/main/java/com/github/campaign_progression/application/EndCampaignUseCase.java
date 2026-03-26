package com.github.campaign_progression.application;

import com.github.campaign_progression.domain.*;
import com.github.campaign_progression.application.dto.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Use case responsible for finalizing the campaign and producing the end summary.
 *
 * <p>This use case gathers all relevant data from the domain layer:
 * <ul>
 *     <li>Party members (converted to DTOs)</li>
 *     <li>Total levels</li>
 *     <li>Gold spent</li>
 *     <li>Remaining gold</li>
 * </ul>
 *
 * It then calculates the final score and returns a {@link CampaignEndDTO}
 * containing the results.</p>
 */
public class EndCampaignUseCase {

    private final PartyService partyService;
    private final InventoryService inventoryService;

    /**
     * Constructs the use case with required services.
     *
     * @param partyService the party manager
     * @param inventoryService the inventory/gold manager
     */
    public EndCampaignUseCase(PartyService partyService,
                             InventoryService inventoryService) {
        this.partyService = partyService;
        this.inventoryService = inventoryService;
    }

    /**
     * Executes the campaign ending logic.
     *
     * @return a {@link CampaignEndDTO} containing party summary, score, and message
     */
    public CampaignEndDTO execute() {

        // Convert party to DTOs
        List<HeroInstanceDTO> partyDTO = partyService.getParty().stream()
                .map(HeroInstanceDTO::fromDomain)
                .collect(Collectors.toList());

        // Gather score inputs
        int totalLevels = partyService.getTotalLevels();
        int goldSpent = inventoryService.getGoldSpent();
        int extraGold = inventoryService.getGold();

        // Calculate score
        Score scoreCalculator = new Score();
        int totalScore = scoreCalculator.calculateScore(goldSpent, totalLevels, extraGold);

        // Final message
        String message = "Campaign complete! Your final results are ready.";

        return new CampaignEndDTO(partyDTO, totalScore, message);
    }
}