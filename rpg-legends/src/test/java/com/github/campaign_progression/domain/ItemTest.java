package com.github.campaign_progression.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {

    @Test
    void constructor_validType_createsItem() {
        Item item = new Item(ItemType.BREAD);

        assertNotNull(item);
        assertEquals(ItemType.BREAD, item.getType());
    }

    @Test
    void constructor_nullType_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> new Item(null));
    }

    @Test
    void getName_returnsCorrectName() {
        Item item = new Item(ItemType.BREAD);

        assertEquals(ItemType.BREAD.getName(), item.getName());
    }

    @Test
    void getCost_returnsCorrectCost() {
        Item item = new Item(ItemType.BREAD);

        assertEquals(ItemType.BREAD.getCost(), item.getCost());
    }

    @Test
    void getHpHeal_returnsCorrectValue() {
        Item item = new Item(ItemType.BREAD);

        assertEquals(ItemType.BREAD.getHpHeal(), item.getHpHeal());
    }

    @Test
    void getManaHeal_returnsCorrectValue() {
        Item item = new Item(ItemType.BREAD);

        assertEquals(ItemType.BREAD.getManaHeal(), item.getManaHeal());
    }

    @Test
    void canRevive_returnsCorrectValue() {
        Item item = new Item(ItemType.BREAD);

        assertEquals(ItemType.BREAD.canRevive(), item.canRevive());
    }

    @Test
    void toString_containsExpectedValues() {
        Item item = new Item(ItemType.BREAD);

        String result = item.toString();

        assertTrue(result.contains(ItemType.BREAD.name()));
        assertTrue(result.contains(String.valueOf(ItemType.BREAD.getCost())));
        assertTrue(result.contains(String.valueOf(ItemType.BREAD.getHpHeal())));
        assertTrue(result.contains(String.valueOf(ItemType.BREAD.getManaHeal())));
    }
}