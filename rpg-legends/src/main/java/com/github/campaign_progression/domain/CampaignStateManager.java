package com.github.campaign_progression.domain;

public class CampaignStateManager {

    private final CampaignManager campaignManager;
    private final PartyService partyService;
    private final InventoryService InventoryService;

    public CampaignStateManager(CampaignManager campaignManager,
                                PartyService partyService,
                                InventoryService InventoryService,
                                Inn inn) {
        this.campaignManager = campaignManager;
        this.partyService = partyService;
        this.InventoryService = InventoryService;
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
        for (HeroState hi : snapshot.getParty()) {
            partyService.addHero(hi); // assumes HeroState -> HeroState
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
            partyService.getParty(),
            InventoryService.getItems()
        );
    }
}