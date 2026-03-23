package com.github.campaign_progression.domain;

import java.util.List;

public interface CampaignSnapshot {

    int getRoomCounter();
    void setRoomCounter(int roomCounter);

    double getBattleChance();
    void setBattleChance(double chance);

    String getCurRoom();
    void setCurRoom(String room);

    int getGold();
    void setGold(int gold);

    int getExp();
    void setExp(int exp);

    List<HeroState> getParty();
    void setParty(List<HeroState> party);

    List<HeroState> getInnRecruits();
    void setInnRecruits(List<HeroState> recruits);

    List<Item> getItems();
    void setItems(List<Item> items);
}