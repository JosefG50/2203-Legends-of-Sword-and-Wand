package com.github.campaign_progression.application.dto;

public class BuyRecruitRequestDTO {
    private int roomCounter;

    public BuyRecruitRequestDTO() {}

    public BuyRecruitRequestDTO(int roomCounter) {
        this.roomCounter = roomCounter;
    }

    public int getRoomCounter() { return roomCounter; }
    public void setRoomCounter(int roomCounter) { this.roomCounter = roomCounter; }
}