package com.github.domain;
public class Water implements Item{
    private String name;
    private int price;
    private int manaHeal;

    public Water(){
        name = "Water";
        description = "A jug of water. Heals 10 MP.";
        price = 150;
        manaHeal = 10;
    }
}

