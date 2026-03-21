package com.github.domain;

import java.util.ArrayList;
import java.util.List;

public class PartyService {

    private final List<HeroState> partyMembers = new ArrayList<>();
    private static final int MAX_PARTY_SIZE = 6;

    public List<HeroState> getParty() {
        return new ArrayList<>(partyMembers);
    }

    public boolean hasSpace() {
        return partyMembers.size() < MAX_PARTY_SIZE;
    }

    public void addHero(HeroState hero) {
        if (hero == null) {
            throw new IllegalArgumentException("Hero cannot be null");
        }
        if (!hasSpace()) {
            throw new IllegalStateException("Party is full");
        }

        partyMembers.add(hero);
    }

    public boolean isDefeated() {
        for (HeroState hero : partyMembers) {
            if (hero.getHp() > 0) {
                return false;
            }
        }
        return true;
    }

    public void levelUp(int exp) {
        if (partyMembers.isEmpty()) {
            throw new IllegalStateException("No party members");
        }

        int sharedExp = exp / partyMembers.size();

        for (HeroState hero : partyMembers) {
            hero.addExp(sharedExp);
        }
    }

    public void maxRestore() {
        for (HeroState hero : partyMembers) {
            hero.restoreHp(hero.getMaxHp());
            hero.restoreMana(hero.getMaxMana());
        }
    }

    public int getTotalLevels() {
        int total = 0;
        for (HeroState hero : partyMembers) {
            total += hero.getTotalLevel(); // YOU MUST HAVE THIS METHOD
        }
        return total;
    }
    public List<RestoreStatus> maxRestoreWithStatus() {
        List<RestoreStatus> statusList = new ArrayList<>();
        for (HeroState hero : partyMembers) {
            int hpBefore = hero.getHp();
            int manaBefore = hero.getMana();

            hero.restoreHp(hero.getMaxHp());
            hero.restoreMana(hero.getMaxMana());

            statusList.add(new RestoreStatus(
                    hero.getName(),
                    hero.getMaxHp() - hpBefore,
                    hero.getMaxMana() - manaBefore
            ));
        }
        return statusList;
    }

    public static class RestoreStatus {
        public final String heroName;
        public final int hpRestored;
        public final int manaRestored;

        public RestoreStatus(String heroName, int hpRestored, int manaRestored) {
            this.heroName = heroName;
            this.hpRestored = hpRestored;
            this.manaRestored = manaRestored;
        }
    }
}