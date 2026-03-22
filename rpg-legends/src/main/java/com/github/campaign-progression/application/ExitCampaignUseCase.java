package com.github.application;

import com.github.domain.*;

import java.util.List;
import java.util.stream.Collectors;

public class ExitCampaignUseCase {

    private final CampaignManager campaign;
    private final PartyService partyService;
    private final InventoryService inventory;
    private final Inn inn;

    public ExitCampaignUseCase(CampaignManager campaign,
            PartyService partyService,
            InventoryService inventory,
            Inn inn) {
        this.campaign = campaign;
        this.partyService = partyService;
        this.inventory = inventory;
        this.inn = inn;
    }

    /**
     * Creates a snapshot of the current campaign state for persistence.
     *
     * @return CampaignSnapshotDTO containing full campaign state
     */
    public CampaignSnapshotDTO execute() {

        List<HeroInstanceDTO> partyDTO = partyService.getParty().stream()
                .map(HeroInstanceDTO::fromHeroState)
                .collect(Collectors.toList());

        List<HeroInstanceDTO> innDTO = inn.viewRecruits().values().stream()
                .map(HeroInstanceDTO::fromHeroState)
                .collect(Collectors.toList());

        List<ItemDTO> itemsDTO = inventory.getItemsSnapshot().stream()
                .map(ItemDTO::fromItemCount)
                .collect(Collectors.toList());

        CampaignSnapshotDTO snapshot = new CampaignSnapshotDTO();
        snapshot.setRoomCounter(campaign.getRoomCounter());
        snapshot.setBattleChance(campaign.getBattleChance());
        snapshot.setCurRoom(campaign.getCurrentRoom().getClass().getSimpleName());
        snapshot.setGold(inventory.getGold());
        snapshot.setExp(partyService.getTotalLevels()); // or separate exp if tracked
        snapshot.setParty(partyDTO);
        snapshot.setInnRecruits(innDTO);
        snapshot.setItems(itemsDTO);

        return snapshot;
    }

    public CampaignEndDTO executeEndCampaign() {
        int totalLevels = partyService.getTotalLevels();

        // Use domain Score class for calculation
        Score scoreCalculator = new Score();
        int totalScore = scoreCalculator.calculateScore(goldSpent, totalLevels, extraGold);

        List<HeroSummaryDTO> partySummary = partyService.getParty().stream()
                .map(hero -> new HeroSummaryDTO(
                        hero.getName(),
                        hero.getSpecialization(),
                        hero.getTotalLevel(),
                        hero.getHp(),
                        hero.getMana()))
                .collect(Collectors.toList());

        String message = "Campaign complete! Your party's performance has been summarized.";

        return new CampaignEndDTO(partySummary, totalScore, message);
    }
}