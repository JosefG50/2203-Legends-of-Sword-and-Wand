package com.github.BattleService.controller;

import com.github.BattleService.application.BattleApplicationService;
import com.github.BattleService.dto.ActionRequest;
import com.github.BattleService.dto.BattleStateResponse;
import com.github.BattleService.dto.StartBattleRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/battle")
@CrossOrigin(origins = "*")
public class BattleController {

    private final BattleApplicationService battleService;

    public BattleController(BattleApplicationService battleService) {
        this.battleService = battleService;
    }

    @PostMapping("/start")
    public BattleStateResponse startBattle(@RequestBody StartBattleRequest request) {
        return battleService.startBattle(request);
    }

    @PostMapping("/action")
    public BattleStateResponse submitAction(@RequestBody ActionRequest request) {
        return battleService.submitAction(request);
    }

    @GetMapping("/state")
    public BattleStateResponse getBattleState() {
        return battleService.getBattleState();
    }
}