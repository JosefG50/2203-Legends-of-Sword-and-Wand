package com.github.campaign_progression.application;

import com.github.campaign_progression.domain.HeroState;
import com.github.campaign_progression.domain.Inn;
import com.github.campaign_progression.application.dto.RecruitDTO;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Use case for viewing the list of recruitable heroes in the inn.
 *
 * <p>This class interacts with the {@link Inn} domain object to fetch all
 * available recruits and maps them to {@link RecruitDTO} objects
 * suitable for presentation in the application or UI layer.</p>
 *
 * <p>Any errors in fetching recruits are caught internally, and an empty
 * list is returned to avoid propagation of domain exceptions to the UI.</p>
 */
public class ViewRecruitUseCase {

    /** The inn domain object providing recruit information. */
    private final Inn inn;

    /**
     * Constructs a new {@code ViewRecruitUseCase} with the specified inn.
     *
     * @param inn the {@link Inn} domain object; must not be {@code null}
     * @throws IllegalArgumentException if {@code inn} is {@code null}
     */
    public ViewRecruitUseCase(Inn inn) {
        if (inn == null) throw new IllegalArgumentException("Inn cannot be null");
        this.inn = inn;
    }

    /**
     * Returns a list of heroes available for recruitment, mapped as DTOs.
     *
     * <p>The mapping preserves:</p>
     * <ul>
     *     <li>Hero unique identifier (map key)</li>
     *     <li>Hero name</li>
     *     <li>Specialization (MAGE, WARRIOR, ORDER, CHAOS)</li>
     *     <li>Level (current overall hero level)</li>
     * </ul>
     *
     * <p>If there are no recruits available, or an exception occurs while
     * fetching them, this method returns an empty list.</p>
     *
     * @return a {@link List} of {@link RecruitDTO} objects representing recruitable heroes,
     *         or an empty list if none are available or on error
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