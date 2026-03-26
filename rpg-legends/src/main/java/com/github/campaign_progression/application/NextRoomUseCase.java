package com.github.campaign_progression.application;

import com.github.campaign_progression.domain.CampaignManager;
import com.github.campaign_progression.domain.Room;
import com.github.campaign_progression.application.dto.NextRoomDTO;

/**
 * Use case for advancing the campaign to the next room.
 *
 * <p>Handles:</p>
 * <ul>
 *     <li>Moving the campaign state forward via {@link CampaignManager#nextRoom()}</li>
 *     <li>Resetting the end-of-room flag for the new room</li>
 *     <li>Returning a {@link NextRoomDTO} describing the new room</li>
 * </ul>
 */
public class NextRoomUseCase {

    private final CampaignManager campaign;

    /**
     * Constructs the use case.
     *
     * @param campaign the campaign manager; must not be null
     */
    public NextRoomUseCase(CampaignManager campaign) {
        if (campaign == null) throw new IllegalArgumentException("CampaignManager cannot be null");
        this.campaign = campaign;
    }

    /**
     * Advances to the next room in the campaign.
     *
     * @return a {@link NextRoomDTO} containing the new room type and room counter
     */
    public NextRoomDTO execute() {
        Room nextRoom = campaign.nextRoom();

        // Reset end-of-room flag for the new room
        campaign.setEndOfRoom(false);

        return new NextRoomDTO(nextRoom.getClass().getSimpleName(), campaign.getRoomCounter());
    }
}