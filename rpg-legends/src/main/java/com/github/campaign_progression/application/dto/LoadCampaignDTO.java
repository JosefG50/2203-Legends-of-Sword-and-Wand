package com.github.campaign_progression.application.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.github.campaign_progression.domain.*;

public class LoadCampaignDTO {
    String statusMessage;  // "Campaign loaded"
    List<HeroInstanceDTO> party;
    List<ItemDTO> inventory;
    String currentRoom;

    public static LoadCampaignDTO fromDomain(CampaignManager campaign,
                                         PartyService partyService,
                                         InventoryService inventoryService) {

    LoadCampaignDTO dto = new LoadCampaignDTO();
    dto.statusMessage = "Campaign loaded";

    // ✅ Party
    dto.party = partyService.getParty().stream()
            .map(HeroInstanceDTO::fromDomain)
            .collect(Collectors.toList());

    // ✅ Inventory (no snapshot map — we rebuild from ItemType)
    dto.inventory = new java.util.ArrayList<>();

    for (ItemType type : ItemType.values()) {
        int quantity = inventoryService.getItemCount(type);

        if (quantity > 0) {
            dto.inventory.add(ItemDTO.fromDomain(type, quantity));
        }
    }

    // ✅ Room
    dto.currentRoom = campaign.getCurrentRoom().getClass().getSimpleName();

    return dto;
}
    
}
