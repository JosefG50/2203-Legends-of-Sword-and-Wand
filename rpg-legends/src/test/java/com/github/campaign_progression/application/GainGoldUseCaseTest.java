package com.github.campaign_progression.application;

import com.github.campaign_progression.application.dto.GoldDTO;
import com.github.campaign_progression.domain.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GainGoldUseCaseTest {

    private InventoryService inventoryService;
    private GainGoldUseCase useCase;

    @BeforeEach
    void setup() {
        inventoryService = new InventoryService(100); // start with 100 gold
        useCase = new GainGoldUseCase(inventoryService);
    }

    @Test
    void execute_addsGoldCorrectly() {
        GoldDTO result = useCase.execute(50);

        assertEquals(50, result.getGoldAdded());
        assertEquals(150, result.getNewTotalGold());
        assertEquals(150, inventoryService.getGold());
    }

    @Test
    void execute_addZeroGold_doesNotChangeTotal() {
        GoldDTO result = useCase.execute(0);

        assertEquals(0, result.getGoldAdded());
        assertEquals(100, result.getNewTotalGold());
        assertEquals(100, inventoryService.getGold());
    }

    @Test
    void execute_negativeGold_throws() {
        assertThrows(IllegalArgumentException.class, () -> useCase.execute(-10));
    }
}