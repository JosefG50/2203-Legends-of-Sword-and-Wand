package com.github.campaign_progression.domain;

public class HeroCombatService {

    public void damage(HeroState hero, int dmg) {
        hero.setCurHp(hero.getCurHp() - dmg);
    }

    public void heal(HeroState hero, int amount) {
        hero.setCurHp(hero.getCurHp() + amount);
    }

    public void useMana(HeroState hero, int amount) {
        hero.setCurMana(hero.getCurMana() - amount);
    }
}