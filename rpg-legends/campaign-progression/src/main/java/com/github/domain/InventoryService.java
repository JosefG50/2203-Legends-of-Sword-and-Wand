package com.github.domain;

public class InventoryService {

    /*
    Fix for delieverable 3:
    Item ID By Index
    0 Bread
    1 Cheese
    2 Steak
    3 Water
    4 Juice
    5 Wine
    6 Exixir 
    */
    private int gold;
    private Array<Integer> items;
    private int goldSpent

    public int getGoldSpent() {
        return goldSpent;
    }

    private Item itemDecoder(int id) {
        switch (id) {
            case 0:
                return new Bread();
            case 1:
                return new Cheese();
            case 2:
                return new Steak();
            case 3:
                return new Water();
            case 4:
                return new Juice();
            case 5:
                return new Wine();
            case 6:
                return new Exilir();
            default:
                throw new IllegalArgumentException("Invalid item id");
        }
    }


    public int getGold() {
        return gold;
    }

    public int minusGold(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (canAfford(amount)) {
            gold -= amount;
            return amount;
        }
        throw new IllegalArgumentException("Not enough gold");
    }

    public void addItem(int id, int amount = 1) {
        if (id < 0 || id >= items.size()) {
            throw new IllegalArgumentException("Invalid item id");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        buyCost = itemDecoder(id).getCost() * amount;
        if (canAfford(buyCost)){
            items[id] += amount;
            gold -= buyCost;
        }else{
            throw new IllegalArgumentException("Not enough gold");
        }
    }

    public boolean canAfford(int amount) {
        return gold >= amount;
    }

    
    public void useItem(int id, int amount) {
        if ( amount > items[id]){
            throw new IllegalArgumentException("Not enough items");
        }
        if (id < 0 || id >= items.size()) {
            throw new IllegalArgumentException("Invalid item id");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        items[id] -= amount;
    }
    
}
