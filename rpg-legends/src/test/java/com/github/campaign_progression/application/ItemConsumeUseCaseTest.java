package com.github.campaign_progression.application;

import com.github.campaign_progression.application.dto.ItemConsumeResponseDTO;
import com.github.campaign_progression.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ItemConsumeUseCaseTest {

    private InventoryService inventory;
    private PartyService partyService;
    private CampaignService campaign;
    private ItemConsumeUseCase useCase;
    private HeroState hero;
    private ItemType potion;

    @BeforeEach
    void setup() {
        inventory = mock(InventoryService.class);
        partyService = mock(PartyService.class);
        campaign = mock(CampaignService.class);

        hero = mock(HeroState.class);
        when(hero.getName()).thenReturn("Alice");
        when(hero.getCurHp()).thenReturn(50);
        when(hero.getCurMana()).thenReturn(10);
        when(hero.getMaxHp()).thenReturn(100);
        when(hero.getMaxMana()).thenReturn(50);

        when(partyService.getParty()).thenReturn(List.of(hero));

        potion = mock(ItemType.class);
        when(potion.getName()).thenReturn("Health Potion");
        when(potion.getHpHeal()).thenReturn(30);
        when(potion.getManaHeal()).thenReturn(5);
        when(potion.canRevive()).thenReturn(false);

        useCase = new ItemConsumeUseCase(inventory, partyService, campaign);
    }

    @Test
    void execute_normalUse_restoresHpMana() {
        when(campaign.isEndOfRoom()).thenReturn(true);
        when(inventory.getItemCount(potion)).thenReturn(1);

        ItemConsumeResponseDTO result = useCase.execute(potion, 0);

        verify(inventory).useItem(potion, 1);
        verify(hero).gainCurHp(30);
        verify(hero).gainCurMana(5);

        assertEquals("Alice", result.heroName());
        assertEquals("Health Potion", result.itemName());
        assertEquals(30, result.hpRestored());
        assertEquals(5, result.manaRestored());
        assertFalse(result.revived());
    }

    @Test
    void execute_notEndOfRoom_throws() {
        when(campaign.isEndOfRoom()).thenReturn(false);
        Exception ex = assertThrows(IllegalStateException.class,
                () -> useCase.execute(potion, 0));
        assertEquals("You can only use items after finishing a room", ex.getMessage());
    }

    @Test
    void execute_invalidHeroIndex_throws() {
        when(campaign.isEndOfRoom()).thenReturn(true);
        Exception ex = assertThrows(IllegalArgumentException.class,
                () -> useCase.execute(potion, 5));
        assertEquals("Invalid hero index", ex.getMessage());
    }

    @Test
    void execute_notEnoughItem_throws() {
        when(campaign.isEndOfRoom()).thenReturn(true);
        when(inventory.getItemCount(potion)).thenReturn(0);

        Exception ex = assertThrows(IllegalStateException.class,
                () -> useCase.execute(potion, 0));
        assertEquals("Not enough Health Potion to use", ex.getMessage());
    }
}