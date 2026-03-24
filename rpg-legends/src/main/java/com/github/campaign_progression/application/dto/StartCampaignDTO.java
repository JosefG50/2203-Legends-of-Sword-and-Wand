package com.github.campaign_progression.application.dto;

import com.github.campaign_progression.domain.CampaignManager;
import com.github.campaign_progression.domain.PartyService;
import com.github.campaign_progression.domain.InventoryService;
import com.github.campaign_progression.domain.ItemType;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class StartCampaignDTO {

    private String statusMessage;
    private List<HeroInstanceDTO> party;
    private List<ItemDTO> inventory;
    private String currentRoom;

    public static StartCampaignDTO fromDomain(CampaignManager campaign,
                                              PartyService partyService,
                                              InventoryService inventoryService) {

        StartCampaignDTO dto = new StartCampaignDTO();
        dto.statusMessage = "Campaign started";

        // ✅ Party mapping
        dto.party = partyService.getParty().stream()
                .map(HeroInstanceDTO::fromDomain) // ✅ FIXED
                .collect(Collectors.toList());

        // ✅ Inventory mapping (based on your domain)
        List<ItemDTO> items = new ArrayList<>();
        for (ItemType type : ItemType.values()) {
            int quantity = inventoryService.getItemCount(type);

            if (quantity > 0) { // optional: skip empty items
                items.add(ItemDTO.fromDomain(type, quantity));
            }
        }
        dto.inventory = items;

        // ✅ Room name
        dto.currentRoom = campaign.getCurrentRoom().getClass().getSimpleName();

        return dto;
    }

    // Getters
    public String getStatusMessage() { return statusMessage; }
    public List<HeroInstanceDTO> getParty() { return party; }
    public List<ItemDTO> getInventory() { return inventory; }
    public String getCurrentRoom() { return currentRoom; }
}