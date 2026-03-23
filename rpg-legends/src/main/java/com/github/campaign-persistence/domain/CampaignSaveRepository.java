package com.github.domain;

// Repository Interface for Dependency Inversion
public interface CampaignSaveRepository {
    boolean saveSnapshot(int userId, GameStateMemento snapshot);
    GameStateMemento loadSnapshot(int userId);
    boolean deleteSnapshot(int userId);
}