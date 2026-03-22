package com.github.BattleService.application;

import com.github.BattleService.application.BattleApplicationService;
import com.github.BattleService.dto.ActionRequest;
import com.github.BattleService.dto.BattleStateResponse;
import com.github.BattleService.dto.StartBattleRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BattleApplicationServiceTest {

    private BattleApplicationService service;

    @BeforeEach
    void setUp() {
        service = new BattleApplicationService();
    }

    private StartBattleRequest createStandardBattleRequest() {
        StartBattleRequest request = new StartBattleRequest();

        request.setHeroName("Hero");
        request.setHeroHp(100);
        request.setHeroMana(50);
        request.setHeroShield(10);
        request.setHeroAttack(20);
        request.setHeroDefense(5);

        request.setEnemyName("Enemy");
        request.setEnemyHp(80);
        request.setEnemyMana(30);
        request.setEnemyShield(0);
        request.setEnemyAttack(15);
        request.setEnemyDefense(8);

        return request;
    }

    private ActionRequest action(String type, String target) {
        ActionRequest req = new ActionRequest();
        req.setActionType(type);
        req.setTargetName(target);
        return req;
    }

    @Test
    void startBattle_initializesCorrectly() {
        BattleStateResponse res = service.startBattle(createStandardBattleRequest());

        assertEquals("Hero", res.getCurrentUnitName());
        assertEquals(100, res.getHeroHp());
        assertEquals(50, res.getHeroMana());
        assertEquals(80, res.getEnemyHp());
        assertEquals(30, res.getEnemyMana());
        assertFalse(res.isBattleOver());
        assertEquals("Battle is still ongoing", res.getResult());
    }

    @Test
    void attack_reducesEnemyHp() {
        service.startBattle(createStandardBattleRequest());

        BattleStateResponse res = service.submitAction(action("attack", "Enemy"));

        assertEquals(68, res.getEnemyHp());
        assertEquals(100, res.getHeroHp());
        assertEquals(50, res.getHeroMana());
        assertEquals("Enemy", res.getCurrentUnitName());
        assertFalse(res.isBattleOver());
    }

    @Test
    void wait_onlyChangesTurn() {
        service.startBattle(createStandardBattleRequest());

        BattleStateResponse res = service.submitAction(action("wait", "Hero"));

        assertEquals(100, res.getHeroHp());
        assertEquals(50, res.getHeroMana());
        assertEquals(80, res.getEnemyHp());
        assertEquals(30, res.getEnemyMana());
        assertEquals("Enemy", res.getCurrentUnitName());
        assertFalse(res.isBattleOver());
    }

    @Test
    void cast_consumesMana_andDealsDamage() {
        service.startBattle(createStandardBattleRequest());

        BattleStateResponse res = service.submitAction(action("cast", "Enemy"));

        assertEquals(30, res.getHeroMana());
        assertEquals(55, res.getEnemyHp());
        assertEquals("Enemy", res.getCurrentUnitName());
        assertFalse(res.isBattleOver());
    }

    @Test
    void cast_fails_whenNotEnoughMana() {
        StartBattleRequest req = createStandardBattleRequest();
        req.setHeroMana(10);

        service.startBattle(req);

        BattleStateResponse res = service.submitAction(action("cast", "Enemy"));

        assertEquals(10, res.getHeroMana());
        assertEquals(80, res.getEnemyHp());
        assertEquals("Enemy", res.getCurrentUnitName());
        assertFalse(res.isBattleOver());
    }

    @Test
    void defend_restoresStats() {
        service.startBattle(createStandardBattleRequest());

        service.submitAction(action("cast", "Enemy"));
        BattleStateResponse res = service.submitAction(action("defend", "Enemy"));

        assertEquals(65, res.getEnemyHp());
        assertEquals(30, res.getEnemyMana());
        assertEquals("Hero", res.getCurrentUnitName());
        assertFalse(res.isBattleOver());
    }

    @Test
    void shield_absorbsDamage_first() {
        service.startBattle(createStandardBattleRequest());

        service.submitAction(action("wait", "Hero"));
        BattleStateResponse res = service.submitAction(action("attack", "Hero"));

        assertEquals(100, res.getHeroHp());
        assertEquals(80, res.getEnemyHp());
        assertEquals("Hero", res.getCurrentUnitName());
        assertFalse(res.isBattleOver());
    }

    @Test
    void battle_ends_when_enemyDies() {
        StartBattleRequest req = createStandardBattleRequest();
        req.setEnemyHp(20);

        service.startBattle(req);

        BattleStateResponse res = service.submitAction(action("cast", "Enemy"));

        assertEquals(0, res.getEnemyHp());
        assertTrue(res.isBattleOver());
        assertEquals("Player party wins", res.getResult());
    }

    @Test
    void invalidAction_doesNothing() {
        service.startBattle(createStandardBattleRequest());

        BattleStateResponse res = service.submitAction(action("fly", "Enemy"));

        assertEquals(100, res.getHeroHp());
        assertEquals(50, res.getHeroMana());
        assertEquals(80, res.getEnemyHp());
        assertEquals(30, res.getEnemyMana());
        assertEquals("Hero", res.getCurrentUnitName());
        assertFalse(res.isBattleOver());
        assertEquals("Battle is still ongoing", res.getResult());
    }

    @Test
    void noBattle_returnsSafeResponse() {
        BattleStateResponse res = service.submitAction(action("attack", "Enemy"));

        assertEquals("None", res.getCurrentUnitName());
        assertEquals(0, res.getHeroHp());
        assertEquals(0, res.getHeroMana());
        assertEquals(0, res.getEnemyHp());
        assertEquals(0, res.getEnemyMana());
        assertTrue(res.isBattleOver());
        assertEquals("No active battle", res.getResult());
    }
}