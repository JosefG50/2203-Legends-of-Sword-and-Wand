package BattleService.dto;

public class ActionRequest {

    private String actionType;
    private String targetName;

    public ActionRequest() {
    }

    public ActionRequest(String actionType, String targetName) {
        this.actionType = actionType;
        this.targetName = targetName;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public boolean isValid() {
        return actionType != null && !actionType.isBlank()
                && targetName != null && !targetName.isBlank();
    }

    public String getNormalizedActionType() {
        return actionType == null ? null : actionType.trim().toLowerCase();
    }

    public String getNormalizedTargetName() {
        return targetName == null ? null : targetName.trim();
    }
}