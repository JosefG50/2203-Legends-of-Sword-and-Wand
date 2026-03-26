package com.github.campaign_progression.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.github.campaign_progression.application.*;
import com.github.campaign_progression.application.dto.*;
import com.github.campaign_progression.domain.ItemType;

/**
 * REST controller for managing campaign-related actions in the RPG system.
 *
 * <p>Exposes endpoints for starting/loading/exiting campaigns, navigating rooms,
 * managing party members and inventory, interacting with the inn shop,
 * generating battle rooms, and saving campaigns to PVP.</p>
 */
@RestController
@RequestMapping("/campaign")
public class CampaignController {

    private final StartCampaignUseCase startCampaignUseCase;
    private final LoadCampaignUseCase loadCampaignUseCase;
    private final ExitCampaignUseCase exitCampaignUseCase;
    private final NextRoomUseCase nextRoomUseCase;
    private final EndCampaignUseCase endCampaignUseCase;
    private final GetPartyUseCase getPartyUseCase;
    private final GetInventoryUseCase getInventoryUseCase;
    private final BuyItemUseCase buyItemUseCase;
    private final BuyRecruitUseCase buyRecruitUseCase;
    private final GenerateBattleRoomUseCase generateBattleRoomUseCase;
    private final GetShopUseCase getShopUseCase;
    private final InnInitializeUseCase innInitializeUseCase;
    private final ItemConsumeUseCase itemConsumeUseCase;
    private final ViewRecruitUseCase viewRecruitUseCase;
    private final InitializeBattleUseCase initializeBattleUseCase;
    private final SaveCampaignToPvpUseCase saveCampaignToPvpUseCase;

    // New use cases
    private final GainExpUseCase gainExpUseCase;
    private final GainGoldUseCase gainGoldUseCase;

    public CampaignController(
            StartCampaignUseCase startCampaignUseCase,
            LoadCampaignUseCase loadCampaignUseCase,
            ExitCampaignUseCase exitCampaignUseCase,
            NextRoomUseCase nextRoomUseCase,
            EndCampaignUseCase endCampaignUseCase,
            GetPartyUseCase getPartyUseCase,
            GetInventoryUseCase getInventoryUseCase,
            BuyItemUseCase buyItemUseCase,
            BuyRecruitUseCase buyRecruitUseCase,
            GenerateBattleRoomUseCase generateBattleRoomUseCase,
            GetShopUseCase getShopUseCase,
            InnInitializeUseCase innInitializeUseCase,
            ItemConsumeUseCase itemConsumeUseCase,
            ViewRecruitUseCase viewRecruitUseCase,
            InitializeBattleUseCase initializeBattleUseCase,
            SaveCampaignToPvpUseCase saveCampaignToPvpUseCase,
            GainExpUseCase gainExpUseCase,
            GainGoldUseCase gainGoldUseCase
    ) {
        this.startCampaignUseCase = startCampaignUseCase;
        this.loadCampaignUseCase = loadCampaignUseCase;
        this.exitCampaignUseCase = exitCampaignUseCase;
        this.nextRoomUseCase = nextRoomUseCase;
        this.endCampaignUseCase = endCampaignUseCase;
        this.getPartyUseCase = getPartyUseCase;
        this.getInventoryUseCase = getInventoryUseCase;
        this.buyItemUseCase = buyItemUseCase;
        this.buyRecruitUseCase = buyRecruitUseCase;
        this.generateBattleRoomUseCase = generateBattleRoomUseCase;
        this.getShopUseCase = getShopUseCase;
        this.innInitializeUseCase = innInitializeUseCase;
        this.itemConsumeUseCase = itemConsumeUseCase;
        this.viewRecruitUseCase = viewRecruitUseCase;
        this.initializeBattleUseCase = initializeBattleUseCase;
        this.saveCampaignToPvpUseCase = saveCampaignToPvpUseCase;

        this.gainExpUseCase = gainExpUseCase;
        this.gainGoldUseCase = gainGoldUseCase;
    }

