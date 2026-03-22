package BattleService.domain;

public class AttackAction implements Action {
    @Override
    public void execute(BattleContext context) {
        if (context == null) return;

        LivingEntity attacker = context.getActor();
        LivingEntity target = context.getTarget();

        if (attacker == null || target == null) return;
        if (!attacker.isAlive() || !target.isAlive()) return;

        int damage = Math.max(0, attacker.getAttack() - target.getDefense());
        target.takeDamage(damage);
    }
}