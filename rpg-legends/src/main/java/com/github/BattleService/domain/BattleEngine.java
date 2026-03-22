package com.github.BattleService.domain;

import java.util.ArrayList;
import java.util.List;

public class BattleEngine {
    private BattleState battleState;
    private TurnManager turnManager;

    public void startBattle(List<LivingEntity> playerParty, List<LivingEntity> enemyParty) {
        battleState = new BattleState(playerParty, enemyParty);

        List<LivingEntity> allUnits = new ArrayList<>();
        allUnits.addAll(playerParty);
        allUnits.addAll(enemyParty);

        turnManager = new TurnManager(allUnits);
    }

    public void submitAction(Action action, LivingEntity target) {
        if (battleState == null || turnManager == null) {
            return;
        }

        if (isBattleOver()) {
            return;
        }

        if (action == null || target == null) {
            return;
        }

        LivingEntity actor = turnManager.getCurrentUnit();

        if (actor == null) {
            return;
        }

        if (actor.getHp() <= 0 || target.getHp() <= 0) {
            return;
        }

        BattleContext context = new BattleContext(actor, target, battleState, turnManager);
        action.execute(context);

        if (!isBattleOver()) {
            turnManager.advanceTurn();
        }
    }

    public boolean isBattleOver() {
        if (battleState == null) {
            return false;
        }

        return battleState.isPartyDefeated(battleState.getPlayerParty()) ||
                battleState.isPartyDefeated(battleState.getEnemyParty());
    }

    public String getBattleResult() {
        if (battleState == null) {
            return "No active battle";
        }

        if (!isBattleOver()) {
            return "Battle is still ongoing";
        }

        if (battleState.isPartyDefeated(battleState.getEnemyParty())) {
            return "Player party wins";
        }

        return "Enemy party wins";
    }

    public LivingEntity getCurrentUnit() {
        if (turnManager == null || isBattleOver()) {
            return null;
        }

        return turnManager.getCurrentUnit();
    }
}