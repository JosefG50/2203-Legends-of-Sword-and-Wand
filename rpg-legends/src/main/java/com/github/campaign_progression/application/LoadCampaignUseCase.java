package com.github.campaign_progression.application;

import com.github.campaign_progression.application.dto.HeroInstanceDTO;
import com.github.campaign_progression.application.dto.ItemDTO;
import com.github.campaign_progression.application.dto.LoadCampaignDTO;
import com.github.campaign_progression.application.dto.CampaignSnapshotDTO;
import com.github.campaign_progression.domain.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Use case for loading a saved campaign snapshot into the domain model.
 *
 * <p>Restores:</p>
 * <ul>
 *     <li>Campaign state (battle chance, room counter if applicable)</li>
 *     <li>Party members with their stats and specializations</li>
 *     <li>Inventory items and quantities</li>
 * </ul>
 *
 * <p>Returns a {@link LoadCampaignDTO} reflecting the restored state for UI or further processing.</p>
 */
public class LoadCampaignUseCase {

    private final CampaignManager campaign;
    private final PartyService partyService;
    private final InventoryService inventoryService;

    /**
     * Constructs the use case.
     *
     * @param campaign         the campaign manager to restore state
     * @param partyService     the party service to rebuild heroes
     * @param inventoryService the inventory service to restore items
     * @param inn              the inn (optional, not used in this version)
     */
    public LoadCampaignUseCase(CampaignManager campaign,
                               PartyService partyService,
                               InventoryService inventoryService,
                               Inn inn) {
        this.campaign = campaign;
        this.partyService = partyService;
        this.inventoryService = inventoryService;
    }

    /**
     * Loads a saved campaign snapshot into the domain.
     *
     * @param snapshotDTO the snapshot DTO containing saved state
     * @return a {@link LoadCampaignDTO} reflecting the restored campaign
     */
    public LoadCampaignDTO execute(CampaignSnapshotDTO snapshotDTO) {

        // 1️⃣ Restore campaign state
        campaign.setBattleChance(snapshotDTO.getBattleChance());

        // 2️⃣ Restore party
        List<HeroState> party = snapshotDTO.getParty().stream()
                .map(this::toDomainHero)
                .collect(Collectors.toList());

        for (HeroState hero : party) {
            partyService.addHero(hero);
        }

        // 3️⃣ Restore inventory
        inventoryService.setItems(
                snapshotDTO.getItems().stream()
                        .mapToInt(ItemDTO::getQuantity)
                        .toArray()
        );

        // Optional: restore gold
        // inventoryService.setGold(snapshotDTO.getGold());

        // 4️⃣ Return updated DTO
        return LoadCampaignDTO.fromDomain(campaign, partyService, inventoryService);
    }

    /**
     * Maps a {@link HeroInstanceDTO} to a {@link HeroState}.
     *
     * @param dto the hero snapshot DTO
     * @return a restored {@link HeroState} object
     */
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