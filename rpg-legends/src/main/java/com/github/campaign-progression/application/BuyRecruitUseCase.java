package com.github.application;

import com.github.domain.HeroState;
import com.github.domain.Inn;
import com.github.domain.PartyService;

import java.util.Optional;

public class BuyRecruitUseCase {

    private final Inn inn;
    private final PartyService partyService;

    public BuyRecruitUseCase(Inn inn, PartyService partyService) {
        this.inn = inn;
        this.partyService = partyService;
    }

    /**
     * Attempt to recruit a hero from the inn.
     *
     * @param roomCounter The current room number (recruits not available after 10)
     * @return Optional<String> containing error message if failed, empty if successful
     */
    public Optional<String> execute(int roomCounter) {
        if (roomCounter > 10) {
            return Optional.of("Recruits are no longer available after room 10");
        }

        if (!partyService.hasSpace()) {
            return Optional.of("Party is full. Cannot recruit more heroes.");
        }

        // Generate recruitable hero
        return inn.generateRecruit(partyService, roomCounter)
                .map(hero -> {
                    try {
                        partyService.addHero(hero);
                        return null; // Success
                    } catch (IllegalStateException ex) {
                        return ex.getMessage(); // Party full (should not happen)
                    }
                });
    }
}