package com.github.campaign_progression.domain;

import java.util.List;

/**
 * Concrete implementation of {@link CampaignSnapshot}, representing a point-in-time
 * snapshot of the campaign state.
 *
 * <p>This class captures all relevant campaign data including room progression,
 * battle chance, gold, experience, party state, and inventory items.
 * It is designed to support serialization frameworks (e.g. Jackson, JPA) via
 * its no-argument constructor, as well as direct instantiation via the full
 * parameterized constructor.</p>
 *
 * <p>Typical usage — saving state:</p>
 * <pre>{@code
 * CampaignSnapshot snapshot = new CampaignSnapshotImpl(
 *     manager.getRoomCounter(),
 *     manager.getBattleChance(),
 *     "BattleRoom",
 *     500, 1200,
 *     party,
 *     items
 * );
 * }</pre>
 */
public class CampaignSnapshotImpl implements CampaignSnapshot {

    /** The room number at the time the snapshot was taken. */
    private int roomCounter;

    /** The battle chance probability (0.0–1.0) at the time the snapshot was taken. */
    private double battleChance;

    /** The type or identifier of the current room at the time of the snapshot. */
    private String curRoom;

    /** The amount of gold held by the party at the time of the snapshot. */
    private int gold;

    /** The total experience points accumulated at the time of the snapshot. */
    private int exp;

    /** The list of hero states representing each party member's status at snapshot time. */
    private List<HeroState> party;

    /**
     * An array of item identifiers representing the player's inventory at snapshot time.
     * Each element corresponds to an item ID.
     */
    private int[] items;

    /**
     * No-argument constructor required for serialization frameworks (e.g. Jackson, JPA, Gson).
     * All fields will be left at their default values and should be populated via setters
     * or by the deserializing framework.
     */
    public CampaignSnapshotImpl() {
        // empty constructor for serialization / frameworks
    }

    /**
     * Constructs a fully initialized {@code CampaignSnapshotImpl} with all campaign state fields.
     *
     * @param roomCounter  the current room number in the campaign
     * @param battleChance the probability (0.0–1.0) of the next room being a battle room
     * @param curRoom      a string identifier for the current room type
     * @param gold         the amount of gold held by the party
     * @param exp          the total experience points accumulated
     * @param party        the list of {@link HeroState} objects representing party members
     * @param items        an array of item IDs representing the player's inventory
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRoomCounter() { return roomCounter; }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRoomCounter(int roomCounter) { this.roomCounter = roomCounter; }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getBattleChance() { return battleChance; }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setBattleChance(double battleChance) { this.battleChance = battleChance; }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getCurRoom() { return curRoom; }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCurRoom(String curRoom) { this.curRoom = curRoom; }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getGold() { return gold; }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setGold(int gold) { this.gold = gold; }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getExp() { return exp; }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setExp(int exp) { this.exp = exp; }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<HeroState> getParty() { return party; }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setParty(List<HeroState> party) { this.party = party; }

    /**
     * {@inheritDoc}
     */
    @Override
    public int[] getItems() { return items; }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setItems(int[] items) { this.items = items; }
}