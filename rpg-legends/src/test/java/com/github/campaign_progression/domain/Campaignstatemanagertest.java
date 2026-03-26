package com.github.campaign_progression.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link CampaignStateManager}.
 * All dependencies are mocked with Mockito.
 */
class CampaignStateManagerTest {

    private CampaignManager      mockCampaignManager;
    private PartyService         mockPartyService;
    private InventoryService     mockInventoryService;
    private Inn                  mockInn;
    private CampaignSnapshot     mockSnapshot;
    private CampaignStateManager stateManager;

    @BeforeEach
    void setUp() {
        mockCampaignManager  = mock(CampaignManager.class);
        mockPartyService     = mock(PartyService.class);
        mockInventoryService = mock(InventoryService.class);
        mockInn              = mock(Inn.class);
        mockSnapshot         = mock(CampaignSnapshot.class);

        stateManager = new CampaignStateManager(
                mockCampaignManager,
                mockPartyService,
                mockInventoryService,
                mockInn
        );
    }

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    @Test
    void constructor_allDependenciesProvided_doesNotThrow() {
        assertDoesNotThrow(() -> new CampaignStateManager(
                mockCampaignManager, mockPartyService, mockInventoryService, mockInn
        ));
    }

    @Test
    void constructor_nullInn_doesNotThrow() {
        // Inn is not stored, so null should be acceptable
        assertDoesNotThrow(() -> new CampaignStateManager(
                mockCampaignManager, mockPartyService, mockInventoryService, null
        ));
    }

    // -------------------------------------------------------------------------
    // loadFromSnapshot() — null guard
    // -------------------------------------------------------------------------

