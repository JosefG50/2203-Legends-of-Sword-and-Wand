package com.github.campaign_progression.domain;

/**
 * Represents an item in the game. 
 * All actual item types (Bread, Cheese, etc.) are handled via ItemType enum.
 */
public class Item {

    private final ItemType type;

    public Item(ItemType type) {
        if (type == null) throw new IllegalArgumentException("ItemType cannot be null");
        this.type = type;
    }

    public String getName() {
        return type.getName();
    }

    public int getCost() {
        return type.getCost();
    }

    public int getHpHeal() {
        return type.getHpHeal();
    }

    public int getManaHeal() {
        return type.getManaHeal();
    }

    public boolean canRevive() {
        return type.canRevive();
    }

    public ItemType getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.format("%s [Cost=%d, HP=%d, Mana=%d, CanRevive=%s]",
                type.name(), type.getCost(), type.getHpHeal(), type.getManaHeal(), type.canRevive());
    }
}