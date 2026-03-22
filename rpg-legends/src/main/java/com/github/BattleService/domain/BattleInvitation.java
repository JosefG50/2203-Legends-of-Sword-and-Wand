package BattleService.domain;

public class BattleInvitation {

    private String invitationId;
    private String senderName;
    private String receiverName;
    private String status;

    public BattleInvitation(String invitationId, String senderName, String receiverName, String status) {
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
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}