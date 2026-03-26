package com.github.campaign_progression.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link CampaignManager}.
 * Uses Mockito to mock {@link RoomFactory} and {@link Room} dependencies.
 */
class CampaignManagerTest {

    private RoomFactory mockFactory;
    private Room mockRoom;
    private CampaignManager manager;

    @BeforeEach
    void setUp() {
        mockFactory = mock(RoomFactory.class);
        mockRoom = mock(Room.class);
        when(mockFactory.createNextRoom(anyDouble())).thenReturn(mockRoom);
        manager = new CampaignManager(mockFactory);
    }

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    @Test
    void constructor_nullFactory_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> new CampaignManager(null),
                "Should throw when RoomFactory is null");
    }

    @Test
    void constructor_validFactory_setsDefaultBattleChance() {
        assertEquals(0.6, manager.getBattleChance(), 0.001);
    }

    @Test
    void constructor_validFactory_setsRoomCounterToOne() {
        assertEquals(1, manager.getRoomCounter());
    }

    @Test
    void constructor_validFactory_createsFirstRoom() {
        assertNotNull(manager.getCurrentRoom());
        verify(mockFactory, times(1)).createNextRoom(anyDouble());
    }

    @Test
    void constructor_validFactory_endOfRoomIsFalse() {
        assertFalse(manager.isEndOfRoom());
    }

    // -------------------------------------------------------------------------
    // startNewCampaign()
    // -------------------------------------------------------------------------

    @Test
    void startNewCampaign_resetsBattleChanceTo0Point6() {
        manager.setBattleChance(0.9);
        manager.startNewCampaign();
        assertEquals(0.6, manager.getBattleChance(), 0.001);
    }

    @Test
    void startNewCampaign_resetsRoomCounterToOne() {
        manager.nextRoom();
        manager.nextRoom();
        manager.startNewCampaign();
        assertEquals(1, manager.getRoomCounter());
    }

    @Test
    void startNewCampaign_resetsEndOfRoomToFalse() {
        manager.setEndOfRoom(true);
        manager.startNewCampaign();
        assertFalse(manager.isEndOfRoom());
    }

    // -------------------------------------------------------------------------
    // isEndOfRoom() / setEndOfRoom()
    // -------------------------------------------------------------------------

    @Test
    void setEndOfRoom_true_isEndOfRoomReturnsTrue() {
        manager.setEndOfRoom(true);
        assertTrue(manager.isEndOfRoom());
    }

    @Test
    void setEndOfRoom_false_isEndOfRoomReturnsFalse() {
        manager.setEndOfRoom(true);
        manager.setEndOfRoom(false);
        assertFalse(manager.isEndOfRoom());
    }

    // -------------------------------------------------------------------------
    // getBattleChance() / setBattleChance()
    // -------------------------------------------------------------------------

    @Test
    void setBattleChance_validValue_updatesCorrectly() {
        manager.setBattleChance(0.75);
        assertEquals(0.75, manager.getBattleChance(), 0.001);
    }

    @Test
    void setBattleChance_zero_isAllowed() {
        assertDoesNotThrow(() -> manager.setBattleChance(0.0));
        assertEquals(0.0, manager.getBattleChance(), 0.001);
    }

    @Test
    void setBattleChance_one_isAllowed() {
        assertDoesNotThrow(() -> manager.setBattleChance(1.0));
        assertEquals(1.0, manager.getBattleChance(), 0.001);
    }

    @Test
    void setBattleChance_negativeValue_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> manager.setBattleChance(-0.1));
    }

    @Test
    void setBattleChance_greaterThanOne_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> manager.setBattleChance(1.1));
    }

    // -------------------------------------------------------------------------
    // nextRoom()
    // -------------------------------------------------------------------------

    @Test
    void nextRoom_incrementsRoomCounter() {
        manager.nextRoom();
        assertEquals(2, manager.getRoomCounter());
    }

    @Test
    void nextRoom_updatesCurrentRoom() {
        Room newRoom = mock(Room.class);
        when(mockFactory.createNextRoom(anyDouble())).thenReturn(newRoom);
        manager.nextRoom();
        assertEquals(newRoom, manager.getCurrentRoom());
    }

    @Test
    void nextRoom_resetsEndOfRoom() {
        manager.setEndOfRoom(true);
        manager.nextRoom();
        assertFalse(manager.isEndOfRoom());
    }

    @Test
    void nextRoom_callsRoomFactory() {
        manager.nextRoom();
        // Once in constructor + once in nextRoom
        verify(mockFactory, times(2)).createNextRoom(anyDouble());
    }

    @Test
    void nextRoom_usesCurrentBattleChance() {
        manager.setBattleChance(0.8);
        manager.nextRoom();
        verify(mockFactory).createNextRoom(0.8);
    }

    @Test
    void nextRoom_atMaxRooms_throwsIllegalStateException() {
        // Advance to room 29 (roomCounter starts at 1, MAX_ROOMS = 30)
        for (int i = 0; i < 28; i++) {
            manager.nextRoom();
        }
        // roomCounter is now 29 — one more call should reach MAX_ROOMS and still succeed
        manager.nextRoom(); // roomCounter = 30

        // Now at MAX_ROOMS — next call should throw
        assertThrows(IllegalStateException.class, () -> manager.nextRoom());
    }

    @Test
    void nextRoom_belowMaxRooms_doesNotThrow() {
        assertDoesNotThrow(() -> manager.nextRoom());
    }

    // -------------------------------------------------------------------------
    // getRoomCounter()
    // -------------------------------------------------------------------------

    @Test
    void getRoomCounter_initialValue_isOne() {
        assertEquals(1, manager.getRoomCounter());
    }

    @Test
    void getRoomCounter_afterMultipleNextRoom_isCorrect() {
        manager.nextRoom();
        manager.nextRoom();
        manager.nextRoom();
        assertEquals(4, manager.getRoomCounter());
    }
}