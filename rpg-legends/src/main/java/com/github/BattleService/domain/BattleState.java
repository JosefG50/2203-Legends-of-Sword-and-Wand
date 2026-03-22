package BattleService.domain;

import java.util.List;

public class BattleState {
    private List<LivingEntity> playerParty;
    private List<LivingEntity> enemyParty;

    public BattleState(List<LivingEntity> playerParty, List<LivingEntity> enemyParty) {
        this.playerParty = playerParty;
        this.enemyParty = enemyParty;
    }

    public List<LivingEntity> getPlayerParty() {
        return playerParty;
    }

    public List<LivingEntity> getEnemyParty() {
        return enemyParty;
    }

    public boolean isPartyDefeated(List<LivingEntity> party) {
        for (LivingEntity unit : party) {
            if (unit.isAlive()) {
                return false;
            }
        }
        return true;
    }
}