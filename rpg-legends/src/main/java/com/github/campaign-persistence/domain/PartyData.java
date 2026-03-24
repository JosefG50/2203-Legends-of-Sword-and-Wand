package com.github.domain;

import java.util.List;
import java.util.Map;

/**
 * The PartyData class is a Data Transfer Object (DTO) that holds party-related information.
 * it includes hero statistics, levels, and health/mana status.
 */
public class PartyData {
    /**
     * A map containing hero names and their corresponding statistics.
     */
    public Map<String, Integer> heroStats;

    /**
     * A list of levels for the heroes in the party.
     */
    public List<Integer> levels;

    /**
     * A map containing hero names and their corresponding HP and Mana status.
     */
    public Map<String, Integer> hpManaStatus;
}
