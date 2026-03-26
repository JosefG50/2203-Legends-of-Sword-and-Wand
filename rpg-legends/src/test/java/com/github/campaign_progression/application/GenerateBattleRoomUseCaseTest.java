package com.github.campaign_progression.application;

import com.github.campaign_progression.application.dto.BattleRoomResponseDTO;
import com.github.campaign_progression.domain.Enemy;
import com.github.campaign_progression.domain.EnemyFactory;
import com.github.campaign_progression.domain.PartyService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GenerateBattleRoomUseCaseTest {

    @Test
    void execute_returnsEnemiesMappedToDTO() {
        // Mock party service
        PartyService partyService = mock(PartyService.class);
        when(partyService.getTotalLevels()).thenReturn(5);

        // Mock enemy factory
        EnemyFactory enemyFactory = mock(EnemyFactory.class);
        Enemy e1 = mock(Enemy.class);
        when(e1.getLevel()).thenReturn(3);
        when(e1.getHp()).thenReturn(50);
        when(e1.getAttack()).thenReturn(10);
        when(e1.getDefense()).thenReturn(5);

        Enemy e2 = mock(Enemy.class);
        when(e2.getLevel()).thenReturn(2);
        when(e2.getHp()).thenReturn(30);
        when(e2.getAttack()).thenReturn(7);
        when(e2.getDefense()).thenReturn(3);

        when(enemyFactory.createEnemies(5)).thenReturn(List.of(e1, e2));

        GenerateBattleRoomUseCase useCase = new GenerateBattleRoomUseCase(partyService, enemyFactory);

        BattleRoomResponseDTO result = useCase.execute();

        assertNotNull(result);
        assertEquals(2, result.getEnemies().size());

        BattleRoomResponseDTO.EnemyDTO dto1 = result.getEnemies().get(0);
        assertEquals(3, dto1.getLevel());
        assertEquals(50, dto1.getHp());
        assertEquals(10, dto1.getAttack());
        assertEquals(5, dto1.getDefense());

        BattleRoomResponseDTO.EnemyDTO dto2 = result.getEnemies().get(1);
        assertEquals(2, dto2.getLevel());
        assertEquals(30, dto2.getHp());
        assertEquals(7, dto2.getAttack());
        assertEquals(3, dto2.getDefense());
    }

    @Test
    void execute_emptyEnemyList_returnsEmptyDTO() {
        PartyService partyService = mock(PartyService.class);
        when(partyService.getTotalLevels()).thenReturn(0);

        EnemyFactory enemyFactory = mock(EnemyFactory.class);
        when(enemyFactory.createEnemies(0)).thenReturn(List.of());

        GenerateBattleRoomUseCase useCase = new GenerateBattleRoomUseCase(partyService, enemyFactory);

        BattleRoomResponseDTO result = useCase.execute();

        assertNotNull(result);
        assertTrue(result.getEnemies().isEmpty());
    }
}