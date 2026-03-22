package BattleService.domain;

public class BattleContext {
    private LivingEntity actor;
    private LivingEntity target;
    private BattleState battleState;
    private TurnManager turnManager;

    public BattleContext(LivingEntity actor, LivingEntity target, BattleState battleState, TurnManager turnManager) {
        this.actor = actor;
        this.target = target;
        this.battleState = battleState;
        this.turnManager = turnManager;
    }

    public LivingEntity getActor() {
        return actor;
    }

    public LivingEntity getTarget() {
        return target;
    }

    public BattleState getBattleState() {
        return battleState;
    }

    public TurnManager getTurnManager() {
        return turnManager;
    }
}