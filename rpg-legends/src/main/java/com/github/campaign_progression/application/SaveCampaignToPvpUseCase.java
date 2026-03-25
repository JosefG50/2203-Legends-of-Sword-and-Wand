package com.github.campaign_progression.application;

import com.github.campaign_progression.application.dto.CampaignEndDTO;
import org.springframework.web.client.RestTemplate;

public class SaveCampaignToPvpUseCase {

    private final EndCampaignUseCase endCampaignUseCase;
    private final RestTemplate restTemplate;
    private final String pvpUrl;

    public SaveCampaignToPvpUseCase(EndCampaignUseCase endCampaignUseCase,
                                     RestTemplate restTemplate,
                                     String pvpUrl) {
        this.endCampaignUseCase = endCampaignUseCase;
        this.restTemplate = restTemplate;
        this.pvpUrl = pvpUrl;
    }

    public CampaignEndDTO execute() {
        // Build end result
        CampaignEndDTO endDTO = endCampaignUseCase.execute();

        // POST to PVP
        restTemplate.postForObject(pvpUrl + "/pvp/save", endDTO, Void.class);

        // Return to Godot so it can show end screen
        return endDTO;
    }
}