package com.github.domain;
public class Steak implements Item{
    private String name;
    private int price;
    private int healAmount;

    public Steak(){
        name = "Steak";
        description = "A juicy steak. Heals 200 HP.";
        price = 1000;
        healAmount = 200;
    }
    
}
