package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto;

public class NotificationForSendDTO {
    private String receiver;
    private String type;
    private String message;

    public NotificationForSendDTO(String receiver, String type, String message) {
        this.receiver = receiver;
        this.type = type;
        this.message = message;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
