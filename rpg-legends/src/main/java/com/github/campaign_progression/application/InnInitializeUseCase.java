package com.github.campaign_progression.application;

import java.util.List;
import java.util.stream.Collectors;

import com.github.campaign_progression.application.dto.InnInitializeResponseDTO;
import com.github.campaign_progression.domain.PartyService;
import com.github.campaign_progression.domain.PartyService.RestoreStatus;

public class InnInitializeUseCase {

    private final PartyService partyService;

    public InnInitializeUseCase(PartyService partyService) {
        this.partyService = partyService;
    }

    public InnInitializeResponseDTO execute() {

        List<RestoreStatus> statusList = partyService.maxRestoreWithStatus();

        List<InnInitializeResponseDTO.HeroRestoreDTO> dtoList =
                statusList.stream()
                        .map(s -> new InnInitializeResponseDTO.HeroRestoreDTO(
                                s.heroName,
                                s.hpRestored,
                                s.manaRestored
                        ))
                        .collect(Collectors.toList());

        return new InnInitializeResponseDTO(dtoList);
    }
}