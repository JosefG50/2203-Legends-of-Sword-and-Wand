package com.github.domain;

public class GameStateMemento {
    private int userID;
    private PartyData partyStats;
    private InvData inventoryData;
    private RoomData roomProgress;

    public GameStateMemento(int userID, PartyData partyStats, InvData inventoryData, RoomData roomProgress) {
        this.userID = userID;
        this.partyStats = partyStats;
        this.inventoryData = inventoryData;
        this.roomProgress = roomProgress;
    }

    public int getUserID() { return userID; }
    public PartyData getPartyStats() { return partyStats; }
    public InvData getInventoryData() { return inventoryData; }
    public RoomData getRoomProgress() { return roomProgress; }
}
