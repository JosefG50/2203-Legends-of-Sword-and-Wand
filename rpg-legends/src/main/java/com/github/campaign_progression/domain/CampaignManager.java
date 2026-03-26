package com.github.campaign_progression.domain;

/**
 * Manages the progression of a campaign, tracking the current room,
 * room counter, battle chance, and end-of-room state.
 *
 * <p>A campaign consists of up to {@value #MAX_ROOMS} rooms generated
 * sequentially via a {@link RoomFactory}. The battle chance influences
 * whether each generated room is a {@link BattleRoom} or another room type.</p>
 *
 * <p>Usage example:</p>
 * <pre>{@code
 * CampaignManager manager = new CampaignManager(myRoomFactory);
 * manager.startNewCampaign();
 * Room room = manager.nextRoom();
 * }</pre>
 */
public class CampaignManager {

    /** The room the player is currently in. */
    private Room currentRoom;

    /**
     * The probability (0.0 to 1.0) that the next generated room will be a battle room.
     * Defaults to {@code 0.6} at campaign start.
     */
    private double battleChance;

    /** Tracks how many rooms have been entered in the current campaign. Starts at 1. */
    private int roomCounter;

    /** Whether the player has finished the current room and is ready to advance. */
    private boolean endOfRoom;

    /** The factory used to create new rooms during campaign progression. */
    private final RoomFactory RoomFactory;

    /** The maximum number of rooms in a single campaign. */
    private static final int MAX_ROOMS = 30;

    /**
     * Constructs a new {@code CampaignManager} with the given {@link RoomFactory}.
     * Initializes the battle chance to {@code 0.6}, sets the room counter to {@code 1},
     * and immediately generates the first room.
     *
     * @param RoomFactory the factory used to generate rooms; must not be {@code null}
     * @throws IllegalArgumentException if {@code RoomFactory} is {@code null}
     */
    public CampaignManager(RoomFactory RoomFactory) {
        if (RoomFactory == null) {
            throw new IllegalArgumentException("RoomFactory cannot be null");
        }
        this.RoomFactory = RoomFactory;
        this.battleChance = 0.6f;
        this.roomCounter = 1;
        this.currentRoom = RoomFactory.createNextRoom(battleChance);
    }

    /**
     * Resets the campaign to its initial state, restoring the default battle chance,
     * resetting the room counter to {@code 1}, and clearing the end-of-room flag.
     *
     * <p>Note: this does not generate a new first room. Call {@link #nextRoom()} or
     * re-construct the manager to obtain a fresh starting room.</p>
     */
    public void startNewCampaign() {
        this.battleChance = 0.6;
        this.roomCounter = 1;
        this.endOfRoom = false;
    }

    /**
     * Returns whether the player has completed the current room.
     *
     * @return {@code true} if the current room is finished; {@code false} otherwise
     */
    public boolean isEndOfRoom() {
        return endOfRoom;
    }

    /**
     * Sets the end-of-room flag to indicate whether the player has completed the current room.
     *
     * @param value {@code true} if the room is complete; {@code false} otherwise
     */
    public void setEndOfRoom(boolean value) {
        endOfRoom = value;
    }

    /**
     * Returns the current battle chance probability.
     *
     * @return a value between {@code 0.0} and {@code 1.0} representing the
     *         likelihood of the next room being a battle room
     */
    public double getBattleChance() {
        return battleChance;
    }

    /**
     * Sets the battle chance probability for future room generation.
     *
     * @param value the new battle chance, must be between {@code 0.0} and {@code 1.0} inclusive
     * @throws IllegalArgumentException if {@code value} is less than {@code 0} or greater than {@code 1}
     */
    public void setBattleChance(double value) {
        if (value < 0 || value > 1) {
            throw new IllegalArgumentException("Battle chance must be between 0 and 1");
        }
        this.battleChance = value;
    }

    /**
     * Returns the current room counter, representing how many rooms have been entered.
     *
     * @return the current room number (starts at {@code 1})
     */
    public int getRoomCounter() {
        return roomCounter;
    }

    /**
     * Returns the room the player is currently in.
     *
     * @return the current {@link Room}
     */
    public Room getCurrentRoom() {
        return currentRoom;
    }

    /**
     * Advances the campaign to the next room. Increments the room counter,
     * resets the end-of-room flag, and generates a new room via the {@link RoomFactory}.
     *
     * @return the newly generated {@link Room}
     * @throws IllegalStateException if the campaign has already reached {@value #MAX_ROOMS} rooms
     */
    public Room nextRoom() {
        if (roomCounter >= MAX_ROOMS) {
            throw new IllegalStateException("Campaign is already complete");
        }
        roomCounter++;
        endOfRoom = false;
        currentRoom = RoomFactory.createNextRoom(battleChance);
        return currentRoom;
    }
}