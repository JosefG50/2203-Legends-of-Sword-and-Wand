package com.github.domain;

public class Inn implements Room{
    private static final int MAX_PARTY_SIZE = 5;
        private static final String[] CLASSES = {"MAGE", "WARRIOR", "ORDER", "CHAOS"};

    private static final List<ItemTest> SHOPLIST = List.of(
            new ItemTest("Health Potion", 10),
            new ItemTest("Mana Potion", 15),
            new ItemTest("Sword", 50),
            new ItemTest("Shield", 40)
    );
    private Map<String, HeroState> recruits = new HashMap<>();

    public Inn(){
        
    }

    public void intialize(PartyServiceTest partyService) {
        partyService.maxRestore(); 
        if (party.getHeroCount() >= MAX_PARTY_SIZE) {
            availableHero = null;
            recruitCost = 0;
            return;
        }
        Random rand = new Random();

        int level = rand.nextInt(4) + 1; // 1–4
        String specialization = CLASSES[rand.nextInt(CLASSES.length)];

        HeroState hero = new HeroState();
        hero.setSpecialization(specialization);

        // Set level based on specialization
        switch (specialization) {
            case "MAGE":
                hero.setMageLvl(level);
                break;
            case "WARRIOR":
                hero.setWarriorLvl(level);
                break;
            case "ORDER":
                hero.setOrderLvl(level);
                break;
            case "CHAOS":
                hero.setChaosLvl(level);
                break;
        }

        // Cost logic
        int cost = (level == 1) ? 0 : level * 200;

        this.availableHero = hero;
        this.recruitCost = cost;
    }

    
    public Map<String, HeroState> viewRecruits() {
        return new HashMap<>(recruits);
    }
    public boolean hasRecruit(String name) {
        return recruits.containsKey(name);
    }
    public List<ItemTest> getShop() {
        return new ArrayList<>(SHOPLIST);
    }
}