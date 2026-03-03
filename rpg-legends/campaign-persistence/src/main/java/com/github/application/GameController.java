// With the use of AI
package com.github.application;

import com.github.domain.*;
import com.github.infrastructure.*;
import com.github.ui.*;


import persistence.*;
import persistence.memento.*;
import state.*;
import factory.*;

public class GameController {
    private CampaignPersistenceManager persistenceManager;
    private CampaignState currentState; 
    private ViewCreator viewFactory;
    
    // Dependencies from teammates (These would be passed in via constructor)
    private PartyManager partyManager;
    private Inventory inventory;
    private CampaignManager campaignManager;

    public GameController() {
        // Initialize your components
        // this.persistenceManager = new CampaignPersistenceManager(new SqlDatabase());
        this.viewFactory = new CampaignViewFactory();
        this.currentState = new ExplorationState(); // Default state
    }

    // --- USE CASE: EXIT PVE CAMPAIGN ---
    public void requestExit() {
        if (!currentState.canExit()) {
            currentState.handleExitRequest(); // Rejects exit
            return;
        }

        // 1. Get Data from Subsystems
        PartyData pData = partyManager.getPartyStatus();
        InvData iData = inventory.getInventoryData();
        RoomData rData = campaignManager.getCurrentRoomInfo();

        // 2. Pass to Persistence Manager
        boolean success = persistenceManager.saveGameState(pData, iData, rData);

        if (success) {
            System.out.println("Campaign Closed. Display Main Menu.");
        }
    }

    // --- USE CASE: CONTINUE PVE CAMPAIGN ---
    public void loadSavedGame(int userID) {
        // 1. Fetch Snapshot from DB
        GameStateSnapshot snapshot = persistenceManager.fetchSaveData(userID);

        // 2. Restore Subsystems
        partyManager.restoreParty(snapshot.getPartyData());
        // inventory.initialize(snapshot.getInventoryData()); // If teammate adds this
        campaignManager.restoreProgress(snapshot.getRoomData());

        // 3. Dynamically build View using Factory
        viewFactory.renderLocation(snapshot.getLocationType());
    }
    
    // --- UTILITY ---
    public boolean deleteSavedGame(int userID) {
        return persistenceManager.deleteSaveData(userID);
    }
    
    public void setGameState(CampaignState newState) {
        this.currentState = newState;
    }
}
