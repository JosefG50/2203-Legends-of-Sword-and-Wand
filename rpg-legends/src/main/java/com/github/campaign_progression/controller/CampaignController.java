package com.github.campaign_progression.controller;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.github.campaign_progression.application.*;
import com.github.campaign_progression.application.dto.*;
import com.github.campaign_progression.domain.ItemType;

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

    public CampaignController(StartCampaignUseCase startCampaignUseCase,
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
                               ViewRecruitUseCase viewRecruitUseCase, InitializeBattleUseCase initializeBattleUseCase, SaveCampaignToPvpUseCase saveCampaignToPvpUseCase) {
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
    }

    @PostMapping("/start")
    public StartCampaignDTO startCampaign(@RequestBody HeroInstanceDTO initialHero) {
        return startCampaignUseCase.execute(initialHero);
    }

    @PostMapping("/load")
    public LoadCampaignDTO loadCampaign(@RequestBody CampaignSnapshotDTO snapshot) {
        return loadCampaignUseCase.execute(snapshot);
    }

    @PostMapping("/exit")
    public CampaignSnapshotDTO exitCampaign() {
        return exitCampaignUseCase.execute();
    }

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

    @GetMapping("/party")
    public List<HeroInstanceDTO> getParty() {
        return getPartyUseCase.execute();
    }

    @GetMapping("/inventory")
    public List<ItemDTO> getInventory() {
        return getInventoryUseCase.execute();
    }

    /** Buy an item from the inn shop */
    @PostMapping("/buy-item")
    public BuyItemResponseDTO buyItem(@RequestBody BuyItemRequestDTO request) {
        ItemType type = ItemType.valueOf(request.getItemType().toUpperCase());
        return buyItemUseCase.execute(type, request.getAmount());
    }

    /** Recruit a new hero */
    @PostMapping("/buy-recruit")
    public BuyRecruitResponseDTO buyRecruit(@RequestBody BuyRecruitRequestDTO request) {
        return buyRecruitUseCase.execute(request.getRoomCounter());
    }

    /** Generate enemies for a battle room */
    @PostMapping("/generate-battle")
    public BattleRoomResponseDTO generateBattle() {
        return generateBattleRoomUseCase.execute();
    }

    /** Get current inn shop items */
    @PostMapping("/shop")
    public GetShopResponseDTO getShop() {
        return getShopUseCase.execute();
    }

    /** Initialize inn — restore all party members to full HP/mana */
    @PostMapping("/inn-initialize")
    public InnInitializeResponseDTO innInitialize() {
        return innInitializeUseCase.execute();
    }

    /** Use an item on a hero */
    @PostMapping("/use-item")
    public ItemConsumeResponseDTO useItem(@RequestBody ItemConsumeRequestDTO request) {
        ItemType type = ItemType.valueOf(request.getItemType().toUpperCase());
        return itemConsumeUseCase.execute(type, request.getHeroIndex());
    }

    /** View available recruits at the inn */
    @PostMapping("/view-recruits")
    public List<RecruitDTO> viewRecruits() {
        return viewRecruitUseCase.execute();
    }


// Add to constructor and assignments, then:

@PostMapping("/initialize-battle")
public BattleRoomResponseDTO initializeBattle() {
    return initializeBattleUseCase.execute();
}

@PostMapping("/save")
public CampaignEndDTO saveCampaign() {
    return saveCampaignToPvpUseCase.execute();
}
}