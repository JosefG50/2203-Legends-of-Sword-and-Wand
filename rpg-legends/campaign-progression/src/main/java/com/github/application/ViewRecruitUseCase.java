package com.github.application;

import com.github.domain.HeroState;
import com.github.domain.Inn;

import java.util.Collections;
import java.util.Map;

public class ViewRecruitUseCase {

    private final Inn inn;

    public ViewRecruitUseCase(Inn inn) {
        if (inn == null) throw new IllegalArgumentException("Inn cannot be null");
        this.inn = inn;
    }

    /**
     * Returns the map of recruitable heroes.
     * 
     * @return a copy of recruits, or empty map if none available
     */
    public Map<String, HeroState> execute() {
        try {
            Map<String, HeroState> recruits = inn.viewRecruits();
            if (recruits.isEmpty()) {
                return Collections.emptyMap();
            }
            return recruits;
        } catch (Exception e) {
            // Any unexpected issue: log if needed, then return empty
            System.err.println("Error viewing recruits: " + e.getMessage());
            return Collections.emptyMap();
        }
    }
}