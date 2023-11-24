package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;

public class Notification {

    private Long id;
    private User receiver;
    private String type;
    private String message;

    public Notification() {
    }

    public Notification(Long id, User receiver, String type, String message) {
        this.id = id;
        this.receiver = receiver;
        this.type = type;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
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
