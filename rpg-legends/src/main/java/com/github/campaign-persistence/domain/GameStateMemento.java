package com.github.domain;

/**
 * The GameStateMemento class stores the internal state of the game at a specific point in time.
 * It is used as part of the Memento pattern to allow for saving and restoring the game state.
 */
public class GameStateMemento {
    private int userID;
    private PartyData partyStats;
    private InvData inventoryData;
    private RoomData roomProgress;

    /**
     * Constructs a GameStateMemento with the specified user ID and game data.
     *
     * @param userID the ID of the user
     * @param partyStats the current party statistics
     * @param inventoryData the current inventory data
     * @param roomProgress the current room progress
     */
    public GameStateMemento(int userID, PartyData partyStats, InvData inventoryData, RoomData roomProgress) {
        this.userID = userID;
        this.partyStats = partyStats;
        this.inventoryData = inventoryData;
        this.roomProgress = roomProgress;
    }

    /**
     * Gets the user ID associated with this memento.
     * @return the user ID
     */
    public int getUserID() { return userID; }

    /**
     * Gets the party statistics stored in this memento.
     * @return the party statistics
     */
    public PartyData getPartyStats() { return partyStats; }

    /**
     * Gets the inventory data stored in this memento.
     * @return the inventory data
     */
    public InvData getInventoryData() { return inventoryData; }

    /**
     * Gets the room progress stored in this memento.
     * @return the room progress
     */
    public RoomData getRoomProgress() { return roomProgress; }
}
