package com.github.campaign_progression.domain;

import java.util.List;

public class CampaignSnapshotImpl implements CampaignSnapshot {

    private int roomCounter;
    private double battleChance;
    private String curRoom;

    private int gold;
    private int exp;

    private List<HeroState> party;
    private int[] items;

    public CampaignSnapshotImpl() {
        // empty constructor for serialization / frameworks
    }

    public CampaignSnapshotImpl(int roomCounter, double battleChance, String curRoom,
                                int gold, int exp,
                                List<HeroState> party,
                                int[] items) {
        this.roomCounter = roomCounter;
        this.battleChance = battleChance;
        this.curRoom = curRoom;
        this.gold = gold;
        this.exp = exp;
        this.party = party;
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
    public List<HeroState> getParty() { return party; }
    @Override
    public void setParty(List<HeroState> party) { this.party = party; }



    @Override
    public int[] getItems() { return items; }
    @Override
    public void setItems(int[] items) { this.items = items; }
}