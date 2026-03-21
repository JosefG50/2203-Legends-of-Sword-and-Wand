package com.github.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CampaignManagerTest {

    private CampaignManager campaignManager;

    @BeforeEach
    void setUp() {
        campaignManager = new CampaignManager();
    }

    @Test
    void shouldInitializeWithDefaultValues() {
        assertEquals(0.6f, campaignManager.getBattleChance());
        assertEquals(1, campaignManager.getRoomCounter());
    }

    @Test
    void shouldSetBattleChance() {
        campaignManager.setBattleChance(0.8f);
        assertEquals(0.8f, campaignManager.getBattleChance());
    }

    @Test
    void shouldIncreaseRoomCounter() {
        int before = campaignManager.getRoomCounter();

        campaignManager.increaseRoomCounter();

        assertEquals(before + 1, campaignManager.getRoomCounter());
    }

    @Test
    void shouldThrowWhenCampaignComplete() {
        // push counter to 30
        while (campaignManager.getRoomCounter() < 30) {
            campaignManager.increaseRoomCounter();
        }

        assertThrows(IllegalStateException.class, () -> {
            campaignManager.increaseRoomCounter();
        });
    }

    @Test
    void nextRoomShouldIncreaseRoomCounter() {
        int before = campaignManager.getRoomCounter();

        campaignManager.nextRoom();

        assertEquals(before + 1, campaignManager.getRoomCounter());
    }

    @Test
    void nextRoomShouldReturnRoom() {
        Room room = campaignManager.nextRoom();
        assertNotNull(room);
    }
}