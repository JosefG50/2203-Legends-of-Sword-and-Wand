package com.github.campaign_progression.application;

import com.github.campaign_progression.application.dto.HeroExpDTO;
import com.github.campaign_progression.domain.HeroState;
import com.github.campaign_progression.domain.PartyService;

import java.util.ArrayList;
import java.util.List;

/**
 * Use case for distributing experience points to all heroes in the party.
 */
public class GainExpUseCase {

    private final PartyService partyService;

    /**
     * Constructs the use case with the given party service.
     *
     * @param partyService the domain service managing the party
     */
    public GainExpUseCase(PartyService partyService) {
        if (partyService == null) throw new IllegalArgumentException("PartyService cannot be null");
        this.partyService = partyService;
    }

    /**
     * Distributes the total experience evenly among all party members.
     *
     * @param totalExp total experience points to distribute
     * @return a list of HeroExpDTO showing each hero's name, experience gained, and new total EXP
     * @throws IllegalStateException if the party is empty
     */
    public List<HeroExpDTO> execute(int totalExp) {
        List<HeroState> party = partyService.getParty();
        if (party.isEmpty()) {
            throw new IllegalStateException("No party members");
        }

        int expPerHero = totalExp / party.size();
        List<HeroExpDTO> result = new ArrayList<>();

        for (HeroState hero : party) {
            int beforeExp = hero.getCurExp();
            hero.gainExp(expPerHero);
            int afterExp = hero.getCurExp();

            result.add(new HeroExpDTO(hero.getName(), afterExp - beforeExp, afterExp));
        }

        return result;
    }
}