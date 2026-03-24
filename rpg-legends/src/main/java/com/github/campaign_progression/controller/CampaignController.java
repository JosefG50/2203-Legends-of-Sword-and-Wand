package com.github.campaign_progression.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.campaign_progression.application.ExitCampaignUseCase;
import com.github.campaign_progression.application.LoadCampaignUseCase;
import com.github.campaign_progression.application.NextRoomUseCase;
import com.github.campaign_progression.application.StartCampaignUseCase;
import com.github.campaign_progression.application.dto.CampaignEndDTO;
import com.github.campaign_progression.application.dto.CampaignSnapshotDTO;
import com.github.campaign_progression.application.dto.HeroInstanceDTO;
import com.github.campaign_progression.application.dto.ItemDTO;
import com.github.campaign_progression.application.dto.LoadCampaignDTO;
import com.github.campaign_progression.application.dto.StartCampaignDTO;

@RestController
@RequestMapping("/campaign")
public class CampaignController {

    private final StartCampaignUseCase startCampaignUseCase;
    private final LoadCampaignUseCase loadCampaignUseCase;
    private final ExitCampaignUseCase exitCampaignUseCase;
    private final NextRoomUseCase nextRoomUseCase;

    public CampaignController(StartCampaignUseCase startCampaignUseCase,
            LoadCampaignUseCase loadCampaignUseCase,
            ExitCampaignUseCase exitCampaignUseCase,
            NextRoomUseCase nextRoomUseCase) {
        this.startCampaignUseCase = startCampaignUseCase;
        this.loadCampaignUseCase = loadCampaignUseCase;
        this.exitCampaignUseCase = exitCampaignUseCase;
        this.nextRoomUseCase = nextRoomUseCase;
    }

    /**
     * Start a new campaign with an initial hero.
     */
    @PostMapping("/start")
    public StartCampaignDTO startCampaign(@RequestBody HeroInstanceDTO initialHero) {
        return startCampaignUseCase.execute(initialHero);
    }

    /**
     * Load an existing campaign from snapshot.
     */
    @PostMapping("/load")
    public LoadCampaignDTO loadCampaign(@RequestBody CampaignSnapshotDTO snapshot) {
        return loadCampaignUseCase.execute(snapshot);
    }

    /**
     * Exit the campaign, returning a snapshot of the current state.
     */
    @PostMapping("/exit")
    public CampaignSnapshotDTO exitCampaign() {
        return exitCampaignUseCase.execute();
    }

    /**
     * Move to the next room if end-of-room is true, return room type DTO.
     */
    @PostMapping("/next-room")
    public Object nextRoom() {
        try {
            // Attempt to move to the next room
            return nextRoomUseCase.execute();
        } catch (IllegalStateException ex) {
            if (ex.getMessage().contains("already complete")) {
                // Campaign reached past max room, trigger end-of-campaign
                CampaignEndDTO endDTO = exitCampaignUseCase.execute();
                return endDTO;
            }
            // Other unexpected exceptions
            throw ex;
        }
    }

    /**
     * Optional: fetch current party (could be used by Godot UI to display hero
     * list)
     */
    @GetMapping("/party")
    public List<HeroInstanceDTO> getParty() {
        return startCampaignUseCase.getCurrentParty(); // or another use case if you track it
    }

    /**
     * Optional: fetch current inventory
     */
    @GetMapping("/inventory")
    public List<ItemDTO> getInventory() {
        return startCampaignUseCase.getCurrentInventory(); // or use InventoryUseCase
    }
}