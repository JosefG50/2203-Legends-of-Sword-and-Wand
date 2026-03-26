package com.github.campaign_progression.application;

import com.github.campaign_progression.domain.CampaignManager;
import com.github.campaign_progression.domain.InventoryService;
import com.github.campaign_progression.domain.PartyService;
import com.github.campaign_progression.domain.HeroState;
import com.github.campaign_progression.application.dto.StartCampaignDTO;
import com.github.campaign_progression.application.dto.HeroInstanceDTO;

/**
 * Use case for starting a new campaign with a single starting hero.
 *
 * <p>This use case:</p>
 * <ul>
 *     <li>Validates the starting hero DTO.</li>
 *     <li>Maps the DTO to a domain {@link HeroState} object.</li>
 *     <li>Adds the hero to the {@link PartyService}.</li>
 *     <li>Starts a new campaign in the {@link CampaignManager}.</li>
 *     <li>Returns a {@link StartCampaignDTO} representing the initial campaign state.</li>
 * </ul>
 */
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

    /**
     * Starts a new campaign with the given starting hero.
     *
     * @param startingHeroDTO the hero to add to the party; must not be null
     * @return a DTO representing the initial state of the campaign
     */
    public StartCampaignDTO execute(HeroInstanceDTO startingHeroDTO) {

        // 1️⃣ Sanity checks
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

        // 2️⃣ Map DTO → domain
        HeroState hero = mapToDomain(startingHeroDTO);

        // 3️⃣ Add hero to party
        partyService.addHero(hero);

        // 4️⃣ Start campaign
        campaign.startNewCampaign();

        // 5️⃣ Return DTO representing the initial state
        return StartCampaignDTO.fromDomain(campaign, partyService, inventoryService);
    }

    /**
     * Maps a {@link HeroInstanceDTO} to a domain {@link HeroState}.
     *
     * @param dto the DTO to map
     * @return a new {@link HeroState} instance
     */
    private HeroState mapToDomain(HeroInstanceDTO dto) {
        HeroState hero = new HeroState();
        hero.setSpecialization(dto.getSpecialization());

        int level = dto.getLevel();

        // Set sub-level based on specialization
        switch (dto.getSpecialization()) {
            case "MAGE" -> hero.setMageLvl(level);
            case "WARRIOR" -> hero.setWarriorLvl(level);
            case "ORDER" -> hero.setOrderLvl(level);
            case "CHAOS" -> hero.setChaosLvl(level);
            default -> throw new IllegalArgumentException("Invalid specialization");
        }

        // Set HP / Mana safely
        if (dto.getMaxHp() > 0) hero.setCurHp(Math.min(dto.getCurHp(), dto.getMaxHp()));
        if (dto.getMaxMana() > 0) hero.setCurMana(Math.min(dto.getCurMana(), dto.getMaxMana()));

        return hero;
    }
}