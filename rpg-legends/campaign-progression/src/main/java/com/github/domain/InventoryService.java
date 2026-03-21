package com.github.domain;

import java.util.ArrayList;
import java.util.List;

public class InventoryService {

    private int gold;
    private int goldSpent;
    private final int[] items; // index = ItemType.ordinal()

    public InventoryService(int startingGold) {
        this.gold = startingGold;
        this.goldSpent = 0;
        this.items = new int[ItemType.values().length]; // one slot per enum
    }

    public int getGold() {
        return gold;
    }

    public int getGoldSpent() {
        return goldSpent;
    }

    public boolean canAfford(int amount) {
        return gold >= amount;
    }

    /** Add item to inventory by enum type */
    public void addItem(ItemType type) {
        addItem(type, 1);
    }

    public void addItem(ItemType type, int amount) {
        if (amount < 1) throw new IllegalArgumentException("Amount must be positive");

        int totalCost = type.getCost() * amount;
        if (!canAfford(totalCost)) {
            throw new IllegalArgumentException("Not enough gold to buy " + amount + " " + type.getName());
        }

        items[type.ordinal()] += amount;
        gold -= totalCost;
        goldSpent += totalCost;
    }

    /** Use items from inventory */
    public void useItem(ItemType type, int amount) {
        if (amount < 1) throw new IllegalArgumentException("Amount must be positive");
        int current = items[type.ordinal()];
        if (current < amount) throw new IllegalArgumentException("Not enough items: " + type.getName());

        items[type.ordinal()] -= amount;
    }

    /** Get current count of a specific item */
    public int getItemCount(ItemType type) {
        return items[type.ordinal()];
    }

    /** Return a copy of inventory counts for snapshot purposes */
    public List<Integer> getItemsSnapshot() {
        List<Integer> snapshot = new ArrayList<>();
        for (int count : items) snapshot.add(count);
        return snapshot;
    }

    /** Restore inventory counts from snapshot (used when loading) */
    public void loadItemsSnapshot(List<Integer> snapshot) {
        if (snapshot.size() != items.length) {
            throw new IllegalArgumentException("Snapshot size does not match item types");
        }
        for (int i = 0; i < items.length; i++) {
            items[i] = snapshot.get(i);
        }
    }
}