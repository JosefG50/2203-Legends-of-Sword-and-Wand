package com.github.domain;

public class ExplorationState implements CampaignState {
    @Override
    public boolean handleExitRequest() {
        System.out.println("Safe to exit. Proceeding to save...");
        return true;
    }
}
