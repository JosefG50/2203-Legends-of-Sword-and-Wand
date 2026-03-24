package com.github.campaign_progression.application;

import com.github.campaign_progression.domain.*;
import com.github.campaign_progression.application.dto.*;

import java.util.List;
import java.util.stream.Collectors;

public class LoadCampaignUseCase {

    private final CampaignManager campaign;
    private final PartyService partyService;
    private final InventoryService inventoryService;

    public LoadCampaignUseCase(CampaignManager campaign,
                               PartyService partyService,
                               InventoryService inventoryService,
                               Inn inn) {
        this.campaign = campaign;
        this.partyService = partyService;
        this.inventoryService = inventoryService;
    }

    public LoadCampaignDTO execute(CampaignSnapshotDTO snapshotDTO) {

        // ✅ 1. Restore campaign state
        campaign.setBattleChance(snapshotDTO.getBattleChance());

        // If you have a setter or method for roomCounter in domain, use it
        // Otherwise this may need a domain method like loadFromSnapshot(...)
        // campaign.setRoomCounter(snapshotDTO.getRoomCounter());

        // ⚠️ Room must be reconstructed via factory or nextRoom logic
        // (depends on your domain design)

        // ✅ 2. Restore party
        List<HeroState> party = snapshotDTO.getParty().stream()
                .map(this::toDomainHero)
                .collect(Collectors.toList());

        // Clear existing party and rebuild
        for (HeroState hero : party) {
            partyService.addHero(hero);
        }

        // ✅ 3. Restore inventory
        inventoryService.setItems(
                snapshotDTO.getItems().stream()
                        .mapToInt(ItemDTO::getQuantity)
                        .toArray()
        );

        // Optional: restore gold
        // inventoryService.setGold(snapshotDTO.getGold());



        // ✅ 5. Return updated DTO
        return LoadCampaignDTO.fromDomain(campaign, partyService, inventoryService);
    }

    // 🔥 DTO → Domain mapping
    private HeroState toDomainHero(HeroInstanceDTO dto) {
        HeroState hero = new HeroState();

        hero.setSpecialization(dto.getSpecialization());

        int level = dto.getLevel();

        switch (dto.getSpecialization()) {
            case "MAGE" -> hero.setMageLvl(level);
            case "WARRIOR" -> hero.setWarriorLvl(level);
            case "ORDER" -> hero.setOrderLvl(level);
            case "CHAOS" -> hero.setChaosLvl(level);
            default -> throw new IllegalArgumentException("Invalid specialization");
        }

        // Restore stats (clamped safely)
        hero.setCurHp(Math.min(dto.getCurHp(), dto.getMaxHp()));
        hero.setCurMana(Math.min(dto.getCurMana(), dto.getMaxMana()));

        return hero;
    }
}