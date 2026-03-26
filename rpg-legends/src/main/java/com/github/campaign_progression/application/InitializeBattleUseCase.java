package com.github.campaign_progression.application;

import com.github.campaign_progression.application.dto.BattleRoomResponseDTO;
import org.springframework.web.client.RestTemplate;

/**
 * Use case to initialize a battle.
 *
 * <p>This use case generates the battle room data using {@link GenerateBattleRoomUseCase}
 * and posts it to an external battle library (BattleLib) via REST. The data is also returned
 * immediately for UI consumption (e.g., Godot frontend).</p>
 */
public class InitializeBattleUseCase {

    private final GenerateBattleRoomUseCase generateBattleRoomUseCase;
    private final RestTemplate restTemplate;
    private final String battleLibUrl;

    /**
     * Constructs the use case.
     *
     * @param generateBattleRoomUseCase domain use case that creates battle room data
     * @param restTemplate              Spring REST client to post to external battle library
     * @param battleLibUrl              base URL of the external battle library
     */
    public InitializeBattleUseCase(GenerateBattleRoomUseCase generateBattleRoomUseCase,
                                   RestTemplate restTemplate,
                                   String battleLibUrl) {
        this.generateBattleRoomUseCase = generateBattleRoomUseCase;
        this.restTemplate = restTemplate;
        this.battleLibUrl = battleLibUrl;
    }

    /**
     * Executes the use case: generates battle data and sends it to the battle library.
     *
     * @return the generated {@link BattleRoomResponseDTO} for immediate UI consumption
     */
    public BattleRoomResponseDTO execute() {
        // Generate enemies
        BattleRoomResponseDTO battleData = generateBattleRoomUseCase.execute();

        // POST to BattleLib
        restTemplate.postForObject(battleLibUrl + "/battle/initialize", battleData, Void.class);

        // Return to UI immediately
        return battleData;
    }
}