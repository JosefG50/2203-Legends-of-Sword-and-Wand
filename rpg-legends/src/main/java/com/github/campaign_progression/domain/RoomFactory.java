package com.github.campaign_progression.domain;

import java.util.Random;

/**
 * Factory class responsible for generating the next {@link Room}
 * in the campaign.
 *
 * <p>The type of room created is determined probabilistically
 * using the provided battle chance.</p>
 */
public class RoomFactory {

    private final Random random;

    public RoomFactory(Random random) {
        this.random = random;
    }

    public RoomFactory() {
        this(new Random());
    }

    public Room createNextRoom(double battleChance) {
        if (random.nextDouble() < battleChance) {
            return new BattleRoom();
        } else {
            return new Inn();
        }
    }
}