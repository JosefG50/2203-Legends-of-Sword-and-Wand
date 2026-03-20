package com.github.domain;

public class CampaignManager {
    protected static Room curRoom;
    protected static float battleChance;
    protected static int roomCounter;
    
    public CampaignManager() {
        curRoom = new Room();
        battleChance = 0.6f;
        roomCounter = 0;
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
        roomCounter++;

    }
    public Room nextRoom() {
        //TODO: use Room factory to create rooms with different probabilities
        increaseRoomCounter();
        curRoom = new Room();
        return curRoom;

        increaseRoomCounter();
    }
    public Room getCurRoom() {
        return curRoom;
    }
    public void StartNewCampaign() {
        // TODO: Fix this method 
        curRoom = new Room();
        battleChance = 0.6f;
        roomCounter = 0;
    }


}
