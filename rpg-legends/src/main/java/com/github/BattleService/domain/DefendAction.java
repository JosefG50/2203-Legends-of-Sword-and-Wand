package com.github.BattleService.domain;

public class DefendAction implements Action {

    private static final int DEFEND_HP_RESTORE = 10;
    private static final int DEFEND_MANA_RESTORE = 5;

    @Override
    public void execute(BattleContext context) {
        if (context == null) {
            return;
        }

        LivingEntity actor = context.getActor();

        if (actor == null || !actor.isAlive()) {
            return;
        }

        actor.restoreHp(DEFEND_HP_RESTORE);
        actor.restoreMana(DEFEND_MANA_RESTORE);
    }
}