package com.github.campaign_progression.application.dto;

/**
 * DTO representing the result of using an item on a hero.
 */
public class ItemConsumeResponseDTO {

    private final String heroName;
    private final String itemName;
    private final int hpRestored;
    private final int manaRestored;
    private final boolean revived;

    public ItemConsumeResponseDTO(String heroName, String itemName,
                                  int hpRestored, int manaRestored, boolean revived) {
        this.heroName = heroName;
        this.itemName = itemName;
        this.hpRestored = hpRestored;
        this.manaRestored = manaRestored;
        this.revived = revived;
    }

    // Make getters match the test
    public String heroName() { return heroName; }
    public String itemName() { return itemName; }
    public int hpRestored() { return hpRestored; }
    public int manaRestored() { return manaRestored; }
    public boolean revived() { return revived; }

    // Optional: factory methods for convenience
    public static ItemConsumeResponseDTO success(String heroName, String itemName,
                                                 int hpRestored, int manaRestored, boolean revived) {
        return new ItemConsumeResponseDTO(heroName, itemName, hpRestored, manaRestored, revived);
    }
}