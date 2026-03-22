package com.github.application;

import com.github.domain.*;
import com.github.application.dto.BattleRoomResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

public class GenerateBattleRoomUseCase {

    private final PartyService partyService;
    private final EnemyFactory enemyFactory;

    public GenerateBattleRoomUseCase(PartyService partyService,
                                     EnemyFactory enemyFactory) {
        this.partyService = partyService;
        this.enemyFactory = enemyFactory;
    }

    public BattleRoomResponseDTO execute() {

        int partyLevel = partyService.getTotalLevels(); // fix naming

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