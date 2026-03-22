public class LoadCampaignUseCase {

    private final CampaignManager campaign;
    private final PartyService partyService;
    private final InventoryService inventoryService;

    public LoadCampaignUseCase(CampaignManager campaign,
                               PartyService partyService,
                               InventoryService inventoryService) {
        this.campaign = campaign;
        this.partyService = partyService;
        this.inventoryService = inventoryService;
    }

    public LoadCampaignDTO execute(CampaignSnapshot snapshot) {
        campaign.loadFromSnapshot(snapshot);
        partyService.loadFromSnapshot(snapshot.getParty());
        inventoryService.loadItemsSnapshot(snapshot.getItems());
        return LoadCampaignDTO.fromDomain(campaign, partyService, inventoryService);
    }
}