package com.github.infrastructure;

public class CampaignPersistenceManager {
    private IDatabase database;

    public CampaignPersistenceManager(IDatabase database) {
        this.database = database;
    }

    public boolean saveGameState(PartyData party, InvData inv, RoomData room) {
        // Logic to serialize data and call database.executeQuery()
        confirmSave();
        return true;
    }

    public GameStateSnapshot fetchSaveData(int userID) {
        // Logic to call database.fetchRecord() and reconstruct the Memento
        return new GameStateSnapshot(userID, "Inn", new PartyData(), new InvData(), new RoomData());
    }

    public boolean deleteSaveData(int userID) {
        return database.deleteRecord(userID);
    }

    public void confirmSave() {
        System.out.println("Save confirmed.");
    }
}
