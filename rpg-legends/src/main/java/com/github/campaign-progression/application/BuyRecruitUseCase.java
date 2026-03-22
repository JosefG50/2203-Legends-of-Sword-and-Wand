package com.github.application;

import com.github.domain.HeroState;
import com.github.domain.Inn;
import com.github.domain.PartyService;
import com.github.application.dto.BuyRecruitResponseDTO;

import java.util.Optional;

public class BuyRecruitUseCase {

    private final Inn inn;
    private final PartyService partyService;

    public BuyRecruitUseCase(Inn inn, PartyService partyService) {
        this.inn = inn;
        this.partyService = partyService;
    }

    public BuyRecruitResponseDTO execute(int roomCounter) {

        if (roomCounter > 10) {
            return new BuyRecruitResponseDTO(
                    false,
                    "Recruits are no longer available after room 10",
                    null, null, 0
            );
        }

        if (!partyService.hasSpace()) {
            return new BuyRecruitResponseDTO(
                    false,
                    "Party is full. Cannot recruit more heroes.",
                    null, null, 0
            );
        }

        Optional<HeroState> recruitOpt = inn.generateRecruit(partyService, roomCounter);

        if (recruitOpt.isEmpty()) {
            return new BuyRecruitResponseDTO(
                    false,
                    "No recruit available",
                    null, null, 0
            );
        }

        HeroState hero = recruitOpt.get();

        try {
            partyService.addHero(hero);

            return new BuyRecruitResponseDTO(
                    true,
                    "Recruit successful",
                    hero.getName(),                 // MUST exist
                    hero.getSpecialization(),
                    hero.getTotalLevel()            // MUST exist
            );

        } catch (IllegalStateException ex) {
            return new BuyRecruitResponseDTO(
                    false,
                    ex.getMessage(),
                    null, null, 0
            );
        }
    }
}