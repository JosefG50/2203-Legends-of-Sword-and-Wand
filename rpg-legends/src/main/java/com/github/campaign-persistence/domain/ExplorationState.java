// With the use of AI
package com.github.domain;

public class ExplorationState implements CampaignState {
    @Override
    public boolean canExit() {
        return true;
    }

    @Override
    public void handleExitRequest() {
        System.out.println("Safe to exit. Proceeding to save...");
    }
}
