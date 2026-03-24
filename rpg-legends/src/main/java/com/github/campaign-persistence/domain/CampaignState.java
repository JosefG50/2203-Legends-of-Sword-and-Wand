// With the use of AI
package com.github.domain;

/**
 * The CampaignState interface defines the state-dependent behavior for a campaign.
 * It is part of the State pattern, allowing the campaign to change its behavior
 * based on its current state (e.g., Exploration, Combat).
 */
public interface CampaignState {
    /**
     * Checks if the campaign can be exited in the current state.
     *
     * @return true if exit is possible, false otherwise
     */
    boolean canExit();

    /**
     * Handles a request to exit the campaign.
     * This method defines what happens when an exit request is made
     * while in the current state (e.g., showing a message if exit is not allowed).
     */
    void handleExitRequest();
}
