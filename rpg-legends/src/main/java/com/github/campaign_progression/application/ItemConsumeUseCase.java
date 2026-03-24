package com.github.campaign_progression.application;

import com.github.campaign_progression.domain.*;
import com.github.campaign_progression.application.dto.ItemConsumeResponseDTO;

import java.util.List;

public class ItemConsumeUseCase {

    private final InventoryService inventory;
    private final PartyService partyService;
    private final CampaignManager campaign;

    public ItemConsumeUseCase(InventoryService inventory, PartyService partyService, CampaignManager campaign) {
        this.inventory = inventory;
        this.partyService = partyService;
        this.campaign = campaign;
    }

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