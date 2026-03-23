package com.github.domain;

import java.util.List;

public class CampaignStateManager {

    private final CampaignManager campaignManager;
    private final PartyService partyService;
    private final InventoryService InventoryService;
    private final Inn inn;

    public CampaignStateManager(CampaignManager campaignManager,
                                PartyService partyService,
                                InventoryService InventoryService,
                                Inn inn) {
        this.campaignManager = campaignManager;
        this.partyService = partyService;
        this.InventoryService = InventoryService;
        this.inn = inn;
    }

    /**
     * Restore campaign state from a snapshot
     */
    public void loadFromSnapshot(CampaignSnapshot snapshot) {
        if (snapshot == null) throw new IllegalArgumentException("Snapshot cannot be null");

        campaignManager.setBattleChance((float) snapshot.getBattleChance());
        while (campaignManager.getRoomCounter() < snapshot.getRoomCounter()) {
            campaignManager.nextRoom(); // ensures room sequence
        }

        InventoryService.gainGold(snapshot.getGold());
        InventoryService.setItems(snapshot.getItems());

        partyService.getParty().clear();
        for (HeroInstance hi : snapshot.getParty()) {
            partyService.addHero(hi.toHeroState()); // assumes HeroInstance -> HeroState
        }

        inn.clearRecruits();
        for (HeroInstance recruit : snapshot.getInnRecruits()) {
            inn.addRecruit(recruit.toHeroState());
        }
    }

    /**
     * Produce a snapshot of the current campaign
     */
    public CampaignSnapshot createSnapshot() {
        return new CampaignSnapshotImpl(
            campaignManager.getRoomCounter(),
            campaignManager.getBattleChance(),
            campaignManager.getCurrentRoom().toString(), // or serialize room ID
            InventoryService.getGold(),
            partyService.getTotalLevels(), // or EXP if needed
            HeroInstance.fromHeroStates(partyService.getParty()),
            HeroInstance.fromHeroStates(inn.viewRecruits()),
            InventoryService.getItems()
        );
    }
}