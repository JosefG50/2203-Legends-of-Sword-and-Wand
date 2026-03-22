package BattleService.domain;

public class LivingEntity {

    private final String name;
    private final int maxHp;
    private int hp;
    private final int maxMana;
    private int mana;
    private int shield;
    private final int attack;
    private final int defense;

    public LivingEntity(String name, int hp, int mana, int shield, int attack, int defense) {
        this.name = name;
        this.maxHp = hp;
        this.hp = hp;
        this.maxMana = mana;
        this.mana = mana;
        this.shield = shield;
        this.attack = attack;
        this.defense = defense;
    }

    public String getName() {
        return name;
    }

    public int getHp() {
        return hp;
    }

    public int getMana() {
        return mana;
    }

    public int getShield() {
        return shield;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public void takeDamage(int amount) {
        if (amount <= 0 || !isAlive()) {
            return;
        }

        int damage = amount;

        if (shield > 0) {
            int absorbed = Math.min(shield, damage);
            shield -= absorbed;
            damage -= absorbed;
        }

        if (damage > 0) {
            hp = Math.max(0, hp - damage);
        }
    }

    public void restoreHp(int amount) {
        if (amount <= 0 || !isAlive()) {
            return;
        }

        hp = Math.min(maxHp, hp + amount);
    }

    public void restoreMana(int amount) {
        if (amount <= 0 || !isAlive()) {
            return;
        }

        mana = Math.min(maxMana, mana + amount);
    }

    public void useMana(int amount) {
        if (amount <= 0 || !isAlive()) {
            return;
        }

        mana = Math.max(0, mana - amount);
    }
}