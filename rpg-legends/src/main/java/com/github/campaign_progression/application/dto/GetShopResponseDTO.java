package com.github.campaign_progression.application.dto;

import java.util.List;

public class GetShopResponseDTO {

    private List<ItemDTO> items;

    public GetShopResponseDTO(List<ItemDTO> items) {
        this.items = items;
    }

    public List<ItemDTO> getItems() {
        return items;
    }

    public static class ItemDTO {
        private String name;
        private int cost;
        private int hpHeal;
        private int manaHeal;
        private boolean canRevive;

        public ItemDTO(String name, int cost, int hpHeal, int manaHeal, boolean canRevive) {
            this.name = name;
            this.cost = cost;
            this.hpHeal = hpHeal;
            this.manaHeal = manaHeal;
            this.canRevive = canRevive;
        }

        public String getName() { return name; }
        public int getCost() { return cost; }
        public int getHpHeal() { return hpHeal; }
        public int getManaHeal() { return manaHeal; }
        public boolean isCanRevive() { return canRevive; }
    }
}