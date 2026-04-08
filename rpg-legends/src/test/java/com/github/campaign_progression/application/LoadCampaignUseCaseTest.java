package com.github.campaign_progression.application;

import com.github.campaign_progression.application.dto.CampaignSnapshotDTO;
import com.github.campaign_progression.application.dto.HeroInstanceDTO;
import com.github.campaign_progression.application.dto.ItemDTO;
import com.github.campaign_progression.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class LoadCampaignUseCaseTest {

    private CampaignManager campaign;
    private PartyService partyService;
    private InventoryService inventoryService;
    private RoomFactory roomFactory;
    private LoadCampaignUseCase useCase;

    @BeforeEach
    void setup() {
        campaign = mock(CampaignManager.class);
        partyService = new PartyService();
        inventoryService = mock(InventoryService.class);
        roomFactory = new RoomFactory();

        useCase = new LoadCampaignUseCase(
                campaign,
                partyService,
                inventoryService,
                roomFactory
        );
    }

    @Test
    void execute_restoresCampaignPartyInventory() {

        // Hero DTO
        HeroInstanceDTO heroDTO = new HeroInstanceDTO();
        heroDTO.setName("Alice");
        heroDTO.setLevel(3);
        heroDTO.setSpecialization("MAGE");
        heroDTO.setCurHp(50);
        heroDTO.setMaxHp(100);
        heroDTO.setCurMana(20);
        heroDTO.setMaxMana(50);

        // Item DTO
        ItemDTO potion = new ItemDTO("Potion", 100, 50, 0, false, 5);

        CampaignSnapshotDTO snapshot = new CampaignSnapshotDTO();
        snapshot.setBattleChance(0.7);
        snapshot.setRoomCounter(1);
        snapshot.setParty(List.of(heroDTO));
        snapshot.setItems(List.of(potion));

        // Execute
        var result = useCase.execute(snapshot);

        // Verify campaign updated
        verify(campaign).setBattleChance(0.7);
        verify(campaign).setRoomCounter(1);
        verify(campaign).setEndOfRoom(false);

        // Verify party restored
        List<HeroState> party = partyService.getParty();
        assertEquals(1, party.size());

        HeroState restored = party.get(0);
        assertEquals("Alice", restored.getName());
        assertEquals("MAGE", restored.getSpecialization());
        assertEquals(50, restored.getCurHp());
        assertEquals(20, restored.getCurMana());

        // Verify inventory
        verify(inventoryService).setItems(argThat(arr ->
                arr.length == 1 && arr[0] == 5
        ));
    }
}