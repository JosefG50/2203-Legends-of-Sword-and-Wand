package com.github.campaign_progression.application;

import com.github.campaign_progression.application.dto.ItemDTO;
import com.github.campaign_progression.domain.InventoryService;
import com.github.campaign_progression.domain.ItemType;

import java.util.ArrayList;
import java.util.List;

public class GetInventoryUseCase {

    private final InventoryService inventoryService;

    public GetInventoryUseCase(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public List<ItemDTO> execute() {
        List<ItemDTO> result = new ArrayList<>();

        for (ItemType type : ItemType.values()) {
            int count = inventoryService.getItemCount(type);
            result.add(ItemDTO.fromDomain(type, count));
        }

        return result;
    }
}