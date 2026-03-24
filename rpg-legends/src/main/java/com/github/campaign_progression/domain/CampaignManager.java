package com.github.campaign_progression.domain;

public class CampaignManager {

    private Room currentRoom;
    private double battleChance;
    private int roomCounter;
    private boolean endOfRoom;

    private final RoomFactory RoomFactory;

    private static final int MAX_ROOMS = 30;

    public CampaignManager(RoomFactory RoomFactory) {
        if (RoomFactory == null) {
            throw new IllegalArgumentException("RoomFactory cannot be null");
        }

        this.RoomFactory = RoomFactory;
        this.battleChance = 0.6f;
        this.roomCounter = 1;

        this.currentRoom = RoomFactory.createNextRoom(battleChance);
    }

    public void startNewCampaign() {
    this.battleChance = 0.6;
    this.roomCounter = 1;
    this.endOfRoom = false;

    }  
    public boolean isEndOfRoom() {
        return endOfRoom;
    }

    public void setEndOfRoom(boolean value) {
        endOfRoom = value;
    }

    public double getBattleChance() {
        return battleChance;
    }

    public void setBattleChance(double value) {
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
        currentRoom = RoomFactory.createNextRoom(battleChance);

        return currentRoom;
    }
}