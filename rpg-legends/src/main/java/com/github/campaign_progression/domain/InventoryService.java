package com.github.campaign_progression.domain;

import java.util.List;

/**
 * Manages the player's gold and item inventory throughout the campaign.
 *
 * <p>Items are stored as a fixed-size integer array indexed by {@link ItemType#ordinal()},
 * meaning one slot exists per {@link ItemType} enum constant. Purchasing items deducts
 * gold automatically, and the total gold spent is tracked separately for statistics.</p>
 *
 * <p>This service is designed to be the single source of truth for inventory state.
 * For save/load support, use {@link #getItems()} to snapshot and {@link #setItems(int[])}
 * or {@link #loadItemsSnapshot(List)} to restore.</p>
 *
 * <p><b>Gold rules:</b> Both {@link #gainGold(int)} and {@link #minusGold(int)} reject
 * negative amounts. Gold is allowed to go negative via {@link #minusGold(int)} — callers
 * should check {@link #canAfford(int)} first if overdraft is undesirable.</p>
 */
public class InventoryService {

    /** The player's current gold balance. */
    private int gold;

    /** The cumulative amount of gold spent on items across the campaign. */
    private int goldSpent;

    /**
     * The inventory array, indexed by {@link ItemType#ordinal()}.
     * Each slot holds the quantity of that item type currently held.
     */
    private final int[] items; // index = ItemType.ordinal()

    /**
     * Constructs a new {@code InventoryService} with the given starting gold balance
     * and an empty inventory (all item counts initialised to zero).
     *
     * @param startingGold the initial gold balance; negative values are accepted
     *                     but will immediately prevent purchases
     */
    public InventoryService(int startingGold) {
        this.gold = startingGold;
        this.goldSpent = 0;
        this.items = new int[ItemType.values().length]; // one slot per enum
    }

    /**
     * Returns the player's current gold balance.
     *
     * @return current gold
     */
    public int getGold() {
        return gold;
    }

    /**
     * Deducts the given amount from the player's gold balance.
     *
     * <p>Gold may go negative if the balance is insufficient — callers should
     * use {@link #canAfford(int)} first if overdraft is undesirable.</p>
     *
     * @param amount the amount to deduct; must be &gt;= 0
     * @throws IllegalArgumentException if {@code amount} is negative
     */
    public void minusGold(int amount) {
        if (amount < 0) throw new IllegalArgumentException("Amount must be non-negative");
        gold -= amount;
    }

    /**
     * Adds the given amount to the player's gold balance.
     *
     * @param amount the amount of gold to add; must be &gt;= 0
     * @throws IllegalArgumentException if {@code amount} is negative
     */
    public void gainGold(int amount) {
        if (amount < 0) throw new IllegalArgumentException("Amount must be non-negative");
        gold += amount;
    }

    /**
     * Directly overwrites the inventory array with the provided values.
     *
     * <p>This is intended for save/load restoration. The provided array must have the
     * same length as the number of {@link ItemType} constants.</p>
     *
     * @param items the item counts to restore; must have length equal to
     *              {@code ItemType.values().length}
     * @throws IllegalArgumentException if {@code items.length} does not match
     *                                  the number of {@link ItemType} values
     */
    public void setItems(int[] items) {
        if (items.length != this.items.length) {
            throw new IllegalArgumentException("Items array length mismatch");
        }
        System.arraycopy(items, 0, this.items, 0, items.length);
    }

    /**
     * Returns the total amount of gold spent on items since this service was constructed.
     *
     * @return cumulative gold spent
     */
    public int getGoldSpent() {
        return goldSpent;
    }

    /**
     * Returns whether the player currently has enough gold to afford the given amount.
     *
     * @param amount the cost to check against the current gold balance
     * @return {@code true} if {@code gold >= amount}; {@code false} otherwise
     */
    public boolean canAfford(int amount) {
        return gold >= amount;
    }

    /**
     * Purchases one unit of the given item type, deducting its cost from the gold balance.
     * Equivalent to calling {@link #addItem(ItemType, int) addItem(type, 1)}.
     *
     * @param type the {@link ItemType} to purchase; must not be {@code null}
     * @throws IllegalArgumentException if the player cannot afford the item
     */
    public void addItem(ItemType type) {
        addItem(type, 1);
    }

    /**
     * Purchases the given quantity of an item type, deducting the total cost from gold
     * and incrementing the {@link #goldSpent} tracker.
     *
     * <p>The total cost is {@code type.getCost() * amount}. Both the gold balance
     * and the {@code goldSpent} counter are updated atomically within this call.</p>
     *
     * @param type   the {@link ItemType} to purchase; must not be {@code null}
     * @param amount the quantity to purchase; must be &gt;= 1
     * @throws IllegalArgumentException if {@code amount} is less than 1, or if the player
     *                                  does not have enough gold to cover the total cost
     */
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

    /**
     * Consumes the given quantity of an item from the inventory.
     *
     * <p>Does not affect gold or {@code goldSpent}.</p>
     *
     * @param type   the {@link ItemType} to consume; must not be {@code null}
     * @param amount the quantity to consume; must be &gt;= 1
     * @throws IllegalArgumentException if {@code amount} is less than 1, or if the inventory
     *                                  holds fewer than {@code amount} of the given item
     */
    public void useItem(ItemType type, int amount) {
        if (amount < 1) throw new IllegalArgumentException("Amount must be positive");
        int current = items[type.ordinal()];
        if (current < amount) throw new IllegalArgumentException("Not enough items: " + type.getName());
        items[type.ordinal()] -= amount;
    }

    /**
     * Returns the current quantity of the given item type in the inventory.
     *
     * @param type the {@link ItemType} to query; must not be {@code null}
     * @return the number of that item currently held (0 or more)
     */
    public int getItemCount(ItemType type) {
        return items[type.ordinal()];
    }

    /**
     * Returns a defensive copy of the raw inventory array for snapshot purposes.
     *
     * <p>Modifications to the returned array do not affect the internal inventory state.</p>
     *
     * @return a clone of the internal item count array, indexed by {@link ItemType#ordinal()}
     */
    public int[] getItems() {
        return items.clone();
    }

    /**
     * Restores the inventory from a list of item counts, typically loaded from a saved snapshot.
     *
     * <p>The list must have exactly one entry per {@link ItemType} constant, in ordinal order.</p>
     *
     * @param snapshot a list of item counts in {@link ItemType#ordinal()} order;
     *                 must have size equal to {@code ItemType.values().length}
     * @throws IllegalArgumentException if {@code snapshot.size()} does not match
     *                                  the number of {@link ItemType} values
     */
    public void loadItemsSnapshot(List<Integer> snapshot) {
        if (snapshot.size() != items.length) {
            throw new IllegalArgumentException("Snapshot size does not match item types");
        }
        for (int i = 0; i < items.length; i++) {
            items[i] = snapshot.get(i);
        }
    }
}