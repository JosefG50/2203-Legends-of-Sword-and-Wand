package com.github.campaign_progression.application;

import com.github.campaign_progression.application.dto.ItemDTO;
import com.github.campaign_progression.domain.InventoryService;
import com.github.campaign_progression.domain.ItemType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetInventoryUseCaseTest {

    @Test
    void execute_returnsAllItemDTOs() {
        // Mock InventoryService
        InventoryService inventoryService = mock(InventoryService.class);
        int[] counts = {2, 0, 5}; // assume 3 items for test
        when(inventoryService.getItems()).thenReturn(counts);

        GetInventoryUseCase useCase = new GetInventoryUseCase(inventoryService);

        List<ItemDTO> result = useCase.execute();

        assertEquals(ItemType.values().length, result.size());

        // Check first item
        ItemDTO dto0 = result.get(0);
        assertEquals(ItemType.BREAD.getName(), dto0.getName());
        assertEquals(2, dto0.getQuantity());

        // Check second item (0 quantity)
        ItemDTO dto1 = result.get(1);
        assertEquals(ItemType.CHEESE.getName(), dto1.getName());
        assertEquals(0, dto1.getQuantity());

        // Check third item
        ItemDTO dto2 = result.get(2);
        assertEquals(ItemType.STEAK.getName(), dto2.getName());
        assertEquals(5, dto2.getQuantity());
    }

    @Test
    void execute_emptyInventory_returnsZeroCounts() {
        InventoryService inventoryService = mock(InventoryService.class);
        int[] counts = new int[ItemType.values().length];
        when(inventoryService.getItems()).thenReturn(counts);

        GetInventoryUseCase useCase = new GetInventoryUseCase(inventoryService);
        List<ItemDTO> result = useCase.execute();

        for (ItemDTO dto : result) {
            assertEquals(0, dto.getQuantity());
        }
    }
}