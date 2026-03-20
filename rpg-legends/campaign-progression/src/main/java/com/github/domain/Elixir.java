package com.github.domain;

public class Elixir implements Item{
    private String name;
    private int price;
    private int hpHeal;
    private int manaHeal;
    private boolean canRevive;

    public Elixir(){
        name = "Elixir";
        description = "A powerful elixir. Heals HP and MP to max, and can revive from death.";
        price = 2000;
        hpHeal = 9999;
        manaHeal = 9999;
        canRevive = true;
    }
    
}