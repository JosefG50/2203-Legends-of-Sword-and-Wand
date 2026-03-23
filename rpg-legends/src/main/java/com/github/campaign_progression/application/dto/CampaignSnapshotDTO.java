package com.github.application;

import java.util.List;

public class CampaignSnapshotDTO {

    private int roomCounter;
    private double battleChance;
    private String curRoom;

    private int gold;
    private int exp;

    private List<HeroInstanceDTO> party;
    private List<HeroInstanceDTO> innRecruits;
    private List<ItemDTO> items;

    // Getters and setters
    public int getRoomCounter() { return roomCounter; }
    public void setRoomCounter(int roomCounter) { this.roomCounter = roomCounter; }

    public double getBattleChance() { return battleChance; }
    public void setBattleChance(double battleChance) { this.battleChance = battleChance; }

    public String getCurRoom() { return curRoom; }
    public void setCurRoom(String curRoom) { this.curRoom = curRoom; }

    public int getGold() { return gold; }
    public void setGold(int gold) { this.gold = gold; }

    public int getExp() { return exp; }
    public void setExp(int exp) { this.exp = exp; }

    public List<HeroInstanceDTO> getParty() { return party; }
    public void setParty(List<HeroInstanceDTO> party) { this.party = party; }

    public List<HeroInstanceDTO> getInnRecruits() { return innRecruits; }
    public void setInnRecruits(List<HeroInstanceDTO> innRecruits) { this.innRecruits = innRecruits; }

    public List<ItemDTO> getItems() { return items; }
    public void setItems(List<ItemDTO> items) { this.items = items; }
}