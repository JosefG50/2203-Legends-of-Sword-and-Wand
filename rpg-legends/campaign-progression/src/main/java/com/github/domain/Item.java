package com.github.domain;

public abstract class Item {
    protected String name;
    protected String description;
    protected int cost;
    protected int hpHeal;
    protected int manaHeal;
    protected boolean canRevive = false;
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

# cost: int
    # hpHeal: int
    # manaHeal: int
    # canRevive = false: boolean
    + getCost(): int
    + getHpHeal(): int
    + getManaHeal(): int
    + getCanRevive(): boolean