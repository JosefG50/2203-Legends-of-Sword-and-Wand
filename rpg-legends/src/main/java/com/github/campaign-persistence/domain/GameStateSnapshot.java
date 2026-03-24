// With the use of AI
// The Memento Object

package com.github.domain;

/**
 * The GameStateSnapshot class provides a snapshot of the game state,
 * including location, party, inventory, and room data.
 * It is similar to GameStateMemento but may include additional context like location type.
 */
public class GameStateSnapshot {
    private int userID;
    private String locationType;
    private PartyData partyData;
    private InvData inventoryData;
    private RoomData roomData;

    /**
     * Constructs a GameStateSnapshot with the specified data.
     *
     * @param userID the ID of the user
     * @param locationType the type of the current location
     * @param partyData the current party data
     * @param inventoryData the current inventory data
     * @param roomData the current room data
     */
    public GameStateSnapshot(int userID, String locationType, PartyData partyData, InvData inventoryData, RoomData roomData) {
        this.userID = userID;
        this.locationType = locationType;
        this.partyData = partyData;
        this.inventoryData = inventoryData;
        this.roomData = roomData;
    }

    /**
     * Gets the party data from the snapshot.
     * @return the party data
     */
    public PartyData getPartyData() { return partyData; }

    /**
     * Gets the inventory data from the snapshot.
     * @return the inventory data
     */
    public InvData getInventoryData() { return inventoryData; }

    /**
     * Gets the room data from the snapshot.
     * @return the room data
     */
    public RoomData getRoomData() { return roomData; }

    /**
     * Gets the location type from the snapshot.
     * @return the location type
     */
    public String getLocationType() { return locationType; }
}
