package com.github.campaign_progression.domain;

/**
 * Handles campaign progression logic.
 * Responsible for room transitions and rules.
 */
public class CampaignService {

    private static final int MAX_ROOMS = 30;

    private final RoomFactory roomFactory;

    public CampaignService(RoomFactory roomFactory) {
        if (roomFactory == null) {
            throw new IllegalArgumentException("RoomFactory cannot be null");
        }
        this.roomFactory = roomFactory;
    }

    public void startNewCampaign(CampaignState state) {
        state.setBattleChance(0.6);
        state.setRoomCounter(1);
        state.setEndOfRoom(false);

        Room room = roomFactory.createNextRoom(state.getBattleChance());
        state.setCurrentRoom(room);
    }

    public Room nextRoom(CampaignState state) {

        if (state.getRoomCounter() >= MAX_ROOMS) {
            throw new IllegalStateException("Campaign is already complete");
        }

        state.setRoomCounter(state.getRoomCounter() + 1);
        state.setEndOfRoom(false);

        Room room = roomFactory.createNextRoom(state.getBattleChance());
        state.setCurrentRoom(room);

        return room;
    }

    public void setBattleChance(CampaignState state, double value) {
        if (value < 0 || value > 1) {
            throw new IllegalArgumentException("Battle chance must be between 0 and 1");
        }
        state.setBattleChance(value);
    }
}