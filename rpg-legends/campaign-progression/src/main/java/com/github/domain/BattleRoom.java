package com.github.domain;

public class BattleRoom implements Room{
    // TO DO: 
    // 1. class description/documentation
    // 2. Decide type of List (ArrayList, LinkedList, etc.)
    private List<Enemy> enemies;
    private List<Entity> turnOrder;

    
    public int getExp(){
        int exp = 0;
        for (Enemy enemy: enemies){
            exp += enemy.getExp();
        }
        return exp;
    }
    public int getGold(){
        int gold = 0;
        for (Enemy enemy: enemies){
            gold += enemy.getGold();
        }
        return gold;
    }
    public List getTurnOrder(){
        return turnOrder;
    }
    public List getEnemies(){
        return enemies;
    }

    public void setTurnOrder(List allies, List enemies){
        //TODO: implement turn order sorting algorithm (currently random)
        turnOrder = new ArrayList<>();
        turnOrder.addAll(allies);
        turnOrder.addAll(enemies);
        Collections.shuffle(turnOrder);
    }
    public void createEnemies(List allies){
        // TODO: implement enemy creation algorithm (currently creates enemies with same level as allies)
        enemies = new ArrayList<>();
        for (int i = 0; i < allies.size(); i++){
            enemies.add(new Enemy(allies.get(i).getLevel()));
        }
    }
    public boolean isOver(){
        for (Enemy enemy: enemies){
            if (enemy.isAlive()){
                return false;
            }
        }
        return true;
    }
}

