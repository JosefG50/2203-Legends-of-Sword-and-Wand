package com.github.campaign_progression.application;

import com.github.campaign_progression.application.dto.HeroInstanceDTO;
import com.github.campaign_progression.domain.HeroState;
import com.github.campaign_progression.domain.PartyService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetPartyUseCaseTest {

    @Test
    void execute_returnsAllPartyMembersAsDTO() {
        PartyService partyService = mock(PartyService.class);

        HeroState hero1 = mock(HeroState.class);
        when(hero1.getName()).thenReturn("Alice");
        when(hero1.getLevel()).thenReturn(2);

        HeroState hero2 = mock(HeroState.class);
        when(hero2.getName()).thenReturn("Bob");
        when(hero2.getLevel()).thenReturn(3);

        when(partyService.getParty()).thenReturn(List.of(hero1, hero2));

        GetPartyUseCase useCase = new GetPartyUseCase(partyService);

        List<HeroInstanceDTO> result = useCase.execute();

        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).getName());
        assertEquals(2, result.get(0).getLevel());
        assertEquals("Bob", result.get(1).getName());
        assertEquals(3, result.get(1).getLevel());
    }

    @Test
    void execute_emptyParty_returnsEmptyList() {
        PartyService partyService = mock(PartyService.class);
        when(partyService.getParty()).thenReturn(List.of());

        GetPartyUseCase useCase = new GetPartyUseCase(partyService);

        List<HeroInstanceDTO> result = useCase.execute();
        assertTrue(result.isEmpty());
    }
}