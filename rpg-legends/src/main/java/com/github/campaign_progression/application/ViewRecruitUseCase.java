package com.github.campaign_progression.application;

import com.github.campaign_progression.domain.HeroState;
import com.github.campaign_progression.domain.Inn;
import com.github.campaign_progression.application.dto.RecruitDTO;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ViewRecruitUseCase {

    private final Inn inn;

    public ViewRecruitUseCase(Inn inn) {
        if (inn == null) throw new IllegalArgumentException("Inn cannot be null");
        this.inn = inn;
    }

    /**
     * Returns a list of recruitable heroes as DTOs.
     */
    public List<RecruitDTO> execute() {
        try {
            Map<String, HeroState> recruits = inn.viewRecruits();
            if (recruits.isEmpty()) return Collections.emptyList();

            return recruits.entrySet().stream()
                    .map(entry -> {
                        HeroState hero = entry.getValue();
                        return new RecruitDTO(
                                entry.getKey(),
                                hero.getName(),
                                hero.getSpecialization(),
                                hero.getLevel() 
                        );
                    })
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.err.println("Error viewing recruits: " + e.getMessage());
            return Collections.emptyList();
        }
    }
}