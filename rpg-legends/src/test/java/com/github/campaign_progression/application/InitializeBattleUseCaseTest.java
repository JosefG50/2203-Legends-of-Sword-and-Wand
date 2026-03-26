package com.github.campaign_progression.application;

import com.github.campaign_progression.application.dto.BattleRoomResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InitializeBattleUseCaseTest {

    @Test
    void execute_postsBattleDataAndReturnsIt() {
        GenerateBattleRoomUseCase generateBattleRoomUseCase = mock(GenerateBattleRoomUseCase.class);
        RestTemplate restTemplate = mock(RestTemplate.class);

        BattleRoomResponseDTO mockBattleData = mock(BattleRoomResponseDTO.class);
        when(generateBattleRoomUseCase.execute()).thenReturn(mockBattleData);

        String battleLibUrl = "http://localhost:8080";

        InitializeBattleUseCase useCase = new InitializeBattleUseCase(
                generateBattleRoomUseCase,
                restTemplate,
                battleLibUrl
        );

        BattleRoomResponseDTO result = useCase.execute();

        // Verify that the battle data was returned
        assertSame(mockBattleData, result);

        // Verify that POST was called exactly once
        verify(restTemplate, times(1))
                .postForObject(battleLibUrl + "/battle/initialize", mockBattleData, Void.class);

        // Verify that generateBattleRoomUseCase.execute() was called
        verify(generateBattleRoomUseCase, times(1)).execute();
    }
}