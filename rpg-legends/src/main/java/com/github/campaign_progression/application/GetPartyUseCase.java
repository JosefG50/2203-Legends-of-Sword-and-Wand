package com.github.campaign_progression.application;

import com.github.campaign_progression.domain.PartyService;
import com.github.campaign_progression.application.dto.HeroInstanceDTO;
import java.util.List;
import java.util.stream.Collectors;

public class GetPartyUseCase {
    private final PartyService partyService;

    public GetPartyUseCase(PartyService partyService) {
        this.partyService = partyService;
    }

    public List<HeroInstanceDTO> execute() {
        return partyService.getParty().stream()
                .map(HeroInstanceDTO::fromDomain)
                .collect(Collectors.toList());
    }
}