package com.github.application;

import com.github.domain.CampaignManager;
import com.github.domain.Room;
import com.github.application.dto.NextRoomDTO;

public class NextRoomUseCase {

    private final CampaignManager campaign;

    public NextRoomUseCase(CampaignManager campaign) {
        if (campaign == null) throw new IllegalArgumentException("CampaignManager cannot be null");
        this.campaign = campaign;
    }

    /**
     * Move to the next room in the campaign and return a DTO.
     */
    public NextRoomDTO execute() {
        Room nextRoom = campaign.nextRoom();

        // Reset end-of-room flag for the new room
        campaign.setEndOfRoom(false);

        return new NextRoomDTO(nextRoom.getClass().getSimpleName(), campaign.getRoomCounter());
    }
}