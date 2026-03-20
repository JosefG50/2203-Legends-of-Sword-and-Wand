package com.github.domain;

public class InventoryService {
    private int gold;
    private List<Item> items;

    public int getGold() {
        return gold;
    }

    public int minusGold(int amount) {
        if (canAfford(amount)) {
            gold -= amount;
            return amount;
        }
        throw new IllegalArgumentException("Not enough gold");
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public boolean canAfford(int amount) {
        return gold >= amount;
    }

    public void addItem(int id, int amount) {
        // Logic to add item by id and amount
    }
    
}
