package com.github.campaign_progression.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PartyServiceTest {

    // Helper method to create a properly initialized HeroState
    private HeroState createHero(String name, int hp, int mana, int level) {
        HeroState hero = new HeroState();
        hero.setName(name);
        hero.setMaxHp(hp);
        hero.setCurHp(hp);
        hero.setMaxMana(mana);
        hero.setCurMana(mana);
        hero.setLevel(level);

        // Needed for EXP system to not break
        hero.setLvlUpExp(100);

        return hero;
    }

    @Test
    void addHero_validHero_addedSuccessfully() {
        PartyService service = new PartyService();
        HeroState hero = createHero("Hero", 100, 50, 1);

        service.addHero(hero);

        assertEquals(1, service.getParty().size());
    }

    @Test
    void addHero_nullHero_throwsException() {
        PartyService service = new PartyService();

        assertThrows(IllegalArgumentException.class, () -> service.addHero(null));
    }

    @Test
    void addHero_whenFull_throwsException() {
        PartyService service = new PartyService();

        for (int i = 0; i < 6; i++) {
            service.addHero(createHero("H" + i, 100, 50, 1));
        }

        assertThrows(IllegalStateException.class,
                () -> service.addHero(createHero("Extra", 100, 50, 1)));
    }

    @Test
    void hasSpace_whenEmpty_returnsTrue() {
        PartyService service = new PartyService();

        assertTrue(service.hasSpace());
    }

    @Test
    void hasSpace_whenFull_returnsFalse() {
        PartyService service = new PartyService();

        for (int i = 0; i < 6; i++) {
            service.addHero(createHero("H" + i, 100, 50, 1));
        }

        assertFalse(service.hasSpace());
    }

    @Test
    void isDefeated_allHeroesDead_returnsTrue() {
        PartyService service = new PartyService();

        HeroState hero = createHero("Hero", 100, 50, 1);
        hero.setCurHp(0);

        service.addHero(hero);

        assertTrue(service.isDefeated());
    }

    @Test
    void isDefeated_oneAlive_returnsFalse() {
        PartyService service = new PartyService();

        HeroState hero = createHero("Hero", 100, 50, 1);

        service.addHero(hero);

        assertFalse(service.isDefeated());
    }

    @Test
    void levelUp_distributesExpEvenly() {
        PartyService service = new PartyService();

        HeroState h1 = createHero("H1", 100, 50, 1);
        HeroState h2 = createHero("H2", 100, 50, 1);

        service.addHero(h1);
        service.addHero(h2);

        service.levelUp(100);

        // Each gets 50 EXP
        assertEquals(50, h1.getCurExp());
        assertEquals(50, h2.getCurExp());
    }

    @Test
    void levelUp_emptyParty_throwsException() {
        PartyService service = new PartyService();

        assertThrows(IllegalStateException.class, () -> service.levelUp(100));
    }

    @Test
    void maxRestore_restoresHpAndMana() {
        PartyService service = new PartyService();

        HeroState hero = createHero("Hero", 100, 50, 1);
        hero.setCurHp(10);
        hero.setCurMana(5);

        service.addHero(hero);

        service.maxRestore();

        assertEquals(100, hero.getCurHp());
        assertEquals(50, hero.getCurMana());
    }

    @Test
    void getTotalLevels_sumsCorrectly() {
        PartyService service = new PartyService();

        service.addHero(createHero("H1", 100, 50, 2));
        service.addHero(createHero("H2", 100, 50, 3));

        assertEquals(5, service.getTotalLevels());
    }

    @Test
    void maxRestoreWithStatus_returnsCorrectValues() {
        PartyService service = new PartyService();

        HeroState hero = createHero("Hero", 100, 50, 1);
        hero.setCurHp(40);
        hero.setCurMana(10);

        service.addHero(hero);

        List<PartyService.RestoreStatus> result = service.maxRestoreWithStatus();

        assertEquals(1, result.size());

        PartyService.RestoreStatus status = result.get(0);

        assertEquals("Hero", status.heroName);
        assertEquals(60, status.hpRestored);
        assertEquals(40, status.manaRestored);
    }

    @Test
    void getParty_returnsCopy_notReference() {
        PartyService service = new PartyService();

        HeroState hero = createHero("Hero", 100, 50, 1);
        service.addHero(hero);

        List<HeroState> party = service.getParty();
        party.clear(); // try to modify external list

        // internal list should remain unchanged
        assertEquals(1, service.getParty().size());
    }
}