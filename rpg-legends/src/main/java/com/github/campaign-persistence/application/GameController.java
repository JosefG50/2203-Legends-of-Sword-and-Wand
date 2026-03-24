// With the use of AI
package com.github.application;

import com.github.domain.*;
import com.github.infrastructure.*;
import com.github.ui.*;


import persistence.*;
import persistence.memento.*;
import state.*;
import factory.*;

/**
 * The GameController class manages the high-level game logic and state transitions.
 * It coordinates between the domain, infrastructure, and UI layers to handle
 * user requests like exiting and loading games.
 */
public class GameController {
    private CampaignPersistenceManager persistenceManager;
    private CampaignState currentState; 
    private ViewCreator viewFactory;
    
    // Dependencies from teammates (These would be passed in via constructor)
    private PartyManager partyManager;
    private Inventory inventory;
    private CampaignManager campaignManager;

    /**
     * Constructs a new GameController and initializes its components.
     * Sets the default state to ExplorationState.
     */
    public GameController() {
        // Initialize your components
        // this.persistenceManager = new CampaignPersistenceManager(new SqlDatabase());
        this.viewFactory = new CampaignViewFactory();
        this.currentState = new ExplorationState(); // Default state
    }

    /**
     * Handles the request to exit the PvE campaign.
     * If the current state allows exiting, it gathers data from subsystems,
     * saves the game state via the persistence manager, and prints a success message.
     */
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

    /**
     * Loads a saved game for the given user ID.
     * Fetches save data from the persistence manager, restores subsystems,
     * and renders the appropriate location view.
     *
     * @param userID the ID of the user whose game is being loaded
     */
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
    
    /**
     * Deletes the saved game data for the given user ID.
     *
     * @param userID the ID of the user whose save data is to be deleted
     * @return true if the deletion was successful, false otherwise
     */
    public boolean deleteSavedGame(int userID) {
        return persistenceManager.deleteSaveData(userID);
    }
    
    /**
     * Sets the current game state.
     *
     * @param newState the new CampaignState to be set
     */
    public void setGameState(CampaignState newState) {
        this.currentState = newState;
    }
}
