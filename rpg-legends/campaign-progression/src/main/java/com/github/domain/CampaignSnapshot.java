public interface CampaignSnapshot {
    
}
interface CampaignSnapshot {
        + getRoomCounter(): int
        + setRoomCounter(roomCounter: int): void

        + getBattleChance(): double
        + setBattleChance(chance: double): void

        + getCurRoom(): String
        + setCurRoom(room: String): void

        + getGold(): int
        + setGold(gold: int): void

        + getExp(): int
        + setExp(exp: int): void

        + getParty(): List<HeroInstance>
        + setParty(party: List<HeroInstance>): void

        + getInnRecruits(): List<HeroInstance>
        + setInnRecruits(recruits: List<HeroInstance>): void

        + getItems(): List<Item>
        + setItems(items: List<Item>): void
    }