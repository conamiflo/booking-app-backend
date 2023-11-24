package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;

import java.time.LocalDate;

public class OwnerReview extends Review{

    private User owner;

    public OwnerReview(Long id, User guest, String description, int rating, LocalDate date, boolean reported, User owner) {
        super(id, guest, description, rating, date, reported);
        this.owner = owner;
    }

    public OwnerReview(User owner) {
        this.owner = owner;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
