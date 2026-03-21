package com.github.application;

import com.github.domain.Inn;
import com.github.domain.InventoryService;
import com.github.domain.ItemType;

import java.util.Optional;

public class BuyItemUseCase {

    private final Inn inn;
    private final InventoryService inventory;

    public BuyItemUseCase(Inn inn, InventoryService inventory) {
        this.inn = inn;
        this.inventory = inventory;
    }

    /**
     * Attempt to buy an item from the inn's shop.
     *
     * @param type   The ItemType to purchase
     * @param amount How many units to buy
     * @return Optional<String> containing error message if failed, empty if successful
     */
    public Optional<String> execute(ItemType type, int amount) {
        if (amount < 1) return Optional.of("Amount must be at least 1");

        // Check if the item exists in the inn shop
        boolean available = inn.getShop().contains(type);
        if (!available) return Optional.of(type.getName() + " is not available in the inn shop");

        try {
            inventory.addItem(type, amount);
            return Optional.empty(); // purchase successful
        } catch (IllegalArgumentException ex) {
            return Optional.of(ex.getMessage()); // purchase failed
        }
    }

    /** Convenience method to buy a single unit */
    public Optional<String> execute(ItemType type) {
        return execute(type, 1);
    }
}