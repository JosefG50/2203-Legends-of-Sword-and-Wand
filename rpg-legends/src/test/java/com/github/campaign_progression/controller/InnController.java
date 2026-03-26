package com.github.campaign_progression.controller;

import com.github.campaign_progression.application.*;
import com.github.campaign_progression.application.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CampaignControllerTest {

    private StartCampaignUseCase startCampaignUseCase;
    private GetPartyUseCase getPartyUseCase;
    private GainExpUseCase gainExpUseCase;
    private GainGoldUseCase gainGoldUseCase;
    private CampaignController controller;

    @BeforeEach
    void setUp() {
        startCampaignUseCase = mock(StartCampaignUseCase.class);
        getPartyUseCase = mock(GetPartyUseCase.class);
        gainExpUseCase = mock(GainExpUseCase.class);
        gainGoldUseCase = mock(GainGoldUseCase.class);

        controller = new CampaignController(
                startCampaignUseCase,
                mock(LoadCampaignUseCase.class),
                mock(ExitCampaignUseCase.class),
                mock(NextRoomUseCase.class),
                mock(EndCampaignUseCase.class),
                getPartyUseCase,
                mock(GetInventoryUseCase.class),
                mock(BuyItemUseCase.class),
                mock(BuyRecruitUseCase.class),
                mock(GenerateBattleRoomUseCase.class),
                mock(GetShopUseCase.class),
                mock(InnInitializeUseCase.class),
                mock(ItemConsumeUseCase.class),
                mock(ViewRecruitUseCase.class),
                mock(InitializeBattleUseCase.class),
                mock(SaveCampaignToPvpUseCase.class),
                gainExpUseCase,
                gainGoldUseCase
        );
    }

    @Test
    void testStartCampaign_returnsDTO() {
        HeroInstanceDTO heroDTO = new HeroInstanceDTO();
        StartCampaignDTO expectedDTO = new StartCampaignDTO();
        when(startCampaignUseCase.execute(heroDTO)).thenReturn(expectedDTO);

        StartCampaignDTO result = controller.startCampaign(heroDTO);

        assertEquals(expectedDTO, result);
        verify(startCampaignUseCase).execute(heroDTO);
    }

    @Test
    void testGetParty_returnsList() {
        List<HeroInstanceDTO> expectedParty = List.of(new HeroInstanceDTO());
        when(getPartyUseCase.execute()).thenReturn(expectedParty);

        List<HeroInstanceDTO> result = controller.getParty();

        assertEquals(expectedParty, result);
        verify(getPartyUseCase).execute();
    }
    
    
}