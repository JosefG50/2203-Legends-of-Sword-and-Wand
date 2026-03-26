package com.github.campaign_progression.application;

import com.github.campaign_progression.application.dto.ItemDTO;
import com.github.campaign_progression.domain.InventoryService;
import com.github.campaign_progression.domain.ItemType;

import java.util.ArrayList;
import java.util.List;

/**
 * Use case for retrieving the current inventory as a list of {@link ItemDTO}.
 *
 * <p>Converts the domain representation (item counts by {@link ItemType})
 * into DTOs suitable for front-end display or API responses.</p>
 */
public class GetInventoryUseCase {

    private final InventoryService inventoryService;

    /**
     * Constructs the use case with the provided inventory service.
     *
     * @param inventoryService the domain inventory service
     */
    public GetInventoryUseCase(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    /**
     * Returns the current inventory as a list of {@link ItemDTO}.
     *
     * @return a list of {@link ItemDTO}, one per {@link ItemType}
     */
    public List<ItemDTO> execute() {
        int[] counts = inventoryService.getItems();
        ItemType[] types = ItemType.values();
        List<ItemDTO> result = new ArrayList<>();
        for (int i = 0; i < counts.length; i++) {
            result.add(ItemDTO.fromDomain(types[i], counts[i]));
        }
        return result;
    }
}