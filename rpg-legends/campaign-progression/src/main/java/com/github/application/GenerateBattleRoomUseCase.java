package com.github.application;

import com.github.domain.*;

import java.util.List;

public class GenerateBattleRoomUseCase {

    private final PartyService partyService;
    private final EnemyFactory enemyFactory;

    public GenerateBattleRoomUseCase(PartyService partyService,
                                     EnemyFactory enemyFactory) {
        this.partyService = partyService;
        this.enemyFactory = enemyFactory;
    }

    public BattleRoom execute() {
        int partyLevel = partyService.getPartyLevel();

        List<Enemy> enemies = enemyFactory.createEnemies(partyLevel);

        return new BattleRoom(enemies);
    }
}