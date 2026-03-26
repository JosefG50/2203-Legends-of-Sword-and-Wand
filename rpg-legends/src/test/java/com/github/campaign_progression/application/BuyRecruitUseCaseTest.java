package com.github.campaign_progression.application;

import com.github.campaign_progression.application.dto.BuyRecruitResponseDTO;
import com.github.campaign_progression.domain.*;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BuyRecruitUseCaseTest {

    // ---------- Helper: Create Hero ----------
    private HeroState createHero(String name, int level) {
        HeroState hero = new HeroState();
        hero.setName(name);
        hero.setLevel(level); // fixed typo
        hero.setSpecialization("MAGE");
        hero.setLvlUpExp(100); // if exists
        return hero;
    }

    // ---------- Fake Inn for controlled recruit ----------
    private Inn createInnReturning(HeroState hero) {
        return new Inn() {
            @Override
            public Optional<HeroState> generateRecruit(PartyService party, int room) {
                return Optional.ofNullable(hero);
            }
        };
    }

    private InventoryService createInventory(int gold) {
        return new InventoryService(gold); // pass starting gold
    }

    // ---------- TESTS ----------

    @Test
    void execute_roomAfter10_returnsFailure() {
        BuyRecruitUseCase useCase = new BuyRecruitUseCase(
                new Inn(),
                new PartyService(),
                createInventory(1000)
        );

        BuyRecruitResponseDTO result = useCase.execute(11);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("no longer available"));
    }

    @Test
    void execute_partyFull_returnsFailure() {
        PartyService party = new PartyService();

        for (int i = 0; i < 6; i++) {
            party.addHero(createHero("H" + i, 1));
        }

        BuyRecruitUseCase useCase = new BuyRecruitUseCase(
                new Inn(),
                party,
                createInventory(1000)
        );

        BuyRecruitResponseDTO result = useCase.execute(1);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("Party is full"));
    }

    @Test
    void execute_noRecruitAvailable_returnsFailure() {
        Inn inn = new Inn() {
            @Override
            public Optional<HeroState> generateRecruit(PartyService party, int room) {
                return Optional.empty();
            }
        };

        BuyRecruitUseCase useCase = new BuyRecruitUseCase(
                inn,
                new PartyService(),
                createInventory(1000)
        );

        BuyRecruitResponseDTO result = useCase.execute(1);

        assertFalse(result.isSuccess());
        assertEquals("No recruit available", result.getMessage());
    }

    @Test
    void execute_notEnoughGold_returnsFailure() {
        HeroState hero = createHero("Hero", 3); // cost = 600 if formula = 200*level

        BuyRecruitUseCase useCase = new BuyRecruitUseCase(
                createInnReturning(hero),
                new PartyService(),
                createInventory(100) // not enough
        );

        BuyRecruitResponseDTO result = useCase.execute(1);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessage().contains("Not enough gold"));
    }

    @Test
    void execute_levelOneHero_freeRecruit_success() {
        HeroState hero = createHero("Hero", 1);

        BuyRecruitUseCase useCase = new BuyRecruitUseCase(
                createInnReturning(hero),
                new PartyService(),
                createInventory(0)
        );

        BuyRecruitResponseDTO result = useCase.execute(1);

        assertTrue(result.isSuccess());
        assertEquals("Hero", result.getHeroName());
        assertEquals(1, result.getLevel());
    }

    @Test
    void execute_validPaidRecruit_success() {
        HeroState hero = createHero("Hero", 2); // cost = 400

        InventoryService inv = createInventory(1000);

        BuyRecruitUseCase useCase = new BuyRecruitUseCase(
                createInnReturning(hero),
                new PartyService(),
                inv
        );

        BuyRecruitResponseDTO result = useCase.execute(1);

        assertTrue(result.isSuccess());
        assertEquals("Hero", result.getHeroName());
        assertEquals(2, result.getLevel());
    }
}