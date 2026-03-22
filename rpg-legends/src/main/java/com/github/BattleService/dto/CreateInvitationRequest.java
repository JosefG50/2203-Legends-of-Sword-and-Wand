package com.github.BattleService.dto;

public class CreateInvitationRequest {

    private String senderName;
    private String receiverName;

    public CreateInvitationRequest() {
    }

    public CreateInvitationRequest(String senderName, String receiverName) {
        this.senderName = senderName;
        this.receiverName = receiverName;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public boolean isValid() {
        return senderName != null && !senderName.isBlank()
                && receiverName != null && !receiverName.isBlank();
    }
}