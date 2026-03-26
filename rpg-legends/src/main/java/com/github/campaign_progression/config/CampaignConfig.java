package com.github.campaign_progression.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.github.campaign_progression.domain.*;
import com.github.campaign_progression.application.*;

@Configuration
public class CampaignConfig {

    // ==================== DOMAIN BEANS ====================
    @Bean
    public PartyService partyService() {
        return new PartyService();
    }

    @Bean
    public InventoryService inventoryService() {
        return new InventoryService(500); // starting gold
    }

    @Bean
    public Inn inn() {
        return new Inn();
    }

    @Bean
    public RoomFactory roomFactory() {
        return new RoomFactory();
    }

    @Bean
    public CampaignManager campaignManager(RoomFactory roomFactory) {
        return new CampaignManager(roomFactory);
    }

    @Bean
    public EnemyFactory enemyFactory() {
        return new EnemyFactory();
    }

    // ==================== USE CASE BEANS ====================
    @Bean
    public StartCampaignUseCase startCampaignUseCase(CampaignManager campaignManager,
                                                      PartyService partyService,
                                                      InventoryService inventoryService) {
        return new StartCampaignUseCase(campaignManager, partyService, inventoryService);
    }

    @Bean
    public LoadCampaignUseCase loadCampaignUseCase(CampaignManager campaignManager,
                                                    PartyService partyService,
                                                    InventoryService inventoryService,
                                                    Inn inn) {
        return new LoadCampaignUseCase(campaignManager, partyService, inventoryService, inn);
    }

    @Bean
    public ExitCampaignUseCase exitCampaignUseCase(CampaignManager campaignManager,
                                                   PartyService partyService,
                                                   InventoryService inventoryService,
                                                   Inn inn) {
        return new ExitCampaignUseCase(campaignManager, partyService, inventoryService, inn);
    }

    @Bean
    public EndCampaignUseCase endCampaignUseCase(PartyService partyService,
                                                  InventoryService inventoryService) {
        return new EndCampaignUseCase(partyService, inventoryService);
    }

    @Bean
    public NextRoomUseCase nextRoomUseCase(CampaignManager campaignManager) {
        return new NextRoomUseCase(campaignManager);
    }

    @Bean
    public GetPartyUseCase getPartyUseCase(PartyService partyService) {
        return new GetPartyUseCase(partyService);
    }

    @Bean
    public GetInventoryUseCase getInventoryUseCase(InventoryService inventoryService) {
        return new GetInventoryUseCase(inventoryService);
    }

    @Bean
    public GetShopUseCase getShopUseCase(Inn inn) {
        return new GetShopUseCase(inn);
    }

    @Bean
    public BuyItemUseCase buyItemUseCase(Inn inn, InventoryService inventoryService) {
        return new BuyItemUseCase(inn, inventoryService);
    }

    @Bean
    public BuyRecruitUseCase buyRecruitUseCase(Inn inn,
                                               PartyService partyService,
                                               InventoryService inventoryService) {
        return new BuyRecruitUseCase(inn, partyService, inventoryService);
    }

    // Fixed: register the real ViewRecruitUseCase, not the test
    @Bean
    public ViewRecruitUseCase viewRecruitUseCase(Inn inn) {
        return new ViewRecruitUseCase(inn);
    }

    @Bean
    public InnInitializeUseCase innInitializeUseCase(PartyService partyService) {
        return new InnInitializeUseCase(partyService);
    }

    @Bean
    public GenerateBattleRoomUseCase generateBattleRoomUseCase(PartyService partyService,
                                                               EnemyFactory enemyFactory) {
        return new GenerateBattleRoomUseCase(partyService, enemyFactory);
    }

    @Bean
    public ItemConsumeUseCase itemConsumeUseCase(InventoryService inventoryService,
                                                  PartyService partyService,
                                                  CampaignManager campaignManager) {
        return new ItemConsumeUseCase(inventoryService, partyService, campaignManager);
    }

    // ==================== EXTERNAL BEANS ====================
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public InitializeBattleUseCase initializeBattleUseCase(
            GenerateBattleRoomUseCase generateBattleRoomUseCase,
            RestTemplate restTemplate,
            @Value("${battlelib.url}") String battleLibUrl) {
        return new InitializeBattleUseCase(generateBattleRoomUseCase, restTemplate, battleLibUrl);
    }

    @Bean
    public SaveCampaignToPvpUseCase saveCampaignToPvpUseCase(
            EndCampaignUseCase endCampaignUseCase,
            RestTemplate restTemplate,
            @Value("${pvp.url}") String pvpUrl) {
        return new SaveCampaignToPvpUseCase(endCampaignUseCase, restTemplate, pvpUrl);
    }
    
}