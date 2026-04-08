package com.github.campaign_progression.domain;

/**
 * Pure state holder for campaign progression.
 * Contains NO game logic.
 */
public class CampaignState {

    private Room currentRoom;
    private double battleChance;
    private int roomCounter;
    private boolean endOfRoom;

    // Getters and setters only

    public Room getCurrentRoom() { return currentRoom; }
    public void setCurrentRoom(Room currentRoom) { this.currentRoom = currentRoom; }

    public double getBattleChance() { return battleChance; }
    public void setBattleChance(double battleChance) { this.battleChance = battleChance; }

    public int getRoomCounter() { return roomCounter; }
    public void setRoomCounter(int roomCounter) { this.roomCounter = roomCounter; }

    public boolean isEndOfRoom() { return endOfRoom; }
    public void setEndOfRoom(boolean endOfRoom) { this.endOfRoom = endOfRoom; }
}