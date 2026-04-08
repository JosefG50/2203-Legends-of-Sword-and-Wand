package com.github.campaign_progression.domain;

public class HeroProgressionService {

    public int gainExp(HeroState hero, int exp) {
        int levelsGained = 0;

        hero.setCurExp(hero.getCurExp() + exp);

        while (hero.getCurExp() >= hero.getLvlUpExp()) {
            hero.setCurExp(hero.getCurExp() - hero.getLvlUpExp());
            levelUp(hero);
            levelsGained++;
        }

        return levelsGained;
    }

    private void levelUp(HeroState hero) {
        hero.setLevel(hero.getLevel() + 1);

        switch (hero.getSpecialization()) {
            case "MAGE": hero.setMageLvl(hero.getMageLvl() + 1); break;
            case "WARRIOR": hero.setWarriorLvl(hero.getWarriorLvl() + 1); break;
            case "ORDER": hero.setOrderLvl(hero.getOrderLvl() + 1); break;
            case "CHAOS": hero.setChaosLvl(hero.getChaosLvl() + 1); break;
        }

        hero.setLvlUpExp((int)(hero.getLvlUpExp() * 1.2));
    }
}
