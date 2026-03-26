package com.github.campaign_progression.application.dto;

public class BuyItemResponseDTO {

    private final boolean success;
    private final String itemName;
    private final int amountBought;
    private final String message;

    public BuyItemResponseDTO(boolean success, String itemName, int amountBought, String message) {
        this.success = success;
        this.itemName = itemName;
        this.amountBought = amountBought;
        this.message = message;
    }

    public boolean isSuccess() { return success; }
    public String getItemName() { return itemName; }
    public int getAmountBought() { return amountBought; }
    public String getMessage() { return message; }
}