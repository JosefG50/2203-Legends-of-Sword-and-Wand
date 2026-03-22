package BattleService.domain;

public class DefendAction implements Action {
    @Override
    public void execute(BattleContext context) {
        if (context == null) {
            return;
        }

        LivingEntity actor = context.getActor();

        if (actor == null || !actor.isAlive()) {
            return;
        }

        actor.restoreHp(10);
        actor.restoreMana(5);
    }
}