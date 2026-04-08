package com.github.campaign_progression.application;

import com.github.campaign_progression.application.dto.CampaignSnapshotDTO;
import com.github.campaign_progression.application.dto.HeroInstanceDTO;
import com.github.campaign_progression.application.dto.ItemDTO;
import com.github.campaign_progression.domain.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Use case responsible for capturing a full snapshot of the current campaign state.
 *
 * <p>Gathers information from multiple domain objects:
 * <ul>
 *     <li>Party members and their stats</li>
 *     <li>Inventory items</li>
 *     <li>Current room, battle chance, gold, and total experience</li>
 * </ul>
 * </p>
 *
 * <p>The result is returned as a {@link CampaignSnapshotDTO} for front-end or persistence use.</p>
 */
public class ExitCampaignUseCase {

    private final CampaignService campaign;
    private final PartyService partyService;
    private final InventoryService inventory;

    /**
     * Constructs the use case with required domain dependencies.
     *
     * @param campaign the active campaign manager
     * @param partyService the party manager
     * @param inventory the inventory service
     * @param inn (ignored, can be removed if unused)
     */
    public ExitCampaignUseCase(CampaignService campaign,
                               PartyService partyService,
                               InventoryService inventory,
                               Inn inn) {
        this.campaign = campaign;
        this.partyService = partyService;
        this.inventory = inventory;
    }

    /**
     * Produces a snapshot of the current campaign state.
     *
     * @return a {@link CampaignSnapshotDTO} containing party, inventory, room info, gold, and experience
     */
    public CampaignSnapshotDTO execute() {

        // Party DTO
        List<HeroInstanceDTO> partyDTO = partyService.getParty().stream()
                .map(HeroInstanceDTO::fromDomain)
                .collect(Collectors.toList());

        // Inventory DTO
        List<ItemDTO> itemsDTO = new java.util.ArrayList<>();
        for (ItemType type : ItemType.values()) {
            int quantity = inventory.getItemCount(type);
            if (quantity > 0) {
                itemsDTO.add(ItemDTO.fromDomain(type, quantity));
            }
        }

        // Build snapshot DTO
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