package com.github.campaign_progression.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link BattleRoom}.
 * Uses Mockito to mock {@link Enemy} dependencies.
 */
class BattleRoomTest {

    private BattleRoom battleRoom;

    @BeforeEach
    void setUp() {
        battleRoom = new BattleRoom();
    }

    // -------------------------------------------------------------------------
    // Constructor
    // -------------------------------------------------------------------------

    @Test
    void constructor_enemiesAreNullByDefault() {
        assertNull(battleRoom.getEnemies(),
                "Enemies should be null immediately after construction");
    }

    // -------------------------------------------------------------------------
    // addEnemies()
    // -------------------------------------------------------------------------

    @Test
    void addEnemies_validList_setsEnemies() {
        Enemy enemy = mock(Enemy.class);
        battleRoom.addEnemies(List.of(enemy));
        assertNotNull(battleRoom.getEnemies());
        assertEquals(1, battleRoom.getEnemies().size());
    }

    @Test
    void addEnemies_multipleEnemies_allStored() {
        Enemy e1 = mock(Enemy.class);
        Enemy e2 = mock(Enemy.class);
        Enemy e3 = mock(Enemy.class);
        battleRoom.addEnemies(List.of(e1, e2, e3));
        assertEquals(3, battleRoom.getEnemies().size());
    }

    @Test
    void addEnemies_nullList_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> battleRoom.addEnemies(null),
                "Should throw when enemies list is null");
    }

    @Test
    void addEnemies_emptyList_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class,
                () -> battleRoom.addEnemies(Collections.emptyList()),
                "Should throw when enemies list is empty");
    }

    @Test
    void addEnemies_replacesExistingEnemies() {
        Enemy first = mock(Enemy.class);
        Enemy second = mock(Enemy.class);

        battleRoom.addEnemies(List.of(first));
        battleRoom.addEnemies(List.of(second));

        assertEquals(1, battleRoom.getEnemies().size());
        assertTrue(battleRoom.getEnemies().contains(second));
        assertFalse(battleRoom.getEnemies().contains(first));
    }

    // -------------------------------------------------------------------------
    // getEnemies()
    // -------------------------------------------------------------------------

    @Test
    void getEnemies_returnsNullBeforeEnemiesAdded() {
        assertNull(battleRoom.getEnemies());
    }

    @Test
    void getEnemies_returnsCorrectListAfterAdd() {
        Enemy enemy = mock(Enemy.class);
        List<Enemy> list = List.of(enemy);
        battleRoom.addEnemies(list);
        assertEquals(list, battleRoom.getEnemies());
    }

    // -------------------------------------------------------------------------
    // getExp()
    // -------------------------------------------------------------------------

    @Test
    void getExp_singleEnemyLevelOne_returns50() {
        Enemy enemy = mock(Enemy.class);
        when(enemy.getLevel()).thenReturn(1);

        battleRoom.addEnemies(List.of(enemy));

        assertEquals(50, battleRoom.getExp());
    }

    @Test
    void getExp_singleEnemyLevelFive_returns250() {
        Enemy enemy = mock(Enemy.class);
        when(enemy.getLevel()).thenReturn(5);

        battleRoom.addEnemies(List.of(enemy));

        assertEquals(250, battleRoom.getExp());
    }

    @Test
    void getExp_multipleEnemies_returnsSummedExp() {
        Enemy e1 = mock(Enemy.class);
        Enemy e2 = mock(Enemy.class);
        when(e1.getLevel()).thenReturn(2); // 100
        when(e2.getLevel()).thenReturn(3); // 150

        battleRoom.addEnemies(List.of(e1, e2));

        assertEquals(250, battleRoom.getExp());
    }

    @Test
    void getExp_enemyLevelZero_contributesZeroExp() {
        Enemy enemy = mock(Enemy.class);
        when(enemy.getLevel()).thenReturn(0);

        battleRoom.addEnemies(List.of(enemy));

        assertEquals(0, battleRoom.getExp());
    }

    @Test
    void getExp_noEnemiesSet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> battleRoom.getExp(),
                "Should throw NullPointerException when enemies have not been set");
    }

    // -------------------------------------------------------------------------
    // getGold()
    // -------------------------------------------------------------------------

    @Test
    void getGold_singleEnemyLevelOne_returns75() {
        Enemy enemy = mock(Enemy.class);
        when(enemy.getLevel()).thenReturn(1);

        battleRoom.addEnemies(List.of(enemy));

        assertEquals(75, battleRoom.getGold());
    }

    @Test
    void getGold_singleEnemyLevelFour_returns300() {
        Enemy enemy = mock(Enemy.class);
        when(enemy.getLevel()).thenReturn(4);

        battleRoom.addEnemies(List.of(enemy));

        assertEquals(300, battleRoom.getGold());
    }

    @Test
    void getGold_multipleEnemies_returnsSummedGold() {
        Enemy e1 = mock(Enemy.class);
        Enemy e2 = mock(Enemy.class);
        when(e1.getLevel()).thenReturn(2); // 150
        when(e2.getLevel()).thenReturn(3); // 225

        battleRoom.addEnemies(List.of(e1, e2));

        assertEquals(375, battleRoom.getGold());
    }

    @Test
    void getGold_enemyLevelZero_contributesZeroGold() {
        Enemy enemy = mock(Enemy.class);
        when(enemy.getLevel()).thenReturn(0);

        battleRoom.addEnemies(List.of(enemy));

        assertEquals(0, battleRoom.getGold());
    }

    @Test
    void getGold_noEnemiesSet_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> battleRoom.getGold(),
                "Should throw NullPointerException when enemies have not been set");
    }
}