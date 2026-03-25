package com.github.campaign_progression.application.dto;

public class ItemConsumeRequestDTO {
    private String itemType;
    private int heroIndex;

    public ItemConsumeRequestDTO() {}

    public ItemConsumeRequestDTO(String itemType, int heroIndex) {
        this.itemType = itemType;
        this.heroIndex = heroIndex;
    }

    public String getItemType() { return itemType; }
    public void setItemType(String itemType) { this.itemType = itemType; }
    public int getHeroIndex() { return heroIndex; }
    public void setHeroIndex(int heroIndex) { this.heroIndex = heroIndex; }
}