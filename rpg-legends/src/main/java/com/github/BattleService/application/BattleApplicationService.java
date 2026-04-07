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
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Application service for starting a battle, submitting actions, and reading state.
 * <p>
 * This Spring bean is a singleton with mutable instance fields ({@code hero},
 * {@code enemy}, {@code battleEngine}). All HTTP clients therefore share one
 * in-memory battle; concurrent requests can overwrite each other's state. This
 * deliverable does not introduce per-session storage so existing tests and API
 * behavior remain unchanged.
 */
@Service
public class BattleApplicationService {

    private static final int DEFAULT_CAST_MANA_COST = 20;
    private static final int DEFAULT_CAST_SPELL_DAMAGE = 25;

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
            return noActiveBattleResponse();
        }

        if (request == null || !request.isValid()) {
            return getBattleState();
        }

        String actionType = request.getNormalizedActionType();
        String targetName = request.getNormalizedTargetName();

        BattleCommand command = BattleCommand.fromNormalized(actionType);
        LivingEntity target = resolveTarget(targetName);

        if (target == null) {
            return getBattleState();
        }

        switch (command) {
            case ATTACK:
                battleEngine.submitAction(new AttackAction(), target);
                break;
            case DEFEND:
                battleEngine.submitAction(new DefendAction(), target);
                break;
            case WAIT:
                battleEngine.submitAction(new WaitAction(), target);
                break;
            case CAST:
                battleEngine.submitAction(
                        new CastAction(DEFAULT_CAST_MANA_COST, DEFAULT_CAST_SPELL_DAMAGE),
                        target
                );
                break;
            case UNKNOWN:
                return getBattleState();
        }

        return getBattleState();
    }

    public BattleStateResponse getBattleState() {
        if (battleEngine == null || hero == null || enemy == null) {
            return noActiveBattleResponse();
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

    private BattleStateResponse noActiveBattleResponse() {
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

    private LivingEntity resolveTarget(String targetName) {
        if (targetName.equalsIgnoreCase(hero.getName())) {
            return hero;
        }
        if (targetName.equalsIgnoreCase(enemy.getName())) {
            return enemy;
        }
        return null;
    }
}
