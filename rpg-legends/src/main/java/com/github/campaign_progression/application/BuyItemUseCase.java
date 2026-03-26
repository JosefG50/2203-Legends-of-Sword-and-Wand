package com.github.campaign_progression.application;

import com.github.campaign_progression.domain.*;
import com.github.campaign_progression.application.dto.BuyItemResponseDTO;

public class BuyItemUseCase {

    private final Inn inn;
    private final InventoryService inventory;

    public BuyItemUseCase(Inn inn, InventoryService inventory) {
        this.inn = inn;
        this.inventory = inventory;
    }

    /**
     * Buys the given amount of the specified item from the inn's shop.
     *
     * @param type   the item type to buy
     * @param amount how many to buy (defaults to 1)
     * @return a DTO indicating success/failure
     */
    public BuyItemResponseDTO execute(ItemType type, int amount) {
        if (amount < 1) {
            return BuyItemResponseDTO.failure("Amount must be at least 1", type.getName());
        }

        if (!inn.getShop().contains(type)) {
            return BuyItemResponseDTO.failure(type.getName() + " not available in shop", type.getName());
        }

        try {
            inventory.addItem(type, amount);
            return BuyItemResponseDTO.success(type.getName(), amount);
        } catch (IllegalArgumentException ex) {
            return BuyItemResponseDTO.failure(ex.getMessage(), type.getName());
        }
    }

    /** Overload: default amount = 1 */
    public BuyItemResponseDTO execute(ItemType type) {
        return execute(type, 1);
    }
}