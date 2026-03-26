package com.github.campaign_progression.application;

import com.github.campaign_progression.application.dto.CampaignEndDTO;
import org.springframework.web.client.RestTemplate;

/**
 * Use case for saving the end-of-campaign results to a PvP service.
 *
 * <p>This use case:</p>
 * <ul>
 *     <li>Executes {@link EndCampaignUseCase} to generate the campaign results.</li>
 *     <li>Posts the results to a PvP service via {@link RestTemplate}.</li>
 *     <li>Returns the DTO for local UI consumption (e.g., Godot).</li>
 * </ul>
 */
public class SaveCampaignToPvpUseCase {

    private final EndCampaignUseCase endCampaignUseCase;
    private final RestTemplate restTemplate;
    private final String pvpUrl;

    /**
     * Constructs the use case.
     *
     * @param endCampaignUseCase the end-campaign use case; must not be null
     * @param restTemplate the RestTemplate for HTTP calls; must not be null
     * @param pvpUrl the base URL of the PvP service; must not be null
     */
    public SaveCampaignToPvpUseCase(EndCampaignUseCase endCampaignUseCase,
                                    RestTemplate restTemplate,
                                    String pvpUrl) {
        this.endCampaignUseCase = endCampaignUseCase;
        this.restTemplate = restTemplate;
        this.pvpUrl = pvpUrl;
    }

    /**
     * Executes the use case:
     * <ol>
     *     <li>Gets the campaign end result DTO.</li>
     *     <li>Sends it to the PvP service via POST.</li>
     *     <li>Returns the DTO for local use.</li>
     * </ol>
     *
     * @return the {@link CampaignEndDTO} representing the final campaign result
     */
    public CampaignEndDTO execute() {
        CampaignEndDTO endDTO = endCampaignUseCase.execute();

        // POST to PvP service
        restTemplate.postForObject(pvpUrl + "/pvp/save", endDTO, Void.class);

        return endDTO;
    }
}