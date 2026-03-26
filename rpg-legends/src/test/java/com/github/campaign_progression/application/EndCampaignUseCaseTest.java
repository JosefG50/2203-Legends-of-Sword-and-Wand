package com.github.campaign_progression.application;

import com.github.campaign_progression.application.dto.CampaignEndDTO;
import com.github.campaign_progression.domain.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EndCampaignUseCaseTest {

    // ---------- Helper to create hero ----------
    private HeroState createHero(String name, int level) {
        HeroState hero = new HeroState();
        hero.setName(name);
        hero.setLevel(level); // fixed typo
        hero.setSpecialization("MAGE"); // safer for domain logic
        hero.setLvlUpExp(100);
        return hero;
    }

    private InventoryService createInventory(int startingGold) {
        return new InventoryService(startingGold);
    }

    // ---------- TESTS ----------

    @Test
    void execute_returnsCorrectDTO() {
        PartyService party = new PartyService();
        party.addHero(createHero("Hero1", 2));
        party.addHero(createHero("Hero2", 3));

        InventoryService inv = createInventory(100);

        EndCampaignUseCase useCase = new EndCampaignUseCase(party, inv);

        CampaignEndDTO result = useCase.execute();

        assertNotNull(result);
        assertEquals(2, result.getPartySummary().size()); // updated
        assertEquals("Campaign complete! Your final results are ready.", result.getMessage());
    }

    @Test
    void execute_calculatesScoreCorrectly() {
        PartyService party = new PartyService();
        party.addHero(createHero("Hero1", 2)); // totalLevels = 2
        party.addHero(createHero("Hero2", 3)); // totalLevels = 5

        InventoryService inv = createInventory(100); // starting gold

        EndCampaignUseCase useCase = new EndCampaignUseCase(party, inv);

        CampaignEndDTO result = useCase.execute();

        // Example formula: totalLevels*100 + extraGold + goldSpent/2*10
        int expectedScore = 5 * 100 + 100 + 0; // 600
        assertEquals(expectedScore, result.getTotalScore());
    }

    @Test
    void execute_emptyParty_returnsEmptyList() {
        PartyService party = new PartyService();
        InventoryService inv = createInventory(0);

        EndCampaignUseCase useCase = new EndCampaignUseCase(party, inv);

        CampaignEndDTO result = useCase.execute();

        assertTrue(result.getPartySummary().isEmpty()); // updated
    }
}