package com.github.application;

import com.github.domain.CampaignManager;
import com.github.domain.Room;

public class NextRoomUseCase {

    private final CampaignManager campaign;

    public NextRoomUseCase(CampaignManager campaign) {
        if (campaign == null) throw new IllegalArgumentException("CampaignManager cannot be null");
        this.campaign = campaign;
    }

    /**
     * Move to the next room in the campaign.
     *
     * @return the class name of the next room for the application layer
     */
    public String execute() {
        Room nextRoom = campaign.nextRoom();

        // Optionally mark the end of the previous room
        campaign.setEndOfRoom(false); // reset end-of-room status

        return nextRoom.getClass().getSimpleName(); // "Inn", "BattleRoom", etc.
    }
}