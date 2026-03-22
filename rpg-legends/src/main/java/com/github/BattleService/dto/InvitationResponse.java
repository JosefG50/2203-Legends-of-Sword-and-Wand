package BattleService.dto;

public class InvitationResponse {

    private String invitationId;
    private String senderName;
    private String receiverName;
    private String status;
    private String message;

    public InvitationResponse() {
    }

    public InvitationResponse(String invitationId, String senderName, String receiverName, String status, String message) {
        this.invitationId = invitationId;
        this.senderName = senderName;
        this.receiverName = receiverName;
        this.status = status;
        this.message = message;
    }

    public String getInvitationId() {
        return invitationId;
    }

    public void setInvitationId(String invitationId) {
        this.invitationId = invitationId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}