
package com.github.domain;

public class CombatState implements CampaignState {
    @Override
    public boolean canExit() {
        return false;
    }

    @Override
    public void handleExitRequest() {
        System.out.println("Cannot exit during battle!");
        // Return error to UI
    }
}
