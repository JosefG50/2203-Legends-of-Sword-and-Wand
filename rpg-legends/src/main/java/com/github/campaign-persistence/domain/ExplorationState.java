// With the use of AI
package com.github.domain;

/**
 * The ExplorationState class represents the game state when the player is exploring.
 * In this state, exiting the campaign is allowed and safe.
 */
public class ExplorationState implements CampaignState {
    /**
     * Checks if the campaign can be exited during exploration.
     *
     * @return true, as it is safe to exit during exploration
     */
    @Override
    public boolean canExit() {
        return true;
    }

    /**
     * Handles a request to exit the campaign during exploration.
     * Prints a message indicating that it is safe to proceed with exiting.
     */
    @Override
    public void handleExitRequest() {
        System.out.println("Safe to exit. Proceeding to save...");
    }
}
