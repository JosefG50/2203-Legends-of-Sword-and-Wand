package com.github.application.dto;

public class BuyItemResponseDTO {

    private boolean success;
    private String message;
    private String itemName;
    private int quantityBought;
    private int remainingGold;

    public BuyItemResponseDTO(boolean success, String message,
                              String itemName, int quantityBought, int remainingGold) {
        this.success = success;
        this.message = message;
        this.itemName = itemName;
        this.quantityBought = quantityBought;
        this.remainingGold = remainingGold;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public String getItemName() { return itemName; }
    public int getQuantityBought() { return quantityBought; }
    public int getRemainingGold() { return remainingGold; }
}