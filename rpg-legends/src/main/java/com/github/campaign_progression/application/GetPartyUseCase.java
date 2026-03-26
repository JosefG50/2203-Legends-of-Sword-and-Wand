package com.github.campaign_progression.application;

import com.github.campaign_progression.application.dto.HeroInstanceDTO;
import com.github.campaign_progression.domain.PartyService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Use case for retrieving the current party as a list of {@link HeroInstanceDTO}.
 *
 * <p>Converts the domain representation of heroes ({@link com.github.campaign_progression.domain.HeroState})
 * into DTOs suitable for front-end display or API responses.</p>
 */
public class GetPartyUseCase {

    private final PartyService partyService;

    /**
     * Constructs the use case with the provided {@link PartyService}.
     *
     * @param partyService the domain party service
     */
    public GetPartyUseCase(PartyService partyService) {
        this.partyService = partyService;
    }

    /**
     * Executes the use case.
     *
     * @return a list of {@link HeroInstanceDTO} representing the current party
     */
    public List<HeroInstanceDTO> execute() {
        return partyService.getParty().stream()
                .map(HeroInstanceDTO::fromDomain)
                .collect(Collectors.toList());
    }
}