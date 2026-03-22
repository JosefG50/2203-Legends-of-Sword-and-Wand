package com.github.BattleService.application;

import com.github.BattleService.domain.AttackAction;
import com.github.BattleService.domain.BattleEngine;
import com.github.BattleService.domain.CastAction;
import com.github.BattleService.domain.DefendAction;
import com.github.BattleService.domain.LivingEntity;
import com.github.BattleService.domain.WaitAction;
import com.github.BattleService.dto.ActionRequest;
import com.github.BattleService.dto.BattleStateResponse;
import com.github.BattleService.dto.StartBattleRequest;
import com.github.org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class BattleApplicationService {

    private BattleEngine battleEngine;
    private LivingEntity hero;
    private LivingEntity enemy;

    public BattleStateResponse startBattle(StartBattleRequest request) {
        hero = new LivingEntity(
                request.getHeroName(),
                request.getHeroHp(),
                request.getHeroMana(),
                request.getHeroShield(),
                request.getHeroAttack(),
                request.getHeroDefense()
        );

        enemy = new LivingEntity(
                request.getEnemyName(),
                request.getEnemyHp(),
                request.getEnemyMana(),
                request.getEnemyShield(),
                request.getEnemyAttack(),
                request.getEnemyDefense()
        );

        battleEngine = new BattleEngine();
        battleEngine.startBattle(Arrays.asList(hero), Arrays.asList(enemy));

        return getBattleState();
    }

    public BattleStateResponse submitAction(ActionRequest request) {
        if (battleEngine == null || hero == null || enemy == null) {
            return new BattleStateResponse(
                    "None",
                    0,
                    0,
                    0,
                    0,
                    true,
                    "No active battle"
            );
        }

        if (request == null || !request.isValid()) {
            return getBattleState();
        }

        String actionType = request.getNormalizedActionType();
        String targetName = request.getNormalizedTargetName();

        LivingEntity target;

        if (targetName.equalsIgnoreCase(hero.getName())) {
            target = hero;
        } else if (targetName.equalsIgnoreCase(enemy.getName())) {
            target = enemy;
        } else {
            return getBattleState();
        }

        switch (actionType) {
            case "attack":
                battleEngine.submitAction(new AttackAction(), target);
                break;
            case "defend":
                battleEngine.submitAction(new DefendAction(), target);
                break;
            case "wait":
                battleEngine.submitAction(new WaitAction(), target);
                break;
            case "cast":
                battleEngine.submitAction(new CastAction(20, 25), target);
                break;
            default:
                return getBattleState();
        }

        return getBattleState();
    }

    public BattleStateResponse getBattleState() {
        if (battleEngine == null || hero == null || enemy == null) {
            return new BattleStateResponse(
                    "None",
                    0,
                    0,
                    0,
                    0,
                    true,
                    "No active battle"
            );
        }

        String currentUnitName = battleEngine.getCurrentUnit() != null
                ? battleEngine.getCurrentUnit().getName()
                : "None";

        return new BattleStateResponse(
                currentUnitName,
                hero.getHp(),
                hero.getMana(),
                enemy.getHp(),
                enemy.getMana(),
                battleEngine.isBattleOver(),
                battleEngine.getBattleResult()
        );
    }
}