package com.github.campaign_progression.domain;

import java.util.*;

/**
 * Represents an inn room in the campaign, providing hero recruitment and an item shop.
 *
 * <p>The {@code Inn} implements {@link Room} and serves two purposes:</p>
 * <ol>
 *   <li><b>Recruitment:</b> Generates randomized {@link HeroState} recruits available
 *       to join the party, subject to party size and room counter limits.</li>
 *   <li><b>Shop:</b> Offers a fixed list of {@link ItemType} items available for purchase.</li>
 * </ol>
 *
 * <p>Recruits are stored internally with a unique composite key
 * ({@code "SPECIALIZATION-UUID"}) to prevent key collisions between heroes of
 * the same class.</p>
 *
 * <p><b>Recruitment rules:</b></p>
 * <ul>
 *   <li>No recruits are generated after room 10.</li>
 *   <li>No recruits are generated if the party is at maximum size ({@value #MAX_PARTY_SIZE}).</li>
 *   <li>Generated heroes are level 1–4 with a randomly assigned specialization.</li>
 * </ul>
 */
public class Inn implements Room {

    /** The maximum number of heroes allowed in the player's party. */
    private static final int MAX_PARTY_SIZE = 5;

    /** The available hero specializations that recruits can be assigned. */
    private static final String[] CLASSES = {"MAGE", "WARRIOR", "ORDER", "CHAOS"};

    /**
     * The list of items available in this inn's shop.
     * Initialized with a fixed set of {@link ItemType} values and returned defensively.
     */
    private final List<ItemType> shop;

    /**
     * The map of recruitable heroes, keyed by {@code "SPECIALIZATION-UUID"}.
     * UUID suffix ensures uniqueness across heroes of the same specialization.
     */
    private final Map<String, HeroState> recruits;

    /**
     * Constructs a new {@code Inn} and populates the shop with the default item selection
     * and initializes an empty recruit pool.
     */
    public Inn() {
        this.shop = new ArrayList<>(List.of(
                ItemType.BREAD,
                ItemType.CHEESE,
                ItemType.STEAK,
                ItemType.WATER,
                ItemType.JUICE,
                ItemType.WINE,
                ItemType.EXILIR
        ));
        this.recruits = new HashMap<>();
    }

    /**
     * Attempts to generate a recruitable {@link HeroState} and adds it to the recruit pool.
     *
     * <p>Returns {@link Optional#empty()} if either of the following conditions are met:</p>
     * <ul>
     *   <li>The {@code roomCounter} exceeds 10 (recruits are unavailable late in the campaign).</li>
     *   <li>The party has already reached the maximum size of {@value #MAX_PARTY_SIZE}.</li>
     * </ul>
     *
     * <p>When a recruit is generated, they are assigned:</p>
     * <ul>
     *   <li>A random level between 1 and 4 (inclusive).</li>
     *   <li>A random specialization from {@code MAGE}, {@code WARRIOR}, {@code ORDER}, {@code CHAOS}.</li>
     *   <li>Their specialization sub-level set to the generated level.</li>
     * </ul>
     *
     * <p>The recruit is stored in the internal pool under a unique
     * {@code "SPECIALIZATION-UUID"} key before being returned.</p>
     *
     * @param partyService the service providing current party state; must not be {@code null}
     * @param roomCounter  the current room number in the campaign
     * @return an {@link Optional} containing the generated {@link HeroState},
     *         or {@link Optional#empty()} if generation was blocked
     */
    public Optional<HeroState> generateRecruit(PartyService partyService, int roomCounter) {
        if (roomCounter > 10) {
            return Optional.empty();
        }
        if (partyService.getParty().size() >= MAX_PARTY_SIZE) {
            return Optional.empty();
        }

        Random rand = new Random();
        int level = rand.nextInt(4) + 1; // Level 1–4
        String specialization = CLASSES[rand.nextInt(CLASSES.length)];

        HeroState hero = new HeroState();
        hero.setSpecialization(specialization);

        switch (specialization) {
            case "MAGE"    -> hero.setMageLvl(level);
            case "WARRIOR" -> hero.setWarriorLvl(level);
            case "ORDER"   -> hero.setOrderLvl(level);
            case "CHAOS"   -> hero.setChaosLvl(level);
        }

        // Store with unique key to avoid collisions between same-specialization heroes
        recruits.put(hero.getSpecialization() + "-" + UUID.randomUUID(), hero);
        return Optional.of(hero);
    }

    /**
     * Returns a defensive copy of the current recruit pool.
     *
     * <p>Modifications to the returned map do not affect the internal recruit pool.</p>
     *
     * @return a new {@link HashMap} containing all current recruits, keyed by their
     *         {@code "SPECIALIZATION-UUID"} identifier
     */
    public Map<String, HeroState> viewRecruits() {
        return new HashMap<>(recruits);
    }

    /**
     * Checks whether a recruit with the given key exists in the recruit pool.
     *
     * @param name the {@code "SPECIALIZATION-UUID"} key to look up
     * @return {@code true} if a recruit with that key exists; {@code false} otherwise
     */
    public boolean hasRecruit(String name) {
        return recruits.containsKey(name);
    }

    /**
     * Returns a defensive copy of the shop's item list.
     *
     * <p>Modifications to the returned list do not affect the inn's internal shop inventory.</p>
     *
     * @return a new {@link ArrayList} containing the available {@link ItemType} items
     */
    public List<ItemType> getShop() {
        return new ArrayList<>(shop);
    }

    /**
     * Manually adds a pre-existing {@link HeroState} to the recruit pool under a
     * unique {@code "SPECIALIZATION-UUID"} key.
     *
     * <p>This method bypasses the generation rules enforced by
     * {@link #generateRecruit(PartyService, int)} and is intended for testing
     * or administrative use.</p>
     *
     * @param hero the hero to add to the recruit pool; must not be {@code null}
     */
    public void addRecruit(HeroState hero) {
        recruits.put(hero.getSpecialization() + "-" + UUID.randomUUID(), hero);
    }
}