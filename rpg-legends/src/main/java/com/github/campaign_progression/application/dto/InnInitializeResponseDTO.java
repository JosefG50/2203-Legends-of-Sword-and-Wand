package com.github.campaign_progression.application.dto;

import java.util.List;

public class InnInitializeResponseDTO {

    private List<HeroRestoreDTO> heroes;

    public InnInitializeResponseDTO(List<HeroRestoreDTO> heroes) {
        this.heroes = heroes;
    }

    public List<HeroRestoreDTO> getHeroes() {
        return heroes;
    }

    public static class HeroRestoreDTO {
        private String heroName;
        private int hpRestored;
        private int manaRestored;

        public HeroRestoreDTO(String heroName, int hpRestored, int manaRestored) {
            this.heroName = heroName;
            this.hpRestored = hpRestored;
            this.manaRestored = manaRestored;
        }

        public String getHeroName() { return heroName; }
        public int getHpRestored() { return hpRestored; }
        public int getManaRestored() { return manaRestored; }
    }
}