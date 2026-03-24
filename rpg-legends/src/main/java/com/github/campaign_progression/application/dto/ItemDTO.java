package com.github.campaign_progression.application.dto;
    import com.github.campaign_progression.domain.ItemType;


public class ItemDTO {

    private String name;
    private int cost;
    private int hpHeal;
    private int manaHeal;
    private boolean canRevive;

    private int quantity; // 🔥 important for inventory snapshot

    // Constructors
    public ItemDTO() {}

    public ItemDTO(
            String name,
            int cost,
            int hpHeal,
            int manaHeal,
            boolean canRevive,
            int quantity
    ) {
        this.name = name;
        this.cost = cost;
        this.hpHeal = hpHeal;
        this.manaHeal = manaHeal;
        this.canRevive = canRevive;
        this.quantity = quantity;
    }

    // Getters & Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getCost() { return cost; }
    public void setCost(int cost) { this.cost = cost; }

    public int getHpHeal() { return hpHeal; }
    public void setHpHeal(int hpHeal) { this.hpHeal = hpHeal; }

    public int getManaHeal() { return manaHeal; }
    public void setManaHeal(int manaHeal) { this.manaHeal = manaHeal; }

    public boolean isCanRevive() { return canRevive; }
    public void setCanRevive(boolean canRevive) { this.canRevive = canRevive; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }


public static ItemDTO fromDomain(ItemType type, int quantity) {
    if (type == null) {
        throw new IllegalArgumentException("ItemType cannot be null");
    }

    return new ItemDTO(
            type.getName(),
            type.getCost(),
            type.getHpHeal(),
            type.getManaHeal(),
            type.canRevive(),
            quantity
    );
}
}