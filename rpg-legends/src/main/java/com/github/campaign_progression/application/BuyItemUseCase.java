package com.github.campaign_progression.application;

import com.github.campaign_progression.domain.Inn;
import com.github.campaign_progression.domain.InventoryService;
import com.github.campaign_progression.domain.ItemType;
import com.github.campaign_progression.application.dto.BuyItemResponseDTO;

public class BuyItemUseCase {

    private final Inn inn;
    private final InventoryService inventory;

    public BuyItemUseCase(Inn inn, InventoryService inventory) {
        this.inn = inn;
        this.inventory = inventory;
    }

    public BuyItemResponseDTO execute(ItemType type, int amount) {

        if (amount < 1) {
            return new BuyItemResponseDTO(
                    false,
                    "Amount must be at least 1",
                    type.getName(),
                    0,
                    inventory.getGold()
            );
        }

        boolean available = inn.getShop().contains(type);
        if (!available) {
            return new BuyItemResponseDTO(
                    false,
                    type.getName() + " is not available in the inn shop",
                    type.getName(),
                    0,
                    inventory.getGold()
            );
        }

        try {
            inventory.addItem(type, amount);

            return new BuyItemResponseDTO(
                    true,
                    "Purchase successful",
                    type.getName(),
                    amount,
                    inventory.getGold()
            );

        } catch (IllegalArgumentException ex) {

            return new BuyItemResponseDTO(
                    false,
                    ex.getMessage(),
                    type.getName(),
                    0,
                    inventory.getGold()
            );
        }
    }

    public BuyItemResponseDTO execute(ItemType type) {
        return execute(type, 1);
    }
}