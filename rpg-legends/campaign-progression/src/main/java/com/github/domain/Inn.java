package com.github.domain;

public class Inn implements Room{
    private static final List<Item> SHOPLIST = List.of(
            new Item("Health Potion", 10),
            new Item("Mana Potion", 15),
            new Item("Sword", 50),
            new Item("Shield", 40)
    );
    private Map<String, HeroState> recruits = new HashMap<>();

    public void recover() {
        // Logic to recover heroes' health and mana
    }
    public Map<String, HeroState> viewRecruits() {
        return new HashMap<>(recruits);
    }
    public boolean hasRecruit(String name) {
        return recruits.containsKey(name);
    }
    public List<Item> getShop() {
        return new ArrayList<>(SHOPLIST);
    }
}