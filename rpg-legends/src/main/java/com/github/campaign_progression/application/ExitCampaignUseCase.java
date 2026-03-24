package com.github.campaign_progression.application;

import com.github.campaign_progression.domain.*;
import com.github.campaign_progression.application.dto.*;

import java.util.List;
import java.util.stream.Collectors;

public class ExitCampaignUseCase {

    private final CampaignManager campaign;
    private final PartyService partyService;
    private final InventoryService inventory;

    public ExitCampaignUseCase(CampaignManager campaign,
                               PartyService partyService,
                               InventoryService inventory,
                               Inn inn) {
        this.campaign = campaign;
        this.partyService = partyService;
        this.inventory = inventory;
    }

    public CampaignSnapshotDTO execute() {

        // ✅ Party
        List<HeroInstanceDTO> partyDTO = partyService.getParty().stream()
                .map(HeroInstanceDTO::fromDomain)
                .collect(Collectors.toList());


        // ✅ Inventory (no snapshot method → rebuild from ItemType)
        List<ItemDTO> itemsDTO = new java.util.ArrayList<>();
        for (ItemType type : ItemType.values()) {
            int quantity = inventory.getItemCount(type);

            if (quantity > 0) {
                itemsDTO.add(ItemDTO.fromDomain(type, quantity));
            }
        }

        // ✅ Build snapshot DTO
        CampaignSnapshotDTO snapshot = new CampaignSnapshotDTO();
        snapshot.setRoomCounter(campaign.getRoomCounter());
        snapshot.setBattleChance(campaign.getBattleChance());
        snapshot.setCurRoom(campaign.getCurrentRoom().getClass().getSimpleName());
        snapshot.setGold(inventory.getGold());
        snapshot.setExp(partyService.getTotalLevels());
        snapshot.setParty(partyDTO);
        snapshot.setItems(itemsDTO);

        return snapshot;
    }
}