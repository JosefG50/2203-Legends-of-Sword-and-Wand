package com.github.campaign_progression.application.dto;

/**
 * DTO representing the result of adding gold to the player's inventory.
 */
public class GoldDTO {

    private final int goldAdded;
    private final int newTotalGold;

    public GoldDTO(int goldAdded, int newTotalGold) {
        this.goldAdded = goldAdded;
        this.newTotalGold = newTotalGold;
    }

    public int getGoldAdded() {
        return goldAdded;
    }

    public int getNewTotalGold() {
        return newTotalGold;
    }
}