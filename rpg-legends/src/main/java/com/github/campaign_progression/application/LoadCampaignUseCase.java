package com.github.campaign_progression.application;

import java.util.List;
import java.util.stream.Collectors;

import com.github.campaign_progression.application.dto.CampaignSnapshotDTO;
import com.github.campaign_progression.application.dto.HeroInstanceDTO;
import com.github.campaign_progression.application.dto.ItemDTO;
import com.github.campaign_progression.application.dto.LoadCampaignDTO;
import com.github.campaign_progression.domain.CampaignManager;
import com.github.campaign_progression.domain.HeroState;
import com.github.campaign_progression.domain.InventoryService;
import com.github.campaign_progression.domain.PartyService;
import com.github.campaign_progression.domain.Room;
import com.github.campaign_progression.domain.RoomFactory;

public class LoadCampaignUseCase {

    private final CampaignManager campaign;
    private final PartyService partyService;
    private final InventoryService inventoryService;
    private final RoomFactory roomFactory;

    public LoadCampaignUseCase(CampaignManager campaign,
                               PartyService partyService,
                               InventoryService inventoryService,
                               RoomFactory roomFactory) {
        this.campaign = campaign;
        this.partyService = partyService;
        this.inventoryService = inventoryService;
        this.roomFactory = roomFactory;
    }

public LoadCampaignDTO execute(CampaignSnapshotDTO snapshotDTO) {

    // 1️⃣ Restore campaign state
    campaign.setBattleChance(snapshotDTO.getBattleChance());
    campaign.setRoomCounter(snapshotDTO.getRoomCounter());
    campaign.setEndOfRoom(false);

    // 2️⃣ Restore room (safe)
    Room restoredRoom = roomFactory.createNextRoom(snapshotDTO.getBattleChance());
    campaign.setCurrentRoom(restoredRoom);

    // 3️⃣ Restore party (NO reset method exists → DO NOT duplicate logic)
    List<HeroState> party = snapshotDTO.getParty().stream()
            .map(this::toDomainHero)
            .collect(Collectors.toList());

    for (HeroState hero : party) {
        partyService.addHero(hero);
    }

    // 4️⃣ Restore inventory
    inventoryService.setItems(
            snapshotDTO.getItems().stream()
                    .mapToInt(ItemDTO::getQuantity)
                    .toArray()
    );

    // 5️⃣ Return DTO
    return LoadCampaignDTO.fromDomain(campaign, partyService, inventoryService);
}

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

        hero.setCurHp(Math.min(dto.getCurHp(), dto.getMaxHp()));
        hero.setCurMana(Math.min(dto.getCurMana(), dto.getMaxMana()));

        return hero;
    }
}