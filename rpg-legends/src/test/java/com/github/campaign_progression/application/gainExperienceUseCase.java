package com.github.campaign_progression.application;

import com.github.campaign_progression.application.dto.HeroExpDTO;
import com.github.campaign_progression.domain.HeroState;
import com.github.campaign_progression.domain.PartyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GainExpUseCaseTest {

    private PartyService partyService;
    private GainExpUseCase useCase;
    private HeroState hero1;
    private HeroState hero2;

    @BeforeEach
    void setup() {
        partyService = new PartyService();
        useCase = new GainExpUseCase(partyService);

        hero1 = new HeroState();
        hero1.setName("Alice");
        hero1.setCurExp(0);
        hero1.setLvlUpExp(100);
        hero1.setSpecialization("MAGE");

        hero2 = new HeroState();
        hero2.setName("Bob");
        hero2.setCurExp(10);
        hero2.setLvlUpExp(100);
        hero2.setSpecialization("WARRIOR");

        partyService.addHero(hero1);
        partyService.addHero(hero2);
    }

    @Test
    void execute_evenlyDistributesExp() {
        List<HeroExpDTO> result = useCase.execute(40);

        assertEquals(2, result.size());

        HeroExpDTO dto1 = result.get(0);
        HeroExpDTO dto2 = result.get(1);

        assertEquals("Alice", dto1.getHeroName());
        assertEquals(20, dto1.getExpGained());
        assertEquals(20, dto1.getNewTotalExp());

        assertEquals("Bob", dto2.getHeroName());
        assertEquals(20, dto2.getExpGained());
        assertEquals(30, dto2.getNewTotalExp());
    }

    @Test
    void execute_partyEmpty_throws() {
        PartyService emptyParty = new PartyService();
        GainExpUseCase emptyUseCase = new GainExpUseCase(emptyParty);

        Exception ex = assertThrows(IllegalStateException.class, () -> emptyUseCase.execute(50));
        assertEquals("No party members", ex.getMessage());
    }

    @Test
    void execute_expNotDivisible_evenFloorDivision() {
        List<HeroExpDTO> result = useCase.execute(5); // 5 / 2 = 2

        assertEquals(2, result.size());

        assertEquals(2, result.get(0).getExpGained());
        assertEquals(2, result.get(0).getNewTotalExp());

        assertEquals(2, result.get(1).getExpGained());
        assertEquals(12, result.get(1).getNewTotalExp());
    }
}