package com.github.campaign_progression.domain;

import java.util.*;

public class Inn implements Room {

    private static final int MAX_PARTY_SIZE = 5;
    private static final String[] CLASSES = {"MAGE", "WARRIOR", "ORDER", "CHAOS"};

    private final List<ItemType> shop; // Use ItemType instead of ItemTest
    private final Map<String, HeroState> recruits;

    public Inn() {
        // Initialize shop with available ItemType values
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
     * Generate a recruitable hero based on party size
     */
    public Optional<HeroState> generateRecruit(PartyService partyService, int roomCounter) {
    // No recruits after room 10
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
        case "MAGE" -> hero.setMageLvl(level);
        case "WARRIOR" -> hero.setWarriorLvl(level);
        case "ORDER" -> hero.setOrderLvl(level);
        case "CHAOS" -> hero.setChaosLvl(level);
    }

    // Add with unique key
    recruits.put(hero.getSpecialization() + "-" + UUID.randomUUID(), hero);
    return Optional.of(hero);
}
    public Map<String, HeroState> viewRecruits() {
        return new HashMap<>(recruits);
    }

    public boolean hasRecruit(String name) {
        return recruits.containsKey(name);
    }

    public List<ItemType> getShop() {
        return new ArrayList<>(shop);
    }

    public void clearRecruits() {
        recruits.clear();
    }

    public void addRecruit(HeroState hero) {
        recruits.put(hero.getSpecialization() + "-" + UUID.randomUUID(), hero);
    }
}