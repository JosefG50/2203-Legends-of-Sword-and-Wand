package com.github.campaign_progression.application;

import com.github.campaign_progression.application.dto.BattleRoomResponseDTO;
import com.github.campaign_progression.domain.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Use case responsible for generating a battle room based on the party's strength.
 *
 * <p>The number and level of enemies is determined by the total levels of the party
 * via the {@link EnemyFactory}.</p>
 *
 * <p>Returns a {@link BattleRoomResponseDTO} containing all enemy stats for front-end or combat system use.</p>
 */
public class GenerateBattleRoomUseCase {

    private final PartyService partyService;
    private final EnemyFactory enemyFactory;

    /**
     * Constructs the use case.
     *
     * @param partyService the current party manager
     * @param enemyFactory factory for generating enemies
     */
    public GenerateBattleRoomUseCase(PartyService partyService,
                                     EnemyFactory enemyFactory) {
        this.partyService = partyService;
        this.enemyFactory = enemyFactory;
    }

    /**
     * Executes the use case.
     *
     * @return a {@link BattleRoomResponseDTO} containing the enemies in the room
     */
    public BattleRoomResponseDTO execute() {

        int partyLevel = partyService.getTotalLevels();

        List<Enemy> enemies = enemyFactory.createEnemies(partyLevel);

        List<BattleRoomResponseDTO.EnemyDTO> enemyDTOs = enemies.stream()
                .map(e -> new BattleRoomResponseDTO.EnemyDTO(
                        e.getLevel(),
                        e.getHp(),
                        e.getAttack(),
                        e.getDefense()
                ))
                .collect(Collectors.toList());

        return new BattleRoomResponseDTO(enemyDTOs);
    }
}