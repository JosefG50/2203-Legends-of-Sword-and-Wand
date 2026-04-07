package com.github.pvp.domain;

public class BattleInvitation {

    private String invitationId;
    private String senderName;
    private String receiverName;
    private InvitationStatus status;

    public BattleInvitation(String invitationId, String senderName, String receiverName, InvitationStatus status) {
        this.invitationId = invitationId;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.status = status;
    }

    public String getInvitationId() {
        return invitationId;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getStatus() {
        return status.name();
    }

    public void setStatus(InvitationStatus status) {
        this.status = status;
    }

    public boolean isPending() {
        return status == InvitationStatus.PENDING;
    }
}
