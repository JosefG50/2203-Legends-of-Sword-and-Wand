package com.github.BattleService.dto;

public class RespondInvitationRequest {

    private String invitationId;
    private String response;

    public RespondInvitationRequest() {
    }

    public RespondInvitationRequest(String invitationId, String response) {
        this.invitationId = invitationId;
        this.response = response;
    }

    public String getInvitationId() {
        return invitationId;
    }

    public void setInvitationId(String invitationId) {
        this.invitationId = invitationId;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public boolean isValid() {
        return invitationId != null && !invitationId.isBlank()
                && response != null && !response.isBlank();
    }

    public String getNormalizedResponse() {
        return response == null ? null : response.trim().toLowerCase();
    }
}