package com.github.application;

import com.github.domain.*;

// Acts as the Originator to safely package and unpack subsystem data
public class GameStateManager {
    // Mocking domain subsystems that your teammates would build
    private PartyManager partyManager;
    private InventoryManager inventoryManager;
    private CampaignManager campaignManager;

    public GameStateManager(PartyManager p, InventoryManager i, CampaignManager c) {
        this.partyManager = p;
        this.inventoryManager = i;
        this.campaignManager = c;
    }

    public GameStateMemento createSnapshot(int userId) {
        // Fetch the DTOs directly from the domain managers
        PartyData pData = partyManager.getPartyStatus();
        InvData iData = inventoryManager.getInventoryData();
        RoomData rData = campaignManager.getCurrentRoomInfo();

        // Package them into the Memento
        return new GameStateMemento(userId, pData, iData, rData);
    }

    public void restore(GameStateMemento memento) {
        // Unpack the memento and distribute the DTOs back to the subsystems
        partyManager.restoreParty(memento.getPartyStats());
        inventoryManager.restoreInventory(memento.getInventoryData());
        campaignManager.restoreProgress(memento.getRoomProgress());
        
        System.out.println("Restoring game state for user: " + memento.getUserID());
    }

    // Fetches the current location so the Facade can update the UI
    public String getCurrentRoom() {
        return campaignManager.getCurrentLocationType();
    }
}

// Interfaces for teammates' modules (Placeholders to ensure compilation)
interface PartyManager { PartyData getPartyStatus(); void restoreParty(PartyData data); }
interface InventoryManager { InvData getInventoryData(); void restoreInventory(InvData data); }
interface CampaignManager { RoomData getCurrentRoomInfo(); String getCurrentLocationType(); void restoreProgress(RoomData data); }
