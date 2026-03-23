package com.github.campaign-progression.application.dto;

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
        dto.party = partyService.getParty().stream()
                        .map(HeroInstanceDTO::fromHeroState)
                        .collect(Collectors.toList());
        dto.inventory = inventoryService.getItemsSnapshot().stream()
                        .map(ItemDTO::fromItemCount)
                        .collect(Collectors.toList());
        dto.currentRoom = campaign.getCurrentRoom().getClass().getSimpleName();
        return dto;
    }
    
}
