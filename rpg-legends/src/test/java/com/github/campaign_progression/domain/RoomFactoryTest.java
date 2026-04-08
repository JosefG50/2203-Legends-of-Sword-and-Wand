package com.github.campaign_progression.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

class RoomFactoryTest {
    @Test
void createNextRoom_controlledRandom_returnsBattleRoom() {
    Random mockRandom = new Random() {
        @Override
        public double nextDouble() {
            return 0.1;
        }
    };

    RoomFactory factory = new RoomFactory(mockRandom);

    Room room = factory.createNextRoom(0.5);

    assertTrue(room instanceof BattleRoom);
}
}