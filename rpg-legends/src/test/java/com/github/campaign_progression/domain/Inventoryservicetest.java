package com.github.campaign_progression.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryServiceTest {

    private InventoryService inventory;

    @BeforeEach
    void setUp() {
        // Provide a starting gold amount
        inventory = new InventoryService(100);
    }

    @Test
    void canAffordShouldReturnTrueWhenEnoughGold() {
        assertTrue(inventory.canAfford(50));
    }

    @Test
    void canAffordShouldReturnFalseWhenNotEnoughGold() {
        assertFalse(inventory.canAfford(200));
    }

    @Test
    void minusGoldShouldReduceGold() {
        int startingGold = inventory.getGold();
        inventory.minusGold(10);
        assertEquals(startingGold - 10, inventory.getGold());
    }

    @Test
    void minusGoldShouldThrowOnNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> inventory.minusGold(-5));
    }

    @Test
    void addItemShouldThrowWhenNotEnoughGold() {
        ItemType expensiveItem = ItemType.values()[0]; // adjust if necessary
        assertThrows(IllegalArgumentException.class, () -> inventory.addItem(expensiveItem, Integer.MAX_VALUE));
    }

    @Test
    void addItemShouldThrowOnNegativeAmount() {
        ItemType item = ItemType.values()[0];
        assertThrows(IllegalArgumentException.class, () -> inventory.addItem(item, -1));
    }

    @Test
    void addItemShouldAddItemsAndDeductGold() {
        ItemType item = ItemType.values()[0];
        int cost = item.getCost();
        int startingGold = inventory.getGold();

        inventory.addItem(item, 2);
        assertEquals(2, inventory.getItemCount(item));
        assertEquals(startingGold - 2 * cost, inventory.getGold());
    }

    @Test
    void useItemShouldThrowWhenNotEnoughItems() {
        ItemType item = ItemType.values()[0];
        assertThrows(IllegalArgumentException.class, () -> inventory.useItem(item, 1));
    }

    @Test
    void useItemShouldThrowOnNegativeAmount() {
        ItemType item = ItemType.values()[0];
        assertThrows(IllegalArgumentException.class, () -> inventory.useItem(item, -1));
    }

    @Test
    void useItemShouldConsumeItemsCorrectly() {
        ItemType item = ItemType.values()[0];
        inventory.addItem(item, 3);
        inventory.useItem(item, 2);
        assertEquals(1, inventory.getItemCount(item));
    }

    @Test
    void loadItemsSnapshotShouldRestoreItems() {
        ItemType item = ItemType.values()[0];
        inventory.addItem(item, 5);

        int[] snapshot = inventory.getItems();
        InventoryService restored = new InventoryService(50);
        restored.setItems(snapshot);

        assertEquals(5, restored.getItemCount(item));
    }
}