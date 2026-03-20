package com.github.application;

import com.github.domain.*;
import com.github.ui.ViewCreator;
import com.github.ui.CampaignViewFactory;

// Acts as the Facade and Caretaker
public class GameController {
    private CampaignSaveRepository persistenceManager;
    private CampaignState currentState;
    private ViewCreator viewFactory;
    private GameStateManager gameStateManager;

    public GameController(CampaignSaveRepository persistenceManager, GameStateManager gameStateManager) {
        this.persistenceManager = persistenceManager;
        this.gameStateManager = gameStateManager;
        this.viewFactory = new CampaignViewFactory();
        this.currentState = new ExplorationState(); // Default state
    }

    // Use Case: Exit PvE Campaign
    public void requestExit(int userId) {
        // State Pattern: Let the state handle the request and tell us if we can proceed
        // CombatState returns false, ExplorationState returns true
        if (!currentState.handleExitRequest()) {
            // The state itself handles printing "Cannot exit during battle"
            return; 
        }

        // Memento Pattern: Create snapshot via Originator
        GameStateMemento memento = gameStateManager.createSnapshot(userId);

        // Persist to Database
        boolean success = persistenceManager.saveSnapshot(userId, memento);

        if (success) {
            System.out.println("Campaign Closed. Displaying Main Menu.");
        } else {
            System.err.println("Failed to save campaign. Please try again.");
        }
    }

    // Use Case: Continue Incomplete PvE Campaign
    public void loadSavedGame(int userId) {
        // Fetch snapshot from DB
        GameStateMemento memento = persistenceManager.loadSnapshot(userId);

        if (memento != null) {
            // Restore state to domain managers FIRST
            gameStateManager.restore(memento);

            // Fetch the current room from the domain AFTER it has been restored,
            // preserving strict Memento encapsulation.
            String currentRoom = gameStateManager.getCurrentRoom(); 

            // Factory Pattern: Dynamically build View
            viewFactory.renderLocation(currentRoom);
        } else {
            System.err.println("No saved campaign found for User ID: " + userId);
        }
    }

    public void setCampaignState(CampaignState state) {
        this.currentState = state;
    }
}
