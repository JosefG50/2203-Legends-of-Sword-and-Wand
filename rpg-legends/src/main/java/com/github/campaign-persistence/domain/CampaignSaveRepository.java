package com.github.domain;

/**
 * The CampaignSaveRepository interface defines the contract for persisting
 * and retrieving game state snapshots. It follows the Repository pattern
 * to decouple the domain layer from the infrastructure layer.
 */
public interface CampaignSaveRepository {
    /**
     * Saves a snapshot of the game state for the given user ID.
     *
     * @param userId the ID of the user whose snapshot is being saved
     * @param snapshot the GameStateMemento representing the game state
     * @return true if the snapshot was saved successfully, false otherwise
     */
    boolean saveSnapshot(int userId, GameStateMemento snapshot);

    /**
     * Loads a snapshot of the game state for the given user ID.
     *
     * @param userId the ID of the user whose snapshot is being loaded
     * @return the GameStateMemento representing the saved game state, or null if not found
     */
    GameStateMemento loadSnapshot(int userId);

    /**
     * Deletes the saved game state snapshot for the given user ID.
     *
     * @param userId the ID of the user whose snapshot is being deleted
     * @return true if the deletion was successful, false otherwise
     */
    boolean deleteSnapshot(int userId);
}