    /**
     * Start a new campaign with the given initial hero.
     *
     * @param initialHero the hero to start the campaign with
     * @return a {@link StartCampaignDTO} containing campaign state
     */
    @PostMapping("/start")
    public StartCampaignDTO startCampaign(@RequestBody HeroInstanceDTO initialHero) {
        return startCampaignUseCase.execute(initialHero);
    }

    /**
     * Load a saved campaign from a snapshot.
     *
     * @param snapshot the campaign snapshot to load
     * @return a {@link LoadCampaignDTO} with restored state
     */
    @PostMapping("/load")
    public LoadCampaignDTO loadCampaign(@RequestBody CampaignSnapshotDTO snapshot) {
        return loadCampaignUseCase.execute(snapshot);
    }

    /** Exit the current campaign and return a snapshot of its state. */
    @PostMapping("/exit")
    public CampaignSnapshotDTO exitCampaign() {
        return exitCampaignUseCase.execute();
    }

    /** Move to the next room or end the campaign if already complete. */
    @PostMapping("/next-room")
    public Object nextRoom() {
        try {
            return nextRoomUseCase.execute();
        } catch (IllegalStateException ex) {
            if (ex.getMessage().contains("already complete")) {
                return endCampaignUseCase.execute();
            }
            throw ex;
        }
    }

    /** Get current party members as DTOs. */
    @GetMapping("/party")
    public List<HeroInstanceDTO> getParty() {
        return getPartyUseCase.execute();
    }

    /** Get current inventory items as DTOs. */
    @GetMapping("/inventory")
    public List<ItemDTO> getInventory() {
        return getInventoryUseCase.execute();
    }

    /** Buy an item from the inn shop. */
    @PostMapping("/buy-item")
    public BuyItemResponseDTO buyItem(@RequestBody BuyItemRequestDTO request) {
        ItemType type = ItemType.valueOf(request.getItemType().toUpperCase());
        return buyItemUseCase.execute(type, request.getAmount());
    }

    /** Recruit a new hero from the inn. */
    @PostMapping("/buy-recruit")
    public BuyRecruitResponseDTO buyRecruit(@RequestBody BuyRecruitRequestDTO request) {
        return buyRecruitUseCase.execute(request.getRoomCounter());
    }

    /** Generate enemies for a battle room. */
    @PostMapping("/generate-battle")
    public BattleRoomResponseDTO generateBattle() {
        return generateBattleRoomUseCase.execute();
    }

    /** Get current inn shop items. */
    @PostMapping("/shop")
    public GetShopResponseDTO getShop() {
        return getShopUseCase.execute();
    }

    /** Initialize inn — restore all party members to full HP/mana. */
    @PostMapping("/inn-initialize")
    public InnInitializeResponseDTO innInitialize() {
        return innInitializeUseCase.execute();
    }

    /** Use an item on a hero. */
    @PostMapping("/use-item")
    public ItemConsumeResponseDTO useItem(@RequestBody ItemConsumeRequestDTO request) {
        ItemType type = ItemType.valueOf(request.getItemType().toUpperCase());
        return itemConsumeUseCase.execute(type, request.getHeroIndex());
    }

    /** View available recruits at the inn. */
    @PostMapping("/view-recruits")
    public List<RecruitDTO> viewRecruits() {
        return viewRecruitUseCase.execute();
    }

    /** Initialize a battle via BattleLib. */
    @PostMapping("/initialize-battle")
    public BattleRoomResponseDTO initializeBattle() {
        return initializeBattleUseCase.execute();
    }

    /** Save campaign results to PVP server. */
    @PostMapping("/save")
    public CampaignEndDTO saveCampaign() {
        return saveCampaignToPvpUseCase.execute();
    }
    /**
     * Awards experience to all party members.
     *
     * @param exp the amount of experience to distribute
     * @return a list of HeroExpDTO showing each hero's new EXP
     */
    public List<HeroExpDTO> gainExp(int exp) {
        return gainExpUseCase.execute(exp);
    }

    /**
     * Adds gold to the player's inventory.
     *
     * @param amount the amount of gold to add
     * @return a GoldDTO containing the added gold and new total
     */
    public GoldDTO gainGold(int amount) {
        return gainGoldUseCase.execute(amount);
    }
}