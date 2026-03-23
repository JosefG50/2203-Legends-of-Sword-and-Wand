package com.github.campaign_progression.domain;


/**
 * Enum representing all possible item types in the game.
 * Each type stores its own stats: cost, healing values, and revival ability.
 */
public enum ItemType {
    BREAD("Bread", 200, 20, 0, false),
    CHEESE("Cheese", 500, 55, 0, false),
    STEAK("Steak", 1000, 200, 0, false),
    WATER("Water", 150, 0, 10, false),
    JUICE("Juice", 400, 0, 30, false),
    WINE("Wine", 750, 0, 100, false),
    EXILIR("Exilir", 2000, 999, 999, true);

    private final String name;
    private final int cost;
    private final int hpHeal;
    private final int manaHeal;
    private final boolean canRevive;

    ItemType(String name, int cost, int hpHeal, int manaHeal, boolean canRevive) {
        this.name = name;
        this.cost = cost;
        this.hpHeal = hpHeal;
        this.manaHeal = manaHeal;
        this.canRevive = canRevive;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getHpHeal() {
        return hpHeal;
    }

    public int getManaHeal() {
        return manaHeal;
    }

    public boolean canRevive() {
        return canRevive;
    }
}