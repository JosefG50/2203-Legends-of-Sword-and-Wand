package com.github.campaign_progression.application;

import com.github.campaign_progression.application.dto.ItemConsumeResponseDTO;
import com.github.campaign_progression.domain.*;

import java.util.List;

/**
 * Use case for consuming an item on a specific hero.
 *
 * <p>Applies the effects of the {@link ItemType} to the target hero,
 * updates the inventory, and returns a response containing the amount
 * of HP/mana restored and whether the hero was revived.</p>
 */
public class ItemConsumeUseCase {

    private final InventoryService inventory;
    private final PartyService partyService;
    private final CampaignManager campaign;

    /**
     * Constructs the use case.
     *
     * @param inventory    the inventory service managing items
     * @param partyService the party service managing heroes
     * @param campaign     the campaign manager to check room status
     */
    public ItemConsumeUseCase(InventoryService inventory, PartyService partyService, CampaignManager campaign) {
        this.inventory = inventory;
        this.partyService = partyService;
        this.campaign = campaign;
    }

    /**
     * Consumes the given item on the hero at the specified index.
     *
     * @param itemType  the item to use
     * @param heroIndex index of the hero in the party
     * @return {@link ItemConsumeResponseDTO} describing the effects
     * @throws IllegalStateException    if the room is not finished or not enough items
     * @throws IllegalArgumentException if the hero index is invalid
     */
    public ItemConsumeResponseDTO execute(ItemType itemType, int heroIndex) {
        if (!campaign.isEndOfRoom()) {
            throw new IllegalStateException("You can only use items after finishing a room");
        }

        List<HeroState> party = partyService.getParty();
        if (heroIndex < 0 || heroIndex >= party.size()) {
            throw new IllegalArgumentException("Invalid hero index");
        }

        HeroState hero = party.get(heroIndex);

        if (inventory.getItemCount(itemType) < 1) {
            throw new IllegalStateException("Not enough " + itemType.getName() + " to use");
        }

        inventory.useItem(itemType, 1);

        int hpBefore = hero.getCurHp();
        int manaBefore = hero.getCurMana();
        boolean revived = false;

        hero.gainCurHp(itemType.getHpHeal());
        hero.gainCurMana(itemType.getManaHeal());

        if (itemType.canRevive() && hero.getCurHp() == 0) {
            hero.gainCurHp(hero.getMaxHp());
            revived = true;
        }

        int hpRestored = hero.getCurHp() - hpBefore;
        int manaRestored = hero.getCurMana() - manaBefore;

        return new ItemConsumeResponseDTO(
                hero.getName(),
                itemType.getName(),
                hpRestored,
                manaRestored,
                revived
        );
    }
}