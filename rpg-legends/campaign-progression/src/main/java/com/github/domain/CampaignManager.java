package com.github.domain;

public class CampaignManager {

    private Room currentRoom;
    private float battleChance;
    private int roomCounter;
    private boolean endOfRoom;

    private final RoomFactory roomFactory;

    private static final int MAX_ROOMS = 30;

    public CampaignManager(RoomFactory roomFactory) {
        if (roomFactory == null) {
            throw new IllegalArgumentException("RoomFactory cannot be null");
        }

        this.roomFactory = roomFactory;
        this.battleChance = 0.6f;
        this.roomCounter = 1;

        this.currentRoom = roomFactory.createNextRoom(battleChance);
    }

    public boolean isEndOfRoom() {
        return endOfRoom;
    }

    public void setEndOfRoom(boolean value) {
        endOfRoom = value;
    }

    public float getBattleChance() {
        return battleChance;
    }

    public void setBattleChance(float value) {
        if (value < 0 || value > 1) {
            throw new IllegalArgumentException("Battle chance must be between 0 and 1");
        }
        this.battleChance = value;
    }

    public int getRoomCounter() {
        return roomCounter;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public Room nextRoom() {
        if (roomCounter >= MAX_ROOMS) {
            throw new IllegalStateException("Campaign is already complete");
        }

        roomCounter++;
        endOfRoom = false;
        currentRoom = roomFactory.createNextRoom(battleChance);

        return currentRoom;
    }
}