package com.github.campaign_progression.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    // Simple concrete class for testing abstract Item
    static class TestItem extends Item {
        public TestItem(int cost, int hpHeal, int manaHeal, boolean canRevive) {
            this.cost = cost;
            this.hpHeal = hpHeal;
            this.manaHeal = manaHeal;
            this.canRevive = canRevive;
        }
    }

    @Test
    void shouldReturnCorrectCost() {
        Item item = new TestItem(50, 0, 0, false);
        assertEquals(50, item.getCost());
    }

    @Test
    void shouldReturnCorrectHpHeal() {
        Item item = new TestItem(0, 25, 0, false);
        assertEquals(25, item.getHpHeal());
    }

    @Test
    void shouldReturnCorrectManaHeal() {
        Item item = new TestItem(0, 0, 30, false);
        assertEquals(30, item.getManaHeal());
    }

    @Test
    void shouldReturnCorrectReviveFlagTrue() {
        Item item = new TestItem(0, 0, 0, true);
        assertTrue(item.getCanRevive());
    }

    @Test
    void shouldReturnCorrectReviveFlagFalse() {
        Item item = new TestItem(0, 0, 0, false);
        assertFalse(item.getCanRevive());
    }

    @Test
    void defaultReviveShouldBeFalse() {
        Item item = new TestItem(0, 0, 0, false);
        assertFalse(item.getCanRevive());
    }
}