package BattleService.application;

import BattleService.dto.CreateInvitationRequest;
import BattleService.dto.InvitationResponse;
import BattleService.dto.RespondInvitationRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BattleInvitationServiceTest {

    private BattleInvitationService service;

    @BeforeEach
    void setUp() {
        service = new BattleInvitationService();
    }

    private CreateInvitationRequest createRequest(String sender, String receiver) {
        CreateInvitationRequest request = new CreateInvitationRequest();
        request.setSenderName(sender);
        request.setReceiverName(receiver);
        return request;
    }

    private RespondInvitationRequest respondRequest(String invitationId, String response) {
        RespondInvitationRequest request = new RespondInvitationRequest();
        request.setInvitationId(invitationId);
        request.setResponse(response);
        return request;
    }

    @Test
    void createInvitation_shouldReturnPending() {
        InvitationResponse response = service.createInvitation(createRequest("Hero", "Enemy"));

        assertNotNull(response.getInvitationId());
        assertEquals("Hero", response.getSenderName());
        assertEquals("Enemy", response.getReceiverName());
        assertEquals("PENDING", response.getStatus());
    }

    @Test
    void acceptInvitation_shouldSetAccepted() {
        InvitationResponse created = service.createInvitation(createRequest("Hero", "Enemy"));

        InvitationResponse response = service.respondToInvitation(
                respondRequest(created.getInvitationId(), "accept")
        );

        assertEquals("ACCEPTED", response.getStatus());
        assertEquals("Invitation accepted. Battle can now be started.", response.getMessage());
    }

    @Test
    void rejectInvitation_shouldSetRejected() {
        InvitationResponse created = service.createInvitation(createRequest("Hero", "Enemy"));

        InvitationResponse response = service.respondToInvitation(
                respondRequest(created.getInvitationId(), "reject")
        );

        assertEquals("REJECTED", response.getStatus());
        assertEquals("Invitation rejected", response.getMessage());
    }

    @Test
    void getInvitationById_shouldReturnInvitation() {
        InvitationResponse created = service.createInvitation(createRequest("Hero", "Enemy"));

        InvitationResponse response = service.getInvitationById(created.getInvitationId());

        assertEquals(created.getInvitationId(), response.getInvitationId());
        assertEquals("Hero", response.getSenderName());
        assertEquals("Enemy", response.getReceiverName());
        assertEquals("PENDING", response.getStatus());
    }

    @Test
    void invalidInvitationId_shouldReturnNotFound() {
        InvitationResponse response = service.getInvitationById("bad-id");

        assertEquals("NOT_FOUND", response.getStatus());
        assertEquals("Invitation not found", response.getMessage());
    }
}