public class StartCampaignDTO {
    private String statusMessage; // "Campaign started"
    private List<HeroInstanceDTO> party;
    private List<ItemDTO> inventory;
    private String currentRoom;

    public static StartCampaignDTO fromDomain(CampaignManager campaign,
                                              PartyService partyService,
                                              InventoryService inventoryService) {
        StartCampaignDTO dto = new StartCampaignDTO();
        dto.statusMessage = "Campaign started";
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