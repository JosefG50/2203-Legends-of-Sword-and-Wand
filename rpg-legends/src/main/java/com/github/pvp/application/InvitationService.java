package com.github.pvp.application;

import com.github.pvp.domain.BattleInvitation;
import com.github.pvp.domain.InvitationStatus;
import com.github.pvp.dto.CreateInvitationRequest;
import com.github.pvp.dto.InvitationResponse;
import com.github.pvp.dto.RespondInvitationRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class InvitationService {

    private final Map<String, BattleInvitation> invitations = new HashMap<>();

    public InvitationResponse createInvitation(CreateInvitationRequest request) {
        if (request == null || !request.isValid()) {
            return new InvitationResponse(
                    null,
                    null,
                    null,
                    "INVALID",
                    "Invalid invitation request"
            );
        }

        String invitationId = UUID.randomUUID().toString();

        BattleInvitation invitation = new BattleInvitation(
                invitationId,
                request.getSenderName().trim(),
                request.getReceiverName().trim(),
                InvitationStatus.PENDING
        );

        invitations.put(invitationId, invitation);

        return new InvitationResponse(
                invitation.getInvitationId(),
                invitation.getSenderName(),
                invitation.getReceiverName(),
                invitation.getStatus(),
                "Invitation created successfully"
        );
    }

    public InvitationResponse respondToInvitation(RespondInvitationRequest request) {
        if (request == null || !request.isValid()) {
            return new InvitationResponse(
                    null,
                    null,
                    null,
                    "INVALID",
                    "Invalid response request"
            );
        }

        BattleInvitation invitation = invitations.get(request.getInvitationId());

        if (invitation == null) {
            return new InvitationResponse(
                    request.getInvitationId(),
                    null,
                    null,
                    "NOT_FOUND",
                    "Invitation not found"
            );
        }

        if (!invitation.isPending()) {
            return invitationSnapshotResponse(
                    invitation,
                    "Invitation has already been responded to"
            );
        }

        String response = request.getNormalizedResponse();

        if (response.equals("accept")) {
            invitation.setStatus(InvitationStatus.ACCEPTED);
            return invitationSnapshotResponse(
                    invitation,
                    "Invitation accepted. Battle can now be started."
            );
        }

        if (response.equals("reject")) {
            invitation.setStatus(InvitationStatus.REJECTED);
            return invitationSnapshotResponse(
                    invitation,
                    "Invitation rejected"
            );
        }

        return invitationSnapshotResponse(
                invitation,
                "Response must be either accept or reject"
        );
    }

    public InvitationResponse getInvitationById(String invitationId) {
        if (invitationId == null || invitationId.isBlank()) {
            return new InvitationResponse(
                    null,
                    null,
                    null,
                    "INVALID",
                    "Invitation id is required"
            );
        }

        BattleInvitation invitation = invitations.get(invitationId);

        if (invitation == null) {
            return new InvitationResponse(
                    invitationId,
                    null,
                    null,
                    "NOT_FOUND",
                    "Invitation not found"
            );
        }

        return new InvitationResponse(
                invitation.getInvitationId(),
                invitation.getSenderName(),
                invitation.getReceiverName(),
                invitation.getStatus(),
                "Invitation retrieved successfully"
        );
    }

    private static InvitationResponse invitationSnapshotResponse(BattleInvitation invitation, String message) {
        return new InvitationResponse(
                invitation.getInvitationId(),
                invitation.getSenderName(),
                invitation.getReceiverName(),
                invitation.getStatus(),
                message
        );
    }
}
