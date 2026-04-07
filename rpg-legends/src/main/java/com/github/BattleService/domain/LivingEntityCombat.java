package com.github.BattleService.domain;

final class LivingEntityCombat {

    private LivingEntityCombat() {
    }

    static boolean areActorAndTargetAlive(LivingEntity actor, LivingEntity target) {
        return actor != null && target != null && actor.isAlive() && target.isAlive();
    }
}
