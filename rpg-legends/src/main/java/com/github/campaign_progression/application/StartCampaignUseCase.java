package com.github.campaign_progression.application;

import com.github.campaign_progression.domain.CampaignManager;
import com.github.campaign_progression.domain.InventoryService;
import com.github.campaign_progression.domain.PartyService;
import com.github.campaign_progression.domain.HeroState;
import com.github.campaign_progression.application.dto.StartCampaignDTO;
import com.github.campaign_progression.application.dto.HeroInstanceDTO;

public class StartCampaignUseCase {

    private final CampaignManager campaign;
    private final PartyService partyService;
    private final InventoryService inventoryService;

    public StartCampaignUseCase(CampaignManager campaign,
                                PartyService partyService,
                                InventoryService inventoryService) {
        this.campaign = campaign;
        this.partyService = partyService;
        this.inventoryService = inventoryService;
    }

    public StartCampaignDTO execute(HeroInstanceDTO startingHeroDTO) {

        // ✅ 1. Sanity checks
        if (startingHeroDTO == null) {
            throw new IllegalArgumentException("Starting hero cannot be null");
        }

        if (startingHeroDTO.getSpecialization() == null) {
            throw new IllegalArgumentException("Hero specialization is required");
        }

        if (startingHeroDTO.getLevel() <= 0) {
            throw new IllegalArgumentException("Hero level must be >= 1");
        }

        if (!partyService.hasSpace()) {
            throw new IllegalStateException("Party is full");
        }

        // ✅ 2. Convert DTO → Domain
        HeroState hero = mapToDomain(startingHeroDTO);

        // ✅ 3. Add to domain
        partyService.addHero(hero);

        // ⚠️ You had this — make sure it exists in domain
        // If NOT, remove this line
        // inventoryService.clearInventory();

        // ✅ 4. Start campaign
        campaign.startNewCampaign();

        // ✅ 5. Return DTO
        return StartCampaignDTO.fromDomain(campaign, partyService, inventoryService);
    }

    // 🔥 DTO → Domain mapper
    private HeroState mapToDomain(HeroInstanceDTO dto) {
        HeroState hero = new HeroState();

        // Basic fields
        hero.setSpecialization(dto.getSpecialization());

        // Level handling (based on your domain design)
        int level = dto.getLevel();

        switch (dto.getSpecialization()) {
            case "MAGE" -> hero.setMageLvl(level);
            case "WARRIOR" -> hero.setWarriorLvl(level);
            case "ORDER" -> hero.setOrderLvl(level);
            case "CHAOS" -> hero.setChaosLvl(level);
            default -> throw new IllegalArgumentException("Invalid specialization");
        }

        // Optional overrides (only if valid)
        if (dto.getMaxHp() > 0) {
            hero.setCurHp(Math.min(dto.getCurHp(), dto.getMaxHp()));
        }

        if (dto.getMaxMana() > 0) {
            hero.setCurMana(Math.min(dto.getCurMana(), dto.getMaxMana()));
        }

        return hero;
    }
}