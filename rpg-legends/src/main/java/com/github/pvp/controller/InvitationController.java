package com.github.pvp.controller;

import com.github.pvp.application.InvitationService;
import com.github.pvp.dto.CreateInvitationRequest;
import com.github.pvp.dto.InvitationResponse;
import com.github.pvp.dto.RespondInvitationRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invitation")
@CrossOrigin(origins = "*")
public class InvitationController {

    private final InvitationService invitationService;

    public InvitationController(InvitationService invitationService) {
        this.invitationService = invitationService;
    }

    @PostMapping("/create")
    public InvitationResponse createInvitation(@RequestBody CreateInvitationRequest request) {
        return invitationService.createInvitation(request);
    }

    @PostMapping("/respond")
    public InvitationResponse respondToInvitation(@RequestBody RespondInvitationRequest request) {
        return invitationService.respondToInvitation(request);
    }

    @GetMapping("/{invitationId}")
    public InvitationResponse getInvitationById(@PathVariable String invitationId) {
        return invitationService.getInvitationById(invitationId);
    }
}
