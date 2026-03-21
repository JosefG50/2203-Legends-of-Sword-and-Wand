package com.github.application;

import com.github.domain.PartyService;
import com.github.domain.PartyService.RestoreStatus;

import java.util.List;

public class InnInitializeUseCase {

    private final PartyService partyService;

    public InnInitializeUseCase(PartyService partyService) {
        this.partyService = partyService;
    }

    /**
     * Restore all heroes to max HP and Mana, and return detailed status.
     *
     * @return List of heroes and amounts restored
     */
    public List<RestoreStatus> execute() {
        return partyService.maxRestoreWithStatus();
    }
}