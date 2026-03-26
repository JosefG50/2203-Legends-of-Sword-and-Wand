package com.github.campaign_progression.application;

import com.github.campaign_progression.domain.*;
import com.github.campaign_progression.application.dto.BuyItemResponseDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BuyItemUseCaseTest {

    /** Helper: create an Inn with a single item in the shop */
    private Inn createInnWith(ItemType type) {
        Inn inn = new Inn();
        inn.getShop().clear();
        inn.getShop().add(type);
        return inn;
    }

    /** Helper: create an InventoryService with given starting gold */
    private InventoryService createInventory(int startingGold) {
        return new InventoryService(startingGold);
    }

    @Test
    void execute_validPurchase_success() {
        ItemType type = ItemType.BREAD;
        Inn inn = createInnWith(type);
        InventoryService inv = createInventory(1000);

        BuyItemUseCase useCase = new BuyItemUseCase(inn, inv);

        BuyItemResponseDTO result = useCase.execute(type, 2);

        assertTrue(result.isSuccess());
        assertEquals(2, result.getAmountBought());
        assertEquals(type.getName(), result.getItemName());
        assertEquals(2, inv.getItemCount(type)); // inventory updated correctly
        assertEquals(1000 - 2 * type.getCost(), inv.getGold()); // gold deducted
    }

    @Test
    void execute_amountLessThanOne_returnsFailure() {
        ItemType type = ItemType.BREAD;
        BuyItemUseCase useCase = new BuyItemUseCase(createInnWith(type), createInventory(1000));

        BuyItemResponseDTO result = useCase.execute(type, 0);

        assertFalse(result.isSuccess());
        assertEquals("Amount must be at least 1", result.getMessage());
    }

    @Test
    void execute_itemNotInShop_returnsFailure() {
        ItemType type = ItemType.BREAD;
        BuyItemUseCase useCase = new BuyItemUseCase(new Inn(), createInventory(1000));

        BuyItemResponseDTO result = useCase.execute(type, 1);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("not available"));
    }

    @Test
    void execute_notEnoughGold_returnsFailure() {
        ItemType type = ItemType.BREAD;
        Inn inn = createInnWith(type);
        InventoryService inv = createInventory(0);

        BuyItemUseCase useCase = new BuyItemUseCase(inn, inv);

        BuyItemResponseDTO result = useCase.execute(type, 1);

        assertFalse(result.isSuccess());
        assertEquals(0, inv.getItemCount(type)); // inventory unchanged
    }

    @Test
    void execute_singleArgument_callsDefaultAmountOne() {
        ItemType type = ItemType.BREAD;
        Inn inn = createInnWith(type);
        InventoryService inv = createInventory(1000);

        BuyItemUseCase useCase = new BuyItemUseCase(inn, inv);

        BuyItemResponseDTO result = useCase.execute(type);

        assertEquals(1, result.getAmountBought());
        assertEquals(type.getName(), result.getItemName());
        assertEquals(1, inv.getItemCount(type));
    }
}