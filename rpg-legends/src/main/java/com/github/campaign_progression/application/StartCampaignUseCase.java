public class StartCampaignUseCase {

    private final CampaignManager campaign;
    private final PartyService partyService;
    private final InventoryService inventoryService;

    public StartCampaignUseCase(CampaignManager campaign, PartyService partyService,
                                InventoryService inventoryService) {
        this.campaign = campaign;
        this.partyService = partyService;
        this.inventoryService = inventoryService;
    }

    public StartCampaignDTO execute(HeroInstance startingHero) {
        partyService.addHero(startingHero.toHeroState()); // convert DTO → domain
        inventoryService.clearInventory(); // start empty
        campaign.startNewCampaign(); // maybe sets roomCounter=1, currentRoom
        return StartCampaignDTO.fromDomain(campaign, partyService, inventoryService);
    }
}