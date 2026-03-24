package com.github.domain;

/**
 * The RoomData class is a Data Transfer Object (DTO) that holds information about a room.
 * It includes the room ID and the player's progress within that room.
 */
public class RoomData {
    /**
     * The unique identifier for the room.
     */
    public int roomID;

    /**
     * The player's progress in the room (e.g., completion percentage or stage).
     */
    public int progress;
}
