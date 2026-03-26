package com.github.campaign_progression.application;

import com.github.campaign_progression.application.dto.GoldDTO;
import com.github.campaign_progression.domain.InventoryService;

/**
 * Use case for adding gold to the player's inventory.
 */
import org.springframework.stereotype.Service;

@Service
public class GainGoldUseCase {

    private final InventoryService inventoryService;

    /**
     * Constructs the use case with the given inventory service.
     *
     * @param inventoryService the domain service managing inventory and gold
     */
    public GainGoldUseCase(InventoryService inventoryService) {
        if (inventoryService == null) throw new IllegalArgumentException("InventoryService cannot be null");
        this.inventoryService = inventoryService;
    }

    /**
     * Adds gold to the player's inventory.
     *
     * @param amount the amount of gold to add; must be &gt;= 0
     * @return a GoldDTO representing the added gold and the new total gold
     * @throws IllegalArgumentException if {@code amount} is negative
     */
    public GoldDTO execute(int amount) {
        inventoryService.gainGold(amount);
        return new GoldDTO(amount, inventoryService.getGold());
    }
}