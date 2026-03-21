package com.github.domain;

public class Bread implements ItemTest{
    private String name;
    private int price;
    private int healAmount;

    public Bread(){
        name = "Bread";
        description = "A loaf of bread. Heals 20 HP.";
        price = 200;
        healAmount = 20;
    }
}
