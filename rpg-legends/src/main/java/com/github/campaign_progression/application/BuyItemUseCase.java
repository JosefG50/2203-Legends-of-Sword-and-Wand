package com.github.campaign_progression.application;

import com.github.campaign_progression.application.dto.BuyItemResponseDTO;
import com.github.campaign_progression.domain.Inn;
import com.github.campaign_progression.domain.InventoryService;
import com.github.campaign_progression.domain.ItemType;

public class BuyItemUseCase {

    private final Inn inn;
    private final InventoryService inventory;

    public BuyItemUseCase(Inn inn, InventoryService inventory) {
        this.inn = inn;
        this.inventory = inventory;
    }

    public BuyItemResponseDTO execute(ItemType type, int amount) {

        if (amount < 1) {
            return BuyItemResponseDTO.failure("Amount must be at least 1", type.getName());
        }

        if (inn.getShop() == null || inn.getShop().stream().noneMatch(i -> i == type)) {
    return BuyItemResponseDTO.failure(type.getName() + " not available in shop", type.getName());
}

        try {
            inventory.addItem(type, amount);
            return BuyItemResponseDTO.success(type.getName(), amount);
        } catch (IllegalArgumentException ex) {
            return BuyItemResponseDTO.failure(ex.getMessage(), type.getName());
        }
    }

    public BuyItemResponseDTO execute(ItemType type) {
        return execute(type, 1);
    }
}