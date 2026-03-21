package com.github.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomFactoryTest {

    private RoomFactory factory = new RoomFactory();

    @Test
    void shouldReturnBattleOrInn() {
        Room room = factory.CreateNextRoom(0.5f);

        assertNotNull(room);
        assertTrue(room instanceof Battle || room instanceof InnTest);
    }

    @Test
    void shouldAlwaysReturnBattleWhenChanceIsOne() {
        Room room = factory.CreateNextRoom(1.0f);

        assertTrue(room instanceof Battle);
    }

    @Test
    void shouldAlwaysReturnInnWhenChanceIsZero() {
        Room room = factory.CreateNextRoom(0.0f);

        assertTrue(room instanceof InnTest);
    }

    @Test
    void multipleCallsShouldReturnValidRooms() {
        for (int i = 0; i < 20; i++) {
            Room room = factory.CreateNextRoom(0.5f);

            assertNotNull(room);
         assertTrue(room instanceof Battle || room instanceof InnTest);
        }
    }
}