package BattleService.controller;

import BattleService.application.BattleInvitationService;
import BattleService.dto.CreateInvitationRequest;
import BattleService.dto.InvitationResponse;
import BattleService.dto.RespondInvitationRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invitation")
@CrossOrigin(origins = "*")
public class BattleInvitationController {

    private final BattleInvitationService battleInvitationService;

    public BattleInvitationController(BattleInvitationService battleInvitationService) {
        this.battleInvitationService = battleInvitationService;
    }

    @PostMapping("/create")
    public InvitationResponse createInvitation(@RequestBody CreateInvitationRequest request) {
        return battleInvitationService.createInvitation(request);
    }

    @PostMapping("/respond")
    public InvitationResponse respondToInvitation(@RequestBody RespondInvitationRequest request) {
        return battleInvitationService.respondToInvitation(request);
    }

    @GetMapping("/{invitationId}")
    public InvitationResponse getInvitationById(@PathVariable String invitationId) {
        return battleInvitationService.getInvitationById(invitationId);
    }
}