package com.github.campaign_progression.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Unit tests for {@link CampaignSnapshotImpl}.
 * Verifies both constructors and all getters/setters.
 */
class CampaignSnapshotImplTest {

    private CampaignSnapshotImpl snapshot;

    // Shared test data
    private static final int    ROOM_COUNTER   = 5;
    private static final double BATTLE_CHANCE  = 0.75;
    private static final String CUR_ROOM       = "BattleRoom";
    private static final int    GOLD           = 300;
    private static final int    EXP            = 1500;

    private List<HeroState> party;
    private int[] items;

    @BeforeEach
    void setUp() {
        party = List.of(mock(HeroState.class), mock(HeroState.class));
        items = new int[]{1, 2, 3};
        snapshot = new CampaignSnapshotImpl(ROOM_COUNTER, BATTLE_CHANCE, CUR_ROOM,
                GOLD, EXP, party, items);
    }

    // -------------------------------------------------------------------------
    // No-arg constructor
    // -------------------------------------------------------------------------

    @Test
    void noArgConstructor_roomCounterDefaultsToZero() {
        CampaignSnapshotImpl empty = new CampaignSnapshotImpl();
        assertEquals(0, empty.getRoomCounter());
    }

    @Test
    void noArgConstructor_battleChanceDefaultsToZero() {
        CampaignSnapshotImpl empty = new CampaignSnapshotImpl();
        assertEquals(0.0, empty.getBattleChance(), 0.001);
    }

    @Test
    void noArgConstructor_curRoomIsNull() {
        CampaignSnapshotImpl empty = new CampaignSnapshotImpl();
        assertNull(empty.getCurRoom());
    }

    @Test
    void noArgConstructor_goldDefaultsToZero() {
        CampaignSnapshotImpl empty = new CampaignSnapshotImpl();
        assertEquals(0, empty.getGold());
    }

    @Test
    void noArgConstructor_expDefaultsToZero() {
        CampaignSnapshotImpl empty = new CampaignSnapshotImpl();
        assertEquals(0, empty.getExp());
    }

    @Test
    void noArgConstructor_partyIsNull() {
        CampaignSnapshotImpl empty = new CampaignSnapshotImpl();
        assertNull(empty.getParty());
    }

    @Test
    void noArgConstructor_itemsIsNull() {
        CampaignSnapshotImpl empty = new CampaignSnapshotImpl();
        assertNull(empty.getItems());
    }

    // -------------------------------------------------------------------------
    // Full constructor
    // -------------------------------------------------------------------------

    @Test
    void fullConstructor_setsRoomCounter() {
        assertEquals(ROOM_COUNTER, snapshot.getRoomCounter());
    }

    @Test
    void fullConstructor_setsBattleChance() {
        assertEquals(BATTLE_CHANCE, snapshot.getBattleChance(), 0.001);
    }

    @Test
    void fullConstructor_setsCurRoom() {
        assertEquals(CUR_ROOM, snapshot.getCurRoom());
    }

    @Test
    void fullConstructor_setsGold() {
        assertEquals(GOLD, snapshot.getGold());
    }

    @Test
    void fullConstructor_setsExp() {
        assertEquals(EXP, snapshot.getExp());
    }

    @Test
    void fullConstructor_setsParty() {
        assertEquals(party, snapshot.getParty());
        assertEquals(2, snapshot.getParty().size());
    }

    @Test
    void fullConstructor_setsItems() {
        assertArrayEquals(items, snapshot.getItems());
    }

    // -------------------------------------------------------------------------
    // setRoomCounter / getRoomCounter
    // -------------------------------------------------------------------------

    @Test
    void setRoomCounter_updatesValue() {
        snapshot.setRoomCounter(10);
        assertEquals(10, snapshot.getRoomCounter());
    }

    @Test
    void setRoomCounter_zero_isAllowed() {
        snapshot.setRoomCounter(0);
        assertEquals(0, snapshot.getRoomCounter());
    }

    // -------------------------------------------------------------------------
    // setBattleChance / getBattleChance
    // -------------------------------------------------------------------------

    @Test
    void setBattleChance_updatesValue() {
        snapshot.setBattleChance(0.5);
        assertEquals(0.5, snapshot.getBattleChance(), 0.001);
    }

    @Test
    void setBattleChance_zero_isAllowed() {
        snapshot.setBattleChance(0.0);
        assertEquals(0.0, snapshot.getBattleChance(), 0.001);
    }

    @Test
    void setBattleChance_one_isAllowed() {
        snapshot.setBattleChance(1.0);
        assertEquals(1.0, snapshot.getBattleChance(), 0.001);
    }

    // -------------------------------------------------------------------------
    // setCurRoom / getCurRoom
    // -------------------------------------------------------------------------

    @Test
    void setCurRoom_updatesValue() {
        snapshot.setCurRoom("TreasureRoom");
        assertEquals("TreasureRoom", snapshot.getCurRoom());
    }

    @Test
    void setCurRoom_null_isAllowed() {
        snapshot.setCurRoom(null);
        assertNull(snapshot.getCurRoom());
    }

    // -------------------------------------------------------------------------
    // setGold / getGold
    // -------------------------------------------------------------------------

    @Test
    void setGold_updatesValue() {
        snapshot.setGold(999);
        assertEquals(999, snapshot.getGold());
    }

    @Test
    void setGold_zero_isAllowed() {
        snapshot.setGold(0);
        assertEquals(0, snapshot.getGold());
    }

    // -------------------------------------------------------------------------
    // setExp / getExp
    // -------------------------------------------------------------------------

    @Test
    void setExp_updatesValue() {
        snapshot.setExp(2000);
        assertEquals(2000, snapshot.getExp());
    }

    @Test
    void setExp_zero_isAllowed() {
        snapshot.setExp(0);
        assertEquals(0, snapshot.getExp());
    }

    // -------------------------------------------------------------------------
    // setParty / getParty
    // -------------------------------------------------------------------------

    @Test
    void setParty_updatesValue() {
        List<HeroState> newParty = List.of(mock(HeroState.class));
        snapshot.setParty(newParty);
        assertEquals(newParty, snapshot.getParty());
    }

    @Test
    void setParty_null_isAllowed() {
        snapshot.setParty(null);
        assertNull(snapshot.getParty());
    }

    @Test
    void setParty_emptyList_isAllowed() {
        snapshot.setParty(List.of());
        assertNotNull(snapshot.getParty());
        assertTrue(snapshot.getParty().isEmpty());
    }

    // -------------------------------------------------------------------------
    // setItems / getItems
    // -------------------------------------------------------------------------

    @Test
    void setItems_updatesValue() {
        int[] newItems = {10, 20, 30, 40};
        snapshot.setItems(newItems);
        assertArrayEquals(newItems, snapshot.getItems());
    }

    @Test
    void setItems_null_isAllowed() {
        snapshot.setItems(null);
        assertNull(snapshot.getItems());
    }

    @Test
    void setItems_emptyArray_isAllowed() {
        snapshot.setItems(new int[]{});
        assertNotNull(snapshot.getItems());
        assertEquals(0, snapshot.getItems().length);
    }

    @Test
    void getItems_returnsSameReference() {
        int[] newItems = {7, 8, 9};
        snapshot.setItems(newItems);
        assertSame(newItems, snapshot.getItems());
    }
}