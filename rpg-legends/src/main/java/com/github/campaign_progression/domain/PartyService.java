package com.github.campaign_progression.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class responsible for managing a party of heroes.
 * <p>
 * Provides functionality for adding heroes, checking party state,
 * distributing experience, and restoring health/mana.
 * </p>
 */
public class PartyService {

    /**
     * Internal list of party members.
     */
    private final List<HeroState> partyMembers = new ArrayList<>();

    /**
     * Maximum number of heroes allowed in a party.
     */
    private static final int MAX_PARTY_SIZE = 6;

    /**
     * Returns a copy of the current party.
     *
     * @return a new list containing all party members
     */
    public List<HeroState> getParty() {
        return new ArrayList<>(partyMembers);
    }

    /**
     * Checks if the party has space for additional heroes.
     *
     * @return true if the party is not full, false otherwise
     */
    public boolean hasSpace() {
        return partyMembers.size() < MAX_PARTY_SIZE;
    }

    /**
     * Adds a hero to the party.
     *
     * @param hero the hero to add
     * @throws IllegalArgumentException if hero is null
     * @throws IllegalStateException if the party is already full
     */
    public void addHero(HeroState hero) {
        if (hero == null) {
            throw new IllegalArgumentException("Hero cannot be null");
        }
        if (!hasSpace()) {
            throw new IllegalStateException("Party is full");
        }

        partyMembers.add(hero);
    }

    /**
     * Checks whether all heroes in the party are defeated.
     *
     * @return true if all heroes have 0 or less HP, false otherwise
     */
    public boolean isDefeated() {
        for (HeroState hero : partyMembers) {
            if (hero.getCurHp() > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Distributes experience evenly among all party members.
     *
     * @param exp total experience to distribute
     * @throws IllegalStateException if the party is empty
     */
    public void levelUp(int exp) {
        if (partyMembers.isEmpty()) {
            throw new IllegalStateException("No party members");
        }

        int sharedExp = exp / partyMembers.size();

        for (HeroState hero : partyMembers) {
            hero.gainExp(sharedExp);
        }
    }

    /**
     * Fully restores HP and mana for all party members.
     */
    public void maxRestore() {
        for (HeroState hero : partyMembers) {
            hero.setCurHp(hero.getMaxHp());
            hero.setCurMana(hero.getMaxMana());
        }
    }

    /**
     * Calculates the total level of all heroes in the party.
     *
     * @return the sum of all hero levels
     */
    public int getTotalLevels() {
        int total = 0;
        for (HeroState hero : partyMembers) {
            total += hero.getLevel();
        }
        return total;
    }

    /**
     * Restores all heroes and returns detailed restoration results.
     *
     * @return list of restore status objects for each hero
     */
    public List<RestoreStatus> maxRestoreWithStatus() {
        List<RestoreStatus> statusList = new ArrayList<>();
        for (HeroState hero : partyMembers) {
            int hpBefore = hero.getCurHp();
            int manaBefore = hero.getCurMana();

            hero.setCurHp(hero.getMaxHp());
            hero.setCurMana(hero.getMaxMana());

            statusList.add(new RestoreStatus(
                    hero.getName(),
                    hero.getMaxHp() - hpBefore,
                    hero.getMaxMana() - manaBefore
            ));
        }
        return statusList;
    }

    /**
     * Represents the result of restoring a hero's HP and mana.
     */
    public static class RestoreStatus {

        /** Name of the hero */
        public final String heroName;

        /** Amount of HP restored */
        public final int hpRestored;

        /** Amount of mana restored */
        public final int manaRestored;

        /**
         * Constructs a RestoreStatus object.
         *
         * @param heroName name of the hero
         * @param hpRestored amount of HP restored
         * @param manaRestored amount of mana restored
         */
        public RestoreStatus(String heroName, int hpRestored, int manaRestored) {
            this.heroName = heroName;
            this.hpRestored = hpRestored;
            this.manaRestored = manaRestored;
        }
    }
}