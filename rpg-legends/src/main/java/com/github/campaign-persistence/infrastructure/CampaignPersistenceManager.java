package com.github.infrastructure;

import com.github.domain.IDatabase;
import com.github.domain.PartyData;
import com.github.domain.InvData;
import com.github.domain.RoomData;
import com.github.domain.GameStateSnapshot;

/**
 * The CampaignPersistenceManager class handles the persistence of game states.
 * It provides methods to save, fetch, and delete game data using a database.
 */
public class CampaignPersistenceManager {
    private IDatabase database;

    /**
     * Constructs a CampaignPersistenceManager with the specified database.
     *
     * @param database the database implementation to use for persistence
     */
    public CampaignPersistenceManager(IDatabase database) {
        this.database = database;
    }

    /**
     * Saves the current game state for a party, including their inventory and room progress.
     *
     * @param party the party data to save
     * @param inv the inventory data to save
     * @param room the room data to save
     * @return true if the game state was saved successfully, false otherwise
     */
    public boolean saveGameState(PartyData party, InvData inv, RoomData room) {
        // Logic to serialize data and call database.executeQuery()
        confirmSave();
        return true;
    }

    /**
     * Fetches the saved game data for the given user ID.
     *
     * @param userID the ID of the user whose data is to be fetched
     * @return a GameStateSnapshot containing the restored game state
     */
    public GameStateSnapshot fetchSaveData(int userID) {
        // Logic to call database.fetchRecord() and reconstruct the Memento
        return new GameStateSnapshot(userID, "Inn", new PartyData(), new InvData(), new RoomData());
    }

    /**
     * Deletes the saved game data for the given user ID.
     *
     * @param userID the ID of the user whose data is to be deleted
     * @return true if the data was deleted successfully, false otherwise
     */
    public boolean deleteSaveData(int userID) {
        return database.deleteRecord(userID);
    }

    /**
     * Confirms that a save operation was successful.
     * Prints a confirmation message to the console.
     */
    public void confirmSave() {
        System.out.println("Save confirmed.");
    }
}
