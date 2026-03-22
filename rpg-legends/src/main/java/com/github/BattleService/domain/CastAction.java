package com.github.BattleService.domain;

public class CastAction implements Action {

    private final int manaCost;
    private final int spellDamage;

    public CastAction(int manaCost, int spellDamage) {
        this.manaCost = manaCost;
        this.spellDamage = spellDamage;
    }

    @Override
    public void execute(BattleContext context) {
        if (context == null) {
            return;
        }

        LivingEntity actor = context.getActor();
        LivingEntity target = context.getTarget();

        if (actor == null || target == null) {
            return;
        }

        if (!actor.isAlive() || !target.isAlive()) {
            return;
        }

        if (actor.getMana() < manaCost) {
            return;
        }

        actor.useMana(manaCost);
        target.takeDamage(spellDamage);
    }
}