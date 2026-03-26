package com.github.campaign_progression.application;

import com.github.campaign_progression.application.dto.NextRoomDTO;
import com.github.campaign_progression.domain.CampaignManager;
import com.github.campaign_progression.domain.Room;
import com.github.campaign_progression.domain.Inn;
import com.github.campaign_progression.domain.BattleRoom;
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
    void execute_advancesToNextRoom_returnsCorrectDTO() {
        Room nextRoom = mock(BattleRoom.class);
        when(campaign.nextRoom()).thenReturn(nextRoom);
        when(campaign.getRoomCounter()).thenReturn(3);

        NextRoomDTO dto = useCase.execute();

        // Verify campaign advanced
        verify(campaign).nextRoom();
        // Verify end-of-room flag reset
        verify(campaign).setEndOfRoom(false);

        assertEquals("BattleRoom", dto.getRoomType());
        assertEquals(3, dto.getRoomCounter());
    }

    @Test
    void execute_withInnRoom_returnsCorrectDTO() {
        Room nextRoom = mock(Inn.class);
        when(campaign.nextRoom()).thenReturn(nextRoom);
        when(campaign.getRoomCounter()).thenReturn(5);

        NextRoomDTO dto = useCase.execute();

        verify(campaign).nextRoom();
        verify(campaign).setEndOfRoom(false);

        assertEquals("Inn", dto.getRoomType());
        assertEquals(5, dto.getRoomCounter());
    }

    @Test
    void constructor_nullCampaign_throwsException() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> new NextRoomUseCase(null));
        assertEquals("CampaignManager cannot be null", ex.getMessage());
    }
}