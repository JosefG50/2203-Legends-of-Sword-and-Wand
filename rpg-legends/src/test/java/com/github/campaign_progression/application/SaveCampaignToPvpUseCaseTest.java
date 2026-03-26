package com.github.campaign_progression.application;

import com.github.campaign_progression.application.dto.CampaignEndDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class SaveCampaignToPvpUseCaseTest {

    private EndCampaignUseCase endCampaignUseCase;
    private RestTemplate restTemplate;
    private SaveCampaignToPvpUseCase useCase;

    private static final String PVP_URL = "http://localhost:8080";

    @BeforeEach
    void setup() {
        endCampaignUseCase = mock(EndCampaignUseCase.class);
        restTemplate = mock(RestTemplate.class);
        useCase = new SaveCampaignToPvpUseCase(endCampaignUseCase, restTemplate, PVP_URL);
    }

    @Test
    void execute_sendsDtoToPvpAndReturnsDto() {
        CampaignEndDTO mockDto = mock(CampaignEndDTO.class);
        when(endCampaignUseCase.execute()).thenReturn(mockDto);

        CampaignEndDTO result = useCase.execute();

        // Verify that POST was called
        verify(restTemplate).postForObject(PVP_URL + "/pvp/save", mockDto, Void.class);

        // Verify returned DTO
        assertSame(mockDto, result);
    }
}