// With the use of AI
// The Memento Object

package com.github.domain;

public class GameStateSnapshot {
    private int userID;
    private String locationType;
    private PartyData partyData;
    private InvData inventoryData;
    private RoomData roomData;

    public GameStateSnapshot(int userID, String locationType, PartyData partyData, InvData inventoryData, RoomData roomData) {
        this.userID = userID;
        this.locationType = locationType;
        this.partyData = partyData;
        this.inventoryData = inventoryData;
        this.roomData = roomData;
    }

    public PartyData getPartyData() { return partyData; }
    public InvData getInventoryData() { return inventoryData; }
    public RoomData getRoomData() { return roomData; }
    public String getLocationType() { return locationType; }
}
