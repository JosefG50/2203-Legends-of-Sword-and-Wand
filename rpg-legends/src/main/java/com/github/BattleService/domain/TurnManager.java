package BattleService.domain;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TurnManager {
    private final Queue<LivingEntity> turnQueue = new LinkedList<>();

    public TurnManager(List<LivingEntity> units) {
        for (LivingEntity unit : units) {
            if (unit != null && unit.isAlive()) {
                turnQueue.offer(unit);
            }
        }
    }

    public LivingEntity getCurrentUnit() {
        removeDeadUnitsFromFront();
        return turnQueue.peek();
    }

    public void advanceTurn() {
        removeDeadUnitsFromFront();

        LivingEntity current = turnQueue.poll();
        if (current != null && current.isAlive()) {
            turnQueue.offer(current);
        }

        removeDeadUnitsFromFront();
    }

    private void removeDeadUnitsFromFront() {
        while (!turnQueue.isEmpty() && !turnQueue.peek().isAlive()) {
            turnQueue.poll();
        }
    }
}