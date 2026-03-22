package BattleService.controller;

import BattleService.application.BattleApplicationService;
import BattleService.dto.ActionRequest;
import BattleService.dto.BattleStateResponse;
import BattleService.dto.StartBattleRequest;
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