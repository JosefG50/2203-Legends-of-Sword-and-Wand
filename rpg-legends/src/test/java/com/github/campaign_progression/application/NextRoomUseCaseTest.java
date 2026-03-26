package com.github.campaign_progression.application;

import com.github.campaign_progression.domain.CampaignManager;
import com.github.campaign_progression.domain.Room;
import com.github.campaign_progression.application.dto.NextRoomDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class NextRoomUseCaseTest {

    private CampaignManager campaign;
    private NextRoomUseCase useCase;

    @BeforeEach
    void setup() {
        campaign = mock(CampaignManager.class);
        useCase = new NextRoomUseCase(campaign);
    }

    @Test
    void execute_movesToNextRoomAndResetsEndOfRoomFlag() {
        Room mockRoom = mock(Room.class);
        when(mockRoom.getClass()).thenReturn(Room.class);
        when(campaign.nextRoom()).thenReturn(mockRoom);
        when(campaign.getRoomCounter()).thenReturn(5);

        NextRoomDTO result = useCase.execute();

        // Verify campaign.nextRoom() called
        verify(campaign).nextRoom();
        // Verify end-of-room flag reset
        verify(campaign).setEndOfRoom(false);

        // Verify DTO content
        assertEquals("Room", result.roomType());
        assertEquals(5, result.roomCounter());
    }

    @Test
    void constructor_throwsIfCampaignIsNull() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new NextRoomUseCase(null));
        assertEquals("CampaignManager cannot be null", ex.getMessage());
    }
}