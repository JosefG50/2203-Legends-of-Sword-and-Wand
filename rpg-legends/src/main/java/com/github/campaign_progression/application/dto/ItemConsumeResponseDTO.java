package com.github.campaign_progression.application.dto;

public class ItemConsumeResponseDTO {

    private final String heroName;
    private final String itemName;
    private final int hpRestored;
    private final int manaRestored;
    private final boolean revived;

    public ItemConsumeResponseDTO(String heroName, String itemName, int hpRestored, int manaRestored, boolean revived) {
        this.heroName = heroName;
        this.itemName = itemName;
        this.hpRestored = hpRestored;
        this.manaRestored = manaRestored;
        this.revived = revived;
    }

    public String getHeroName() { return heroName; }
    public String getItemName() { return itemName; }
    public int getHpRestored() { return hpRestored; }
    public int getManaRestored() { return manaRestored; }
    public boolean isRevived() { return revived; }
}