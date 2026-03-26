package com.github.campaign_progression.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link Inn}.
 *
 * <p>Because {@link Inn#generateRecruit} uses internal randomness,
 * probabilistic assertions are run with {@link RepeatedTest} to build
 * confidence across varied random outcomes.</p>
 */
class InnTest {

    private static final int REPEAT = 30;
    private static final Set<String> VALID_SPECS = Set.of("MAGE", "WARRIOR", "ORDER", "CHAOS");

    private Inn inn;
    private PartyService mockPartyService;

    @BeforeEach
    void setUp() {
        inn = new Inn();
        mockPartyService = mock(PartyService.class);
        // Default: party has room for more heroes
        when(mockPartyService.getParty()).thenReturn(List.of());
    }

    // -------------------------------------------------------------------------
    // Constructor / initial state
    // -------------------------------------------------------------------------

    @Test
    void constructor_shopIsNotNull() {
        assertNotNull(inn.getShop());
    }

    @Test
    void constructor_shopContainsSevenItems() {
        assertEquals(7, inn.getShop().size());
    }

    @Test
    void constructor_shopContainsExpectedItems() {
        List<ItemType> shop = inn.getShop();
        assertTrue(shop.contains(ItemType.BREAD));
        assertTrue(shop.contains(ItemType.CHEESE));
        assertTrue(shop.contains(ItemType.STEAK));
        assertTrue(shop.contains(ItemType.WATER));
        assertTrue(shop.contains(ItemType.JUICE));
        assertTrue(shop.contains(ItemType.WINE));
        assertTrue(shop.contains(ItemType.EXILIR));
    }

    @Test
    void constructor_recruitPoolIsEmpty() {
        assertTrue(inn.viewRecruits().isEmpty());
    }

    // -------------------------------------------------------------------------
    // getShop() — defensive copy
    // -------------------------------------------------------------------------

    @Test
    void getShop_returnsDefensiveCopy_mutationDoesNotAffectInternalShop() {
        List<ItemType> copy = inn.getShop();
        copy.clear();
        assertEquals(7, inn.getShop().size());
    }

    // -------------------------------------------------------------------------
    // generateRecruit() — blocked cases
    // -------------------------------------------------------------------------

    @Test
    void generateRecruit_roomCounterAbove10_returnsEmpty() {
        Optional<HeroState> result = inn.generateRecruit(mockPartyService, 11);
        assertTrue(result.isEmpty());
    }

    @Test
    void generateRecruit_roomCounterExactly10_doesNotBlock() {
        Optional<HeroState> result = inn.generateRecruit(mockPartyService, 10);
        assertTrue(result.isPresent());
    }

    @Test
    void generateRecruit_partyAtMaxSize_returnsEmpty() {
        List<HeroState> fullParty = List.of(
                mock(HeroState.class), mock(HeroState.class),
                mock(HeroState.class), mock(HeroState.class),
                mock(HeroState.class)
        );
        when(mockPartyService.getParty()).thenReturn(fullParty);

        Optional<HeroState> result = inn.generateRecruit(mockPartyService, 5);
        assertTrue(result.isEmpty());
    }

    @Test
    void generateRecruit_partyOneBelowMax_doesNotBlock() {
        List<HeroState> almostFull = List.of(
                mock(HeroState.class), mock(HeroState.class),
                mock(HeroState.class), mock(HeroState.class)
        );
        when(mockPartyService.getParty()).thenReturn(almostFull);

        Optional<HeroState> result = inn.generateRecruit(mockPartyService, 5);
        assertTrue(result.isPresent());
    }

    @Test
    void generateRecruit_roomCounterAbove10AndFullParty_returnsEmpty() {
        when(mockPartyService.getParty()).thenReturn(
                List.of(mock(HeroState.class), mock(HeroState.class),
                        mock(HeroState.class), mock(HeroState.class),
                        mock(HeroState.class))
        );
        assertTrue(inn.generateRecruit(mockPartyService, 15).isEmpty());
    }

    // -------------------------------------------------------------------------
    // generateRecruit() — successful generation
    // -------------------------------------------------------------------------

    @Test
    void generateRecruit_validConditions_returnsNonEmpty() {
        assertTrue(inn.generateRecruit(mockPartyService, 1).isPresent());
    }

    @RepeatedTest(REPEAT)
    void generateRecruit_heroHasValidSpecialization() {
        HeroState hero = inn.generateRecruit(mockPartyService, 1).orElseThrow();
        assertTrue(VALID_SPECS.contains(hero.getSpecialization()),
                "Unexpected specialization: " + hero.getSpecialization());
    }

    @RepeatedTest(REPEAT)
    void generateRecruit_heroSubLevelMatchesSpecialization() {
        HeroState hero = inn.generateRecruit(mockPartyService, 1).orElseThrow();
        int subLevel = switch (hero.getSpecialization()) {
            case "MAGE"    -> hero.getMageLvl();
            case "WARRIOR" -> hero.getWarriorLvl();
            case "ORDER"   -> hero.getOrderLvl();
            case "CHAOS"   -> hero.getChaosLvl();
            default        -> -1;
        };
        assertTrue(subLevel >= 1 && subLevel <= 4,
                "Sub-level out of range: " + subLevel);
    }

    @RepeatedTest(REPEAT)
    void generateRecruit_heroIsAddedToRecruitPool() {
        int before = inn.viewRecruits().size();
        inn.generateRecruit(mockPartyService, 1);
        assertEquals(before + 1, inn.viewRecruits().size());
    }

    @Test
    void generateRecruit_multipleCallsProduceUniqueKeys() {
        inn.generateRecruit(mockPartyService, 1);
        inn.generateRecruit(mockPartyService, 1);
        inn.generateRecruit(mockPartyService, 1);
        assertEquals(3, inn.viewRecruits().size());
    }

    // -------------------------------------------------------------------------
    // viewRecruits() — defensive copy
    // -------------------------------------------------------------------------

    @Test
    void viewRecruits_initiallyEmpty() {
        assertTrue(inn.viewRecruits().isEmpty());
    }

    @Test
    void viewRecruits_returnsDefensiveCopy_mutationDoesNotAffectPool() {
        inn.generateRecruit(mockPartyService, 1);
        Map<String, HeroState> copy = inn.viewRecruits();
        copy.clear();
        assertEquals(1, inn.viewRecruits().size());
    }

    // -------------------------------------------------------------------------
    // hasRecruit()
    // -------------------------------------------------------------------------

    @Test
    void hasRecruit_existingKey_returnsTrue() {
        inn.generateRecruit(mockPartyService, 1);
        String key = inn.viewRecruits().keySet().iterator().next();
        assertTrue(inn.hasRecruit(key));
    }

    @Test
    void hasRecruit_nonExistingKey_returnsFalse() {
        assertFalse(inn.hasRecruit("MAGE-00000000-0000-0000-0000-000000000000"));
    }

    @Test
    void hasRecruit_emptyPool_returnsFalse() {
        assertFalse(inn.hasRecruit("anything"));
    }

    // -------------------------------------------------------------------------
    // addRecruit()
    // -------------------------------------------------------------------------

    @Test
    void addRecruit_addsHeroToPool() {
        HeroState hero = new HeroState();
        hero.setSpecialization("MAGE");
        inn.addRecruit(hero);
        assertEquals(1, inn.viewRecruits().size());
    }

    @Test
    void addRecruit_keyContainsSpecialization() {
        HeroState hero = new HeroState();
        hero.setSpecialization("CHAOS");
        inn.addRecruit(hero);
        String key = inn.viewRecruits().keySet().iterator().next();
        assertTrue(key.startsWith("CHAOS-"),
                "Key should start with specialization: " + key);
    }

    @Test
    void addRecruit_twoSameSpecialization_producesUniqueKeys() {
        HeroState h1 = new HeroState();
        h1.setSpecialization("WARRIOR");
        HeroState h2 = new HeroState();
        h2.setSpecialization("WARRIOR");

        inn.addRecruit(h1);
        inn.addRecruit(h2);

        assertEquals(2, inn.viewRecruits().size(),
                "Two WARRIOR heroes should produce distinct keys");
    }

    @Test
    void addRecruit_bypassesRoomAndPartyChecks() {
        // Fill party to max
        when(mockPartyService.getParty()).thenReturn(
                List.of(mock(HeroState.class), mock(HeroState.class),
                        mock(HeroState.class), mock(HeroState.class),
                        mock(HeroState.class))
        );

        HeroState hero = new HeroState();
        hero.setSpecialization("ORDER");

        // addRecruit should still work regardless
        assertDoesNotThrow(() -> inn.addRecruit(hero));
        assertEquals(1, inn.viewRecruits().size());
    }
}