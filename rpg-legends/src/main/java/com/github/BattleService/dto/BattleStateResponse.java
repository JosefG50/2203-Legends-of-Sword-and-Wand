package com.github.BattleService.dto;

public class BattleStateResponse {

    private String currentUnitName;
    private int heroHp;
    private int heroMana;
    private int enemyHp;
    private int enemyMana;
    private boolean battleOver;
    private String result;

    public BattleStateResponse() {
    }

    public BattleStateResponse(String currentUnitName, int heroHp, int heroMana, int enemyHp, int enemyMana, boolean battleOver, String result) {
        this.currentUnitName = currentUnitName;
        this.heroHp = heroHp;
        this.heroMana = heroMana;
        this.enemyHp = enemyHp;
        this.enemyMana = enemyMana;
        this.battleOver = battleOver;
        this.result = result;
    }

    public String getCurrentUnitName() {
        return currentUnitName;
    }

    public void setCurrentUnitName(String currentUnitName) {
        this.currentUnitName = currentUnitName;
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

    public boolean isBattleOver() {
        return battleOver;
    }

    public void setBattleOver(boolean battleOver) {
        this.battleOver = battleOver;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}