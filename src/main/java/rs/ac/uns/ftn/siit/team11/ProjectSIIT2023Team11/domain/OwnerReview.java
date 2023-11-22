package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;

import java.time.LocalDate;

public class OwnerReview extends Review{

    private String owner;

    public OwnerReview(int id, String guest, String description, int rating, LocalDate date, String owner) {
        super(id, guest, description, rating, date);
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
