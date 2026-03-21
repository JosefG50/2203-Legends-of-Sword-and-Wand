package com.github.domain;

import java.util.List;

public class CampaignSnapshotImpl implements CampaignSnapshot {

    private int roomCounter;
    private double battleChance;
    private String curRoom;

    private int gold;
    private int exp;

    private List<HeroInstance> party;
    private List<HeroInstance> innRecruits;
    private List<Item> items;

    public CampaignSnapshotImpl() {
        // empty constructor for serialization / frameworks
    }

    public CampaignSnapshotImpl(int roomCounter, double battleChance, String curRoom,
                                int gold, int exp,
                                List<HeroInstance> party,
                                List<HeroInstance> innRecruits,
                                List<Item> items) {
        this.roomCounter = roomCounter;
        this.battleChance = battleChance;
        this.curRoom = curRoom;
        this.gold = gold;
        this.exp = exp;
        this.party = party;
        this.innRecruits = innRecruits;
        this.items = items;
    }

    @Override
    public int getRoomCounter() { return roomCounter; }
    @Override
    public void setRoomCounter(int roomCounter) { this.roomCounter = roomCounter; }

    @Override
    public double getBattleChance() { return battleChance; }
    @Override
    public void setBattleChance(double battleChance) { this.battleChance = battleChance; }

    @Override
    public String getCurRoom() { return curRoom; }
    @Override
    public void setCurRoom(String curRoom) { this.curRoom = curRoom; }

    @Override
    public int getGold() { return gold; }
    @Override
    public void setGold(int gold) { this.gold = gold; }

    @Override
    public int getExp() { return exp; }
    @Override
    public void setExp(int exp) { this.exp = exp; }

    @Override
    public List<HeroInstance> getParty() { return party; }
    @Override
    public void setParty(List<HeroInstance> party) { this.party = party; }

    @Override
    public List<HeroInstance> getInnRecruits() { return innRecruits; }
    @Override
    public void setInnRecruits(List<HeroInstance> innRecruits) { this.innRecruits = innRecruits; }

    @Override
    public List<Item> getItems() { return items; }
    @Override
    public void setItems(List<Item> items) { this.items = items; }
}