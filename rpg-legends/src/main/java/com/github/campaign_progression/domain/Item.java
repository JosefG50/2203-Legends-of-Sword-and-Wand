package com.github.campaign_progression.domain;

/**
 * Represents an item in the game.
 * <p>
 * The behavior and properties of the item (such as cost, healing values, etc.)
 * are determined by the associated {@link ItemType}.
 * </p>
 */
public class Item {

    /**
     * The type of this item, which defines its properties.
     */
    private final ItemType type;

    /**
     * Constructs a new Item with the given type.
     *
     * @param type the {@link ItemType} defining this item's properties
     * @throws IllegalArgumentException if the type is null
     */
    public Item(ItemType type) {
        if (type == null) throw new IllegalArgumentException("ItemType cannot be null");
        this.type = type;
    }

    /**
     * Gets the display name of the item.
     *
     * @return the item name
     */
    public String getName() {
        return type.getName();
    }

    /**
     * Gets the cost of the item.
     *
     * @return the cost in gold
     */
    public int getCost() {
        return type.getCost();
    }

    /**
     * Gets the amount of HP this item restores.
     *
     * @return the HP healing value
     */
    public int getHpHeal() {
        return type.getHpHeal();
    }

    /**
     * Gets the amount of mana this item restores.
     *
     * @return the mana healing value
     */
    public int getManaHeal() {
        return type.getManaHeal();
    }

    /**
     * Indicates whether this item can revive a character.
     *
     * @return true if the item can revive, false otherwise
     */
    public boolean canRevive() {
        return type.canRevive();
    }

    /**
     * Gets the underlying item type.
     *
     * @return the {@link ItemType}
     */
    public ItemType getType() {
        return type;
    }

    /**
     * Returns a string representation of the item including its properties.
     *
     * @return formatted string describing the item
     */
    @Override
    public String toString() {
        return String.format("%s [Cost=%d, HP=%d, Mana=%d, CanRevive=%s]",
                type.name(), type.getCost(), type.getHpHeal(), type.getManaHeal(), type.canRevive());
    }
}