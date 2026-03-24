package com.github.application;

import com.github.domain.*;

/**
 * The GameStateManager class acts as the Originator in the Memento pattern.
 * It is responsible for creating snapshots of the current game state and
 * restoring the game state from a given memento.
 */
public class GameStateManager {
    // Mocking domain subsystems that your teammates would build
    private PartyManager partyManager;
    private InventoryManager inventoryManager;
    private CampaignManager campaignManager;

    /**
     * Constructs a GameStateManager with the required domain managers.
     *
     * @param p the PartyManager to manage party data
     * @param i the InventoryManager to manage inventory data
     * @param c the CampaignManager to manage campaign progress
     */
    public GameStateManager(PartyManager p, InventoryManager i, CampaignManager c) {
        this.partyManager = p;
        this.inventoryManager = i;
        this.campaignManager = c;
    }

    /**
     * Creates a snapshot of the current game state for a specific user.
     *
     * @param userId the ID of the user for whom the snapshot is created
     * @return a GameStateMemento containing the current state of the game
     */
    public GameStateMemento createSnapshot(int userId) {
        // Fetch the DTOs directly from the domain managers
        PartyData pData = partyManager.getPartyStatus();
        InvData iData = inventoryManager.getInventoryData();
        RoomData rData = campaignManager.getCurrentRoomInfo();

        // Package them into the Memento
        return new GameStateMemento(userId, pData, iData, rData);
    }

    /**
     * Restores the game state from a given memento.
     * Unpacks the memento and distributes the data back to the relevant subsystems.
     *
     * @param memento the GameStateMemento from which to restore the game state
     */
    public void restore(GameStateMemento memento) {
        // Unpack the memento and distribute the DTOs back to the subsystems
        partyManager.restoreParty(memento.getPartyStats());
        inventoryManager.restoreInventory(memento.getInventoryData());
        campaignManager.restoreProgress(memento.getRoomProgress());
        
        System.out.println("Restoring game state for user: " + memento.getUserID());
    }

    /**
     * Fetches the current location type from the campaign manager.
     *
     * @return a string representing the current room's location type
     */
    public String getCurrentRoom() {
        return campaignManager.getCurrentLocationType();
    }
}

/**
 * Interface for the PartyManager subsystem.
 */
interface PartyManager { 
    /**
     * Gets the current status of the party.
     * @return the party status data
     */
    PartyData getPartyStatus(); 
    /**
     * Restores the party status from the given data.
     * @param data the party status data to restore
     */
    void restoreParty(PartyData data); 
}

/**
 * Interface for the InventoryManager subsystem.
 */
interface InventoryManager { 
    /**
     * Gets the current inventory data.
     * @return the inventory data
     */
    InvData getInventoryData(); 
    /**
     * Restores the inventory from the given data.
     * @param data the inventory data to restore
     */
    void restoreInventory(InvData data); 
}

/**
 * Interface for the CampaignManager subsystem.
 */
interface CampaignManager { 
    /**
     * Gets the current room information.
     * @return the current room data
     */
    RoomData getCurrentRoomInfo(); 
    /**
     * Gets the current location type.
     * @return the current location type as a string
     */
    String getCurrentLocationType(); 
    /**
     * Restores the campaign progress from the given data.
     * @param data the room data to restore progress from
     */
    void restoreProgress(RoomData data); 
}
