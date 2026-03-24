// With the use of AI
package com.github.domain;

/**
 * The CombatState class represents the game state when the player is in combat.
 * In this state, exiting the campaign is typically not allowed until the combat is resolved.
 */
public class CombatState implements CampaignState {
    /**
     * Checks if the campaign can be exited during combat.
     *
     * @return false, as exiting is not allowed during battle
     */
    @Override
    public boolean canExit() {
        return false;
    }

    /**
     * Handles a request to exit the campaign during combat.
     * Prints a message indicating that exiting is not allowed.
     */
    @Override
    public void handleExitRequest() {
        System.out.println("Cannot exit during battle!");
        // Return error to UI
    }
}
