package BattleService.dto;

public class StartBattleRequest {

    private String heroName;
    private int heroHp;
    private int heroMana;
    private int heroShield;
    private int heroAttack;
    private int heroDefense;

    private String enemyName;
    private int enemyHp;
    private int enemyMana;
    private int enemyShield;
    private int enemyAttack;
    private int enemyDefense;

    public StartBattleRequest() {
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }

    public int getHeroHp() {
        return heroHp;
    }

    public void setHeroHp(int heroHp) {
        this.heroHp = heroHp;
    }

    public int getHeroMana() {
        return heroMana;
    }

    public void setHeroMana(int heroMana) {
        this.heroMana = heroMana;
    }

    public int getHeroShield() {
        return heroShield;
    }

    public void setHeroShield(int heroShield) {
        this.heroShield = heroShield;
    }

    public int getHeroAttack() {
        return heroAttack;
    }

    public void setHeroAttack(int heroAttack) {
        this.heroAttack = heroAttack;
    }

    public int getHeroDefense() {
        return heroDefense;
    }

    public void setHeroDefense(int heroDefense) {
        this.heroDefense = heroDefense;
    }

    public String getEnemyName() {
        return enemyName;
    }

    public void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
    }

    public int getEnemyHp() {
        return enemyHp;
    }

    public void setEnemyHp(int enemyHp) {
        this.enemyHp = enemyHp;
    }

    public int getEnemyMana() {
        return enemyMana;
    }

    public void setEnemyMana(int enemyMana) {
        this.enemyMana = enemyMana;
    }

    public int getEnemyShield() {
        return enemyShield;
    }

    public void setEnemyShield(int enemyShield) {
        this.enemyShield = enemyShield;
    }

    public int getEnemyAttack() {
        return enemyAttack;
    }

    public void setEnemyAttack(int enemyAttack) {
        this.enemyAttack = enemyAttack;
    }

    public int getEnemyDefense() {
        return enemyDefense;
    }

    public void setEnemyDefense(int enemyDefense) {
        this.enemyDefense = enemyDefense;
    }
}