package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;

import java.util.List;

public class Guest {
    private Long guestId;
    private String email;

    public Guest(Long guestId, String email) {
        this.guestId = guestId;
        this.email = email;
    }

    public Long getGuestId() {
        return guestId;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
