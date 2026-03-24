package com.github.campaign_progression.application;

import java.util.Optional;

import com.github.campaign_progression.application.dto.BuyRecruitResponseDTO;
import com.github.campaign_progression.domain.HeroState;
import com.github.campaign_progression.domain.Inn;
import com.github.campaign_progression.domain.InventoryService;
import com.github.campaign_progression.domain.PartyService;


public class BuyRecruitUseCase {

    private final Inn inn;
private final PartyService partyService;
private final InventoryService inventoryService;

public BuyRecruitUseCase(Inn inn, PartyService partyService, InventoryService inventoryService) {
    this.inn = inn;
    this.partyService = partyService;
    this.inventoryService = inventoryService;
}

public BuyRecruitResponseDTO execute(int roomCounter) {

    if (roomCounter > 10) {
        return new BuyRecruitResponseDTO(
                false,
                "Recruits are no longer available after room 10",
                null, null, 0
        );
    }

    if (!partyService.hasSpace()) {
        return new BuyRecruitResponseDTO(
                false,
                "Party is full. Cannot recruit more heroes.",
                null, null, 0
        );
    }

    Optional<HeroState> recruitOpt = inn.generateRecruit(partyService, roomCounter);

    if (recruitOpt.isEmpty()) {
        return new BuyRecruitResponseDTO(
                false,
                "No recruit available",
                null, null, 0
        );
    }

    HeroState hero = recruitOpt.get();

    int level = hero.getLevel(); // ✅ FIXED
    int cost = (level == 1) ? 0 : level * 200;

    // 💰 Check gold
    if (!inventoryService.canAfford(cost)) {
        return new BuyRecruitResponseDTO(
                false,
                "Not enough gold to recruit hero. Cost: " + cost,
                null, null, 0
        );
    }

    try {
        // 💰 Deduct gold (manual since no domain method exists)
        if (cost > 0) {
            inventoryService.minusGold(cost);
        }

        partyService.addHero(hero);

        return new BuyRecruitResponseDTO(
                true,
                "Recruit successful (Cost: " + cost + ")",
                hero.getName(),
                hero.getSpecialization(),
                level
        );

    } catch (IllegalStateException ex) {
        return new BuyRecruitResponseDTO(
                false,
                ex.getMessage(),
                null, null, 0
        );
    }
}
}