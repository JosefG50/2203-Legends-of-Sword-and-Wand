package com.github.domain;

public class CombatState implements CampaignState {
    @Override
    public boolean handleExitRequest() {
        // Returns error to the UI blocking the save process
        System.out.println("Cannot exit during battle!");
        return false; 
    }
}
