package com.github.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InnTest {

    private Inn inn;

    @BeforeEach
    void setUp() {
        inn = new Inn();
    }

    @Test
    void viewRecruitsShouldReturnCopy() {
        Map<String, HeroState> recruits = inn.viewRecruits();

        assertNotNull(recruits);
        assertTrue(recruits.isEmpty());

        recruits.put("Test", new HeroState());

        // original should not be modified
        assertTrue(inn.viewRecruits().isEmpty());
    }

    @Test
    void hasRecruitShouldReturnFalseWhenEmpty() {
        assertFalse(inn.hasRecruit("Unknown"));
    }

    @Test
    void getShopShouldReturnItems() {
        List<ItemTest> shop = inn.getShop();

        assertNotNull(shop);
        assertFalse(shop.isEmpty());
    }

    @Test
    void getShopShouldReturnCopy() {
        List<ItemTest> shop = inn.getShop();
        int originalSize = shop.size();

        shop.clear();

        assertEquals(originalSize, inn.getShop().size());
    }

    @Test
    void initializeShouldNotCrashWithValidPartyService() {
        PartyServiceTest mockParty = new PartyServiceTest() {
            @Override
            public void maxRestore() {}

            @Override
            public int getHeroCount() {
                return 0;
            }
        };

        assertDoesNotThrow(() -> inn.intialize(mockParty));
    }

    @Test
    void initializeShouldHandleFullParty() {
        PartyServiceTest mockParty = new PartyServiceTest() {
            @Override
            public void maxRestore() {}

            @Override
            public int getHeroCount() {
                return 5;
            }
        };

        assertDoesNotThrow(() -> inn.intialize(mockParty));
    }
}