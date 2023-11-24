package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;

import java.time.LocalDate;
//@Getter
//@Entity
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Review {

    //@Id
    private Long id;
    private User guest;
    private String description;
    private int rating;
    private LocalDate date;
    private boolean reported;

    public Review(Long id, User guest, String description, int rating, LocalDate date, boolean reported
    ) {
        this.id = id;
        this.guest = guest;
        this.description = description;
        this.rating = rating;
        this.date = date;
        this.reported = reported;
    }

    public Review() {

    }
    public void setId(Long id) {
        this.id = id;
    }

    public void setGuest(User guest) {
        this.guest = guest;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setReported(boolean reported) {
        this.reported = reported;
    }

    public Long getId() {
        return id;
    }

    public User getGuest() {
        return guest;
    }

    public String getDescription() {
        return description;
    }

    public int getRating() {
        return rating;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isReported() {
        return reported;
    }
}
