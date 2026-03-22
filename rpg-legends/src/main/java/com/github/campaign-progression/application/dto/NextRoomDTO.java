package com.github.application.dto;

public class NextRoomDTO {
    private final String roomType; // e.g., "Inn", "Battle"
    private final int roomCounter;

    public NextRoomDTO(String roomType, int roomCounter) {
        this.roomType = roomType;
        this.roomCounter = roomCounter;
    }

    public String getRoomType() { return roomType; }
    public int getRoomCounter() { return roomCounter; }
}