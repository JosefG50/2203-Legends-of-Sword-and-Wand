
package com.github.domain;

public interface CampaignState {
    boolean canExit();
    void handleExitRequest();
}
