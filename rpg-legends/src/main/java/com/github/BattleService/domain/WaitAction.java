package com.github.BattleService.domain;

public class WaitAction implements Action {
    @Override
    public void execute(BattleContext context) {
        if (context == null) {
            return;
        }

        LivingEntity actor = context.getActor();

        if (actor == null || !actor.isAlive()) {
            return;
        }

    }
}
