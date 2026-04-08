package com.github.campaign_progression.domain;

import java.util.List;

public class InventoryService {

    private int gold;
    private int goldSpent;
    private final int[] items;

    public InventoryService(int startingGold) {
        this.gold = startingGold;
        this.goldSpent = 0;
        this.items = new int[ItemType.values().length];
    }

    public int getGold() {
        return gold;
    }

    public void minusGold(int amount) {
        if (amount < 0) throw new IllegalArgumentException("Amount must be non-negative");
        gold -= amount;
    }

    public void gainGold(int amount) {
        if (amount < 0) throw new IllegalArgumentException("Amount must be non-negative");
        gold += amount;
    }

    public void setItems(int[] items) {
        if (items.length != this.items.length) {
            throw new IllegalArgumentException("Items array length mismatch");
        }
        System.arraycopy(items, 0, this.items, 0, items.length);
    }
    public int getItemTypeCount() {
    return items.length;
}

    public int getGoldSpent() {
        return goldSpent;
    }

    public boolean canAfford(int amount) {
        return gold >= amount;
    }

    public void addItem(ItemType type) {
        addItem(type, 1);
    }

    public void addItem(ItemType type, int amount) {
        if (amount < 1) throw new IllegalArgumentException("Amount must be positive");

        int totalCost = type.getCost() * amount;

        if (!canAfford(totalCost)) {
            throw new IllegalArgumentException("Not enough gold to buy " + type.getName());
        }

        items[type.ordinal()] += amount;
        gold -= totalCost;
        goldSpent += totalCost;
    }

    public void useItem(ItemType type, int amount) {
        if (amount < 1) throw new IllegalArgumentException("Amount must be positive");

        int current = items[type.ordinal()];
        if (current < amount) {
            throw new IllegalArgumentException("Not enough items: " + type.getName());
        }

        items[type.ordinal()] -= amount;
    }

    public int getItemCount(ItemType type) {
        return items[type.ordinal()];
    }

    public int[] getItems() {
        return items.clone();
    }

    public void loadItemsSnapshot(List<Integer> snapshot) {
        if (snapshot.size() != items.length) {
            throw new IllegalArgumentException("Snapshot size does not match item types");
        }

        for (int i = 0; i < items.length; i++) {
            items[i] = snapshot.get(i);
        }
    }
}