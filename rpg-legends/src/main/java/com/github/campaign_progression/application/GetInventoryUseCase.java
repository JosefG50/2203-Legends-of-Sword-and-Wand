package com.github.campaign_progression.application;

import com.github.campaign_progression.domain.InventoryService;
import com.github.campaign_progression.domain.ItemType;
import com.github.campaign_progression.application.dto.ItemDTO;
import java.util.ArrayList;
import java.util.List;

public class GetInventoryUseCase {

    private final InventoryService inventoryService;

    public GetInventoryUseCase(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

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