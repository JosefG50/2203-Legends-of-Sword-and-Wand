package com.github.domain;

public class PartyService {
    private ArrayList<HeroState> partyMembers;
    private int totalLevels;

    public ArrayList<HeroState> getPartyMembers() {
        return new ArrayList<>(partyMembers);
    }

    private boolean hasSpace() {
        return partyMembers.size() < 6;
    }

    public void addHero(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        //TODO: add logic to determine hero class and stats based INN recruits
        // TODO: add to totalLevels
    }

    public boolean isDeafeated() {
        for (HeroState hero : partyMembers) {
            if (hero.getHp() > 0) {
                return false;
            }
        }
        return true;
     }
    public void levelUp(int exp) {
        exp = exp / partyMembers.size();
        for (HeroState hero : partyMembers) {
            totalLevels += hero.addExp(exp);
        }
    }

    public void maxRestore(){
        for (HeroState hero : partyMembers) {
            hero.restoreHp(hero.getMaxHp());
            hero.restoreMana(hero.getMaxMana());
        }
    }
    public List<HeroState> getParty() {
        return new ArrayList<>(partyMembers);
    }
    public int getTotalLevels() {
        return totalLevels;
    }     

}
