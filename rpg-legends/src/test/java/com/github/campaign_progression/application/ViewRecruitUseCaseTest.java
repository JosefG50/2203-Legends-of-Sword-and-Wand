package com.github.campaign_progression.application;

import com.github.campaign_progression.domain.HeroState;
import com.github.campaign_progression.domain.Inn;
import com.github.campaign_progression.application.dto.RecruitDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ViewRecruitUseCaseTest {

    private Inn inn;
    private ViewRecruitUseCase useCase;

    @BeforeEach
    void setUp() {
        inn = mock(Inn.class);
        useCase = new ViewRecruitUseCase(inn);
    }

    @Test
    void testExecute_returnsRecruitList() {
        HeroState hero1 = new HeroState();
        hero1.setName("Alice");
        hero1.setSpecialization("MAGE");
        hero1.setCurHp(10);
        hero1.setMaxHp(10);
        hero1.setCurMana(5);
        hero1.setMaxMana(5);
        hero1.setMageLvl(1);

        HeroState hero2 = new HeroState();
        hero2.setName("Bob");
        hero2.setSpecialization("WARRIOR");
        hero2.setCurHp(12);
        hero2.setMaxHp(12);
        hero2.setCurMana(3);
        hero2.setMaxMana(3);
        hero2.setWarriorLvl(2);

        Map<String, HeroState> recruits = new HashMap<>();
        recruits.put("hero1", hero1);
        recruits.put("hero2", hero2);

        when(inn.viewRecruits()).thenReturn(recruits);

        List<RecruitDTO> result = useCase.execute();

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(r -> r.getName().equals("Alice") && r.getSpecialization().equals("MAGE")));
        assertTrue(result.stream().anyMatch(r -> r.getName().equals("Bob") && r.getSpecialization().equals("WARRIOR")));

        verify(inn, times(1)).viewRecruits();
    }

    @Test
    void testExecute_emptyRecruits_returnsEmptyList() {
        when(inn.viewRecruits()).thenReturn(Collections.emptyMap());

        List<RecruitDTO> result = useCase.execute();

        assertTrue(result.isEmpty());
        verify(inn, times(1)).viewRecruits();
    }

    @Test
    void testExecute_innThrowsException_returnsEmptyList() {
        when(inn.viewRecruits()).thenThrow(new RuntimeException("DB error"));

        List<RecruitDTO> result = useCase.execute();

        assertTrue(result.isEmpty());
        verify(inn, times(1)).viewRecruits();
    }
}