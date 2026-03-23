package com.github.campaign_progression.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PartyServiceTest {

    private PartyService party;

    // Simple mock HeroState for testing
    static class TestHero extends HeroState {
        private int hp = 10;
        private int maxHp = 10;
        private int mana = 5;
        private int maxMana = 5;

        @Override
        public int getHp() {
            return hp;
        }

        @Override
        public int getMaxHp() {
            return maxHp;
        }

        @Override
        public int getMaxMana() {
            return maxMana;
        }

        @Override
        public void restoreHp(int amount) {
            hp = amount;
        }

        @Override
        public void restoreMana(int amount) {
            mana = amount;
        }

        @Override
        public int addExp(int exp) {
            return 1; // simulate level up
        }
    }

    @BeforeEach
    void setUp() throws Exception {
        party = new PartyService();

        // Inject partyMembers via reflection (since no constructor/setter exists)
        var field = PartyService.class.getDeclaredField("partyMembers");
        field.setAccessible(true);

        ArrayList<HeroState> members = new ArrayList<>();
        members.add(new TestHero());
        members.add(new TestHero());

        field.set(party, members);
    }

    @Test
    void getPartyMembersShouldReturnCopy() {
        var list = party.getPartyMembers();
        int originalSize = list.size();

        list.clear();

        assertEquals(originalSize, party.getPartyMembers().size());
    }

    @Test
    void isDefeatedShouldReturnFalseIfAnyAlive() {
        assertFalse(party.isDeafeated());
    }

    @Test
    void isDefeatedShouldReturnTrueIfAllDead() throws Exception {
        var field = PartyService.class.getDeclaredField("partyMembers");
        field.setAccessible(true);

        ArrayList<HeroState> members = new ArrayList<>();
        members.add(new TestHero() {
            @Override
            public int getHp() {
                return 0;
            }
        });

        field.set(party, members);

        assertTrue(party.isDeafeated());
    }

    @Test
    void levelUpShouldIncreaseTotalLevels() {
        int before = party.getTotalLevels();

        party.levelUp(100);

        assertTrue(party.getTotalLevels() > before);
    }

    @Test
    void maxRestoreShouldRestoreAllHeroes() {
        party.maxRestore();

        for (HeroState hero : party.getPartyMembers()) {
            assertEquals(hero.getMaxHp(), hero.getHp());
        }
    }

    @Test
    void getPartyShouldReturnCopy() {
        var list = party.getParty();
        int originalSize = list.size();

        list.clear();

        assertEquals(originalSize, party.getParty().size());
    }

    @Test
    void addHeroShouldThrowOnInvalidName() {
        assertThrows(IllegalArgumentException.class, () -> {
            party.addHero(null);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            party.addHero("");
        });
    }
}