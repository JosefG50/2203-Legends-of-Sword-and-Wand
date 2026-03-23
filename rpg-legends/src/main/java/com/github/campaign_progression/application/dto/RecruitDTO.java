package com.github.application.dto;

public class RecruitDTO {
    private final String id;           // unique key from Inn
    private final String name;
    private final String specialization;
    private final int level;

    public RecruitDTO(String id, String name, String specialization, int level) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.level = level;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getSpecialization() { return specialization; }
    public int getLevel() { return level; }
}