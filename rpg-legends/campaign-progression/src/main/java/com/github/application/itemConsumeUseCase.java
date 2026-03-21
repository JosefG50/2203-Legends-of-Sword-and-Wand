package com.github.application;

import com.github.domain.*;

public class ItemConsumeUseCase {

    private final InventoryService inventory;
    private final PartyService partyService;
    private final CampaignManager campaign;

    public ItemConsumeUseCase(InventoryService inventory, PartyService partyService, CampaignManager campaign) {
        this.inventory = inventory;
        this.partyService = partyService;
        this.campaign = campaign;
    }

    /**
     * Consume an item on a hero by index.
     *
     * @param itemType the item to use
     * @param heroIndex the index of the hero in the party
     * @return String describing the effect
     */
    public String execute(ItemType itemType, int heroIndex) {
        // 1️⃣ Can only activate after a room
        if (!campaignEndOfRoom()) {
            throw new IllegalStateException("You can only use items after finishing a room");
        }

        // 2️⃣ Validate hero index
        var party = partyService.getParty();
        if (heroIndex < 0 || heroIndex >= party.size()) {
            throw new IllegalArgumentException("Invalid hero index");
        }

        HeroState hero = party.get(heroIndex);

        // 3️⃣ Check inventory
        if (inventory.getItemCount(itemType) < 1) {
            throw new IllegalStateException("Not enough " + itemType.getName() + " to use");
        }

        // 4️⃣ Consume the item
        inventory.useItem(itemType, 1);

        // 5️⃣ Apply effects
        int hpBefore = hero.getHp();
        int manaBefore = hero.getMana();

        hero.restoreHp(itemType.getHpHeal());
        hero.restoreMana(itemType.getManaHeal());

        if (itemType.getCanRevive() && hero.getHp() == 0) {
            hero.restoreHp(hero.getMaxHp());
        }

        int hpRestored = hero.getHp() - hpBefore;
        int manaRestored = hero.getMana() - manaBefore;

        return String.format("%s used on %s: +%d HP, +%d Mana",
                itemType.getName(), hero.getName(), hpRestored, manaRestored);
    }

    /** Helper: checks if the current room is finished */
    private boolean campaignEndOfRoom() {
        // Assuming CampaignManager now has an 'endOfRoom' boolean flag
        // You said you wanted to add it
        return campaign.isEndOfRoom();
    }
}