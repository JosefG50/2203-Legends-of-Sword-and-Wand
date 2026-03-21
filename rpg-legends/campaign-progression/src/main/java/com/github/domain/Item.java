package com.github.domain;

public abstract class Item {
    protected String name;
    protected String description;
    protected int cost;
    protected int hpHeal;
    protected int manaHeal;
    protected boolean canRevive = false;

    public int getId() {
        return id;
    }

    public int getCost(){
        return cost;
    }
    public int getHpHeal(){
        return hpHeal;
    }
    public int getManaHeal(){
        return manaHeal;
    }
    public boolean getCanRevive(){
        return canRevive;
    }

}