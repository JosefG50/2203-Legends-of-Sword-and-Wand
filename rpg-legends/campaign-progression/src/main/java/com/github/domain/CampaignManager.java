package com.github.domain;

public class CampaignManager {
    protected static Room curRoom;
    protected static float battleChance;
    protected static int roomCounter;
    
    public CampaignManager() {
        battleChance = 0.6f;
        roomCounter = 1;
        RoomFactory roomFactory = new RoomFactory();
        roomFactory.CreateNextRoom(battleChance);
    }

    public float getBattleChance() {
        return battleChance;
    }
    public void setBattleChance(float percent) {
        battleChance = percent;
    }
    public int getRoomCounter() {
        return roomCounter;
    }
    public void increaseRoomCounter() {
        if (roomCounter == 30) {
            throw new IllegalStateException("Campaign is already complete");
        }
        roomCounter++;
    }
    public Room nextRoom() {
        //TODO: use Room factory to create rooms with different probabilities
        increaseRoomCounter();
        roomFactory.CreateNextRoom(battleChance);    
        return curRoom;

    }
}
