package com.github.campaign_progression.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryServiceTest {

    private InventoryService inventory;

    @BeforeEach
    void setUp() {
        inventory = new InventoryService();
    }

    @Test
    void canAffordShouldReturnTrueWhenEnoughGold() {
        // assuming default gold is >= 0
        assertTrue(inventory.canAfford(0));
    }

    @Test
    void canAffordShouldReturnFalseWhenNotEnoughGold() {
        assertFalse(inventory.canAfford(Integer.MAX_VALUE));
    }

    @Test
    void minusGoldShouldReduceGold() {
        int startingGold = inventory.getGold();

        if (startingGold > 0) {
            int deducted = inventory.minusGold(1);

            assertEquals(1, deducted);
            assertEquals(startingGold - 1, inventory.getGold());
        }
    }

    @Test
    void minusGoldShouldThrowOnNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> {
            inventory.minusGold(-5);
        });
    }

    @Test
    void minusGoldShouldThrowWhenNotEnoughGold() {
        assertThrows(IllegalArgumentException.class, () -> {
            inventory.minusGold(Integer.MAX_VALUE);
        });
    }

    @Test
    void addItemShouldThrowOnInvalidId() {
        assertThrows(IllegalArgumentException.class, () -> {
            inventory.addItem(-1, 1);
        });
    }

    @Test
    void addItemShouldThrowOnNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> {
            inventory.addItem(0, -1);
        });
    }

    @Test
    void useItemShouldThrowWhenNotEnoughItems() {
        assertThrows(IllegalArgumentException.class, () -> {
            inventory.useItem(0, 1);
        });
    }

    @Test
    void useItemShouldThrowOnInvalidId() {
        assertThrows(IllegalArgumentException.class, () -> {
            inventory.useItem(-1, 1);
        });
    }

    @Test
    void useItemShouldThrowOnNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> {
            inventory.useItem(0, -1);
        });
    }
}