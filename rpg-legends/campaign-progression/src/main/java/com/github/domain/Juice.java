package com.github.domain;

public class Juice implements ItemTest{
    private String name;
    private int price;
    private int manaHeal;

    public Juice(){
        name = "Juice";
        description = "A bottle of juice. Heals 30 MP.";
        price = 450;
        manaHeal = 30;
    }
    
}
