package com.github.infrastructure;

import com.github.domain.*;
import com.google.gson.Gson;

public class CampaignPersistenceManager implements CampaignSaveRepository {

    private IDatabase database;
    private Gson gson;

    public CampaignPersistenceManager(IDatabase database) {
        this.database = database;
        this.gson = new Gson();
    }

    @Override
    public boolean saveSnapshot(int userId, GameStateMemento snapshot) {
        String jsonData = gson.toJson(snapshot);

        String query = "REPLACE INTO campaign_saves (userId, save_data) VALUES (?, ?)";

        boolean success = database.executeUpdate(query, userId, jsonData);
        if (success) {
            confirmSave();
        }
        return success;
    }

    @Override
    public GameStateMemento loadSnapshot(int userId) {
        Object record = database.fetchRecord(userId);
        if (record != null) {
            try {
                String jsonData = (String) record;
                return gson.fromJson(jsonData, GameStateMemento.class);
            } catch (Exception e) {
                System.err.println("Error parsing snapshot for User ID " + userId + ": " + e.getMessage());
            }
        }
        return null;
    }

    @Override
    public boolean deleteSnapshot(int userId) {
        return database.deleteRecord(userId);
    }

    public void confirmSave() {
        System.out.println("Save confirmed successfully with the database.");
    }
}
