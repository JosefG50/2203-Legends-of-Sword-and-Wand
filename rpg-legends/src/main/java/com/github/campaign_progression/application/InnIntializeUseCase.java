package com.github.application;

import com.github.domain.PartyService;
import com.github.domain.PartyService.RestoreStatus;
import com.github.application.dto.InnInitializeResponseDTO;

import java.util.List;
import java.util.stream.Collectors;

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