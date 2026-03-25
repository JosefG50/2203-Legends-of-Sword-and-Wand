package com.github.campaign_progression.application.dto;

public class BuyItemRequestDTO {
    private String itemType;
    private int amount;

    public BuyItemRequestDTO() {}

    public BuyItemRequestDTO(String itemType, int amount) {
        this.itemType = itemType;
        this.amount = amount;
    }

    public String getItemType() { return itemType; }
    public void setItemType(String itemType) { this.itemType = itemType; }
    public int getAmount() { return amount; }
    public void setAmount(int amount) { this.amount = amount; }
}