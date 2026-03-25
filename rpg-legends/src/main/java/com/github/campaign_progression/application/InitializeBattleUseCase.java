package com.github.campaign_progression.application;

import com.github.campaign_progression.application.dto.BattleRoomResponseDTO;
import org.springframework.web.client.RestTemplate;

public class InitializeBattleUseCase {

    private final GenerateBattleRoomUseCase generateBattleRoomUseCase;
    private final RestTemplate restTemplate;
    private final String battleLibUrl;

    public InitializeBattleUseCase(GenerateBattleRoomUseCase generateBattleRoomUseCase,
                                    RestTemplate restTemplate,
                                    String battleLibUrl) {
        this.generateBattleRoomUseCase = generateBattleRoomUseCase;
        this.restTemplate = restTemplate;
        this.battleLibUrl = battleLibUrl;
    }

    public BattleRoomResponseDTO execute() {
        // Generate enemies
        BattleRoomResponseDTO battleData = generateBattleRoomUseCase.execute();

        // POST to BattleLib
        restTemplate.postForObject(battleLibUrl + "/battle/initialize", battleData, Void.class);

        // Return to Godot as well so UI can react immediately
        return battleData;
    }
}