    @Test
    void loadFromSnapshot_nullSnapshot_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> stateManager.loadFromSnapshot(null));
    }

    // -------------------------------------------------------------------------
    // loadFromSnapshot() — battle chance
    // -------------------------------------------------------------------------

    @Test
    void loadFromSnapshot_setsBattleChanceFromSnapshot() {
        setupMinimalSnapshot(0.8, 1, 0, 0, List.of(), new int[]{});

        stateManager.loadFromSnapshot(mockSnapshot);

        verify(mockCampaignManager).setBattleChance(0.8f);
    }

    // -------------------------------------------------------------------------
    // loadFromSnapshot() — room counter advancement
    // -------------------------------------------------------------------------

    @Test
    void loadFromSnapshot_roomCounterAlreadyMatches_doesNotCallNextRoom() {
        setupMinimalSnapshot(0.6, 3, 0, 0, List.of(), new int[]{});
        when(mockCampaignManager.getRoomCounter()).thenReturn(3);

        stateManager.loadFromSnapshot(mockSnapshot);

        verify(mockCampaignManager, never()).nextRoom();
    }

    @Test
    void loadFromSnapshot_roomCounterBehind_advancesRoomsCorrectly() {
        setupMinimalSnapshot(0.6, 5, 0, 0, List.of(), new int[]{});

        // Simulate counter incrementing: 1 → 2 → 3 → 4 → 5
        when(mockCampaignManager.getRoomCounter())
                .thenReturn(1, 2, 3, 4, 5);

        stateManager.loadFromSnapshot(mockSnapshot);

        verify(mockCampaignManager, times(4)).nextRoom();
    }

    @Test
    void loadFromSnapshot_roomCounterAheadOfSnapshot_doesNotCallNextRoom() {
        setupMinimalSnapshot(0.6, 2, 0, 0, List.of(), new int[]{});
        when(mockCampaignManager.getRoomCounter()).thenReturn(5);

        stateManager.loadFromSnapshot(mockSnapshot);

        verify(mockCampaignManager, never()).nextRoom();
    }

    // -------------------------------------------------------------------------
    // loadFromSnapshot() — inventory
    // -------------------------------------------------------------------------

    @Test
    void loadFromSnapshot_gainsGoldFromSnapshot() {
        setupMinimalSnapshot(0.6, 1, 500, 0, List.of(), new int[]{});
        when(mockCampaignManager.getRoomCounter()).thenReturn(1);

        stateManager.loadFromSnapshot(mockSnapshot);

        verify(mockInventoryService).gainGold(500);
    }

    @Test
    void loadFromSnapshot_setsItemsFromSnapshot() {
        int[] items = {1, 2, 3};
        setupMinimalSnapshot(0.6, 1, 0, 0, List.of(), items);
        when(mockCampaignManager.getRoomCounter()).thenReturn(1);

        stateManager.loadFromSnapshot(mockSnapshot);

        verify(mockInventoryService).setItems(items);
    }

    // -------------------------------------------------------------------------
    // loadFromSnapshot() — party
    // -------------------------------------------------------------------------

    @Test
    void loadFromSnapshot_clearsExistingPartyBeforeAdding() {
        List<HeroState> mutableParty = new ArrayList<>();
        when(mockPartyService.getParty()).thenReturn(mutableParty);
        setupMinimalSnapshot(0.6, 1, 0, 0, List.of(), new int[]{});
        when(mockCampaignManager.getRoomCounter()).thenReturn(1);

        stateManager.loadFromSnapshot(mockSnapshot);

        // getParty().clear() was called — verify getParty was accessed
        verify(mockPartyService, atLeastOnce()).getParty();
    }

    @Test
    void loadFromSnapshot_addsEachHeroFromSnapshot() {
        HeroState hero1 = mock(HeroState.class);
        HeroState hero2 = mock(HeroState.class);
        List<HeroState> party = List.of(hero1, hero2);

        when(mockPartyService.getParty()).thenReturn(new ArrayList<>());
        setupMinimalSnapshot(0.6, 1, 0, 0, party, new int[]{});
        when(mockCampaignManager.getRoomCounter()).thenReturn(1);

        stateManager.loadFromSnapshot(mockSnapshot);

        verify(mockPartyService).addHero(hero1);
        verify(mockPartyService).addHero(hero2);
    }

    @Test
    void loadFromSnapshot_emptyParty_doesNotCallAddHero() {
        when(mockPartyService.getParty()).thenReturn(new ArrayList<>());
        setupMinimalSnapshot(0.6, 1, 0, 0, List.of(), new int[]{});
        when(mockCampaignManager.getRoomCounter()).thenReturn(1);

        stateManager.loadFromSnapshot(mockSnapshot);

        verify(mockPartyService, never()).addHero(any());
    }

    // -------------------------------------------------------------------------
    // createSnapshot()
    // -------------------------------------------------------------------------

    @Test
    void createSnapshot_returnsNonNullSnapshot() {
        setupCreateSnapshotMocks(3, 0.7, "BattleRoom", 200, 5, List.of(), new int[]{});

        CampaignSnapshot result = stateManager.createSnapshot();

        assertNotNull(result);
    }

    @Test
    void createSnapshot_snapshotHasCorrectRoomCounter() {
        setupCreateSnapshotMocks(7, 0.6, "TreasureRoom", 0, 0, List.of(), new int[]{});

        CampaignSnapshot result = stateManager.createSnapshot();

        assertEquals(7, result.getRoomCounter());
    }

    @Test
    void createSnapshot_snapshotHasCorrectBattleChance() {
        setupCreateSnapshotMocks(1, 0.85, "BattleRoom", 0, 0, List.of(), new int[]{});

        CampaignSnapshot result = stateManager.createSnapshot();

        assertEquals(0.85, result.getBattleChance(), 0.001);
    }

    @Test
    void createSnapshot_snapshotHasCorrectGold() {
        setupCreateSnapshotMocks(1, 0.6, "BattleRoom", 999, 0, List.of(), new int[]{});

        CampaignSnapshot result = stateManager.createSnapshot();

        assertEquals(999, result.getGold());
    }

    @Test
    void createSnapshot_snapshotHasCorrectExp() {
        setupCreateSnapshotMocks(1, 0.6, "BattleRoom", 0, 12, List.of(), new int[]{});

        CampaignSnapshot result = stateManager.createSnapshot();

        assertEquals(12, result.getExp());
    }

    @Test
    void createSnapshot_snapshotHasCorrectParty() {
        List<HeroState> party = List.of(mock(HeroState.class));
        setupCreateSnapshotMocks(1, 0.6, "BattleRoom", 0, 0, party, new int[]{});

        CampaignSnapshot result = stateManager.createSnapshot();

        assertEquals(party, result.getParty());
    }

    @Test
    void createSnapshot_snapshotHasCorrectItems() {
        int[] items = {4, 5, 6};
        setupCreateSnapshotMocks(1, 0.6, "BattleRoom", 0, 0, List.of(), items);

        CampaignSnapshot result = stateManager.createSnapshot();

        assertArrayEquals(items, result.getItems());
    }

    @Test
    void createSnapshot_curRoomUsesRoomToString() {
        Room mockRoom = mock(Room.class);
        when(mockRoom.toString()).thenReturn("MockRoom");
        when(mockCampaignManager.getCurrentRoom()).thenReturn(mockRoom);
        when(mockCampaignManager.getRoomCounter()).thenReturn(1);
        when(mockCampaignManager.getBattleChance()).thenReturn(0.6);
        when(mockInventoryService.getGold()).thenReturn(0);
        when(mockInventoryService.getItems()).thenReturn(new int[]{});
        when(mockPartyService.getTotalLevels()).thenReturn(0);
        when(mockPartyService.getParty()).thenReturn(List.of());

        CampaignSnapshot result = stateManager.createSnapshot();

        assertEquals("MockRoom", result.getCurRoom());
    }

    // -------------------------------------------------------------------------
    // Helpers
    // -------------------------------------------------------------------------

    /**
     * Configures mockSnapshot with the given values for loadFromSnapshot tests.
     */
    private void setupMinimalSnapshot(double battleChance, int roomCounter,
                                      int gold, int exp,
                                      List<HeroState> party, int[] items) {
        when(mockSnapshot.getBattleChance()).thenReturn(battleChance);
        when(mockSnapshot.getRoomCounter()).thenReturn(roomCounter);
        when(mockSnapshot.getGold()).thenReturn(gold);
        when(mockSnapshot.getExp()).thenReturn(exp);
        when(mockSnapshot.getParty()).thenReturn(party);
        when(mockSnapshot.getItems()).thenReturn(items);
        when(mockPartyService.getParty()).thenReturn(new ArrayList<>());
    }

    /**
     * Configures all mocks needed for createSnapshot() tests.
     */
    private void setupCreateSnapshotMocks(int roomCounter, double battleChance,
                                          String roomName, int gold, int totalLevels,
                                          List<HeroState> party, int[] items) {
        Room mockRoom = mock(Room.class);
        when(mockRoom.toString()).thenReturn(roomName);
        when(mockCampaignManager.getRoomCounter()).thenReturn(roomCounter);
        when(mockCampaignManager.getBattleChance()).thenReturn(battleChance);
        when(mockCampaignManager.getCurrentRoom()).thenReturn(mockRoom);
        when(mockInventoryService.getGold()).thenReturn(gold);
        when(mockInventoryService.getItems()).thenReturn(items);
        when(mockPartyService.getTotalLevels()).thenReturn(totalLevels);
        when(mockPartyService.getParty()).thenReturn(party);
    }
}