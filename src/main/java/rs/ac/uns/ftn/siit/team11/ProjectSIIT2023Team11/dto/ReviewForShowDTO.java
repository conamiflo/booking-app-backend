package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.User;

import java.time.LocalDate;

public class ReviewForShowDTO {
    private User guest;
    private String description;
    private int rating;
    private LocalDate date;

    public ReviewForShowDTO(User guest, String description, int rating, LocalDate date) {
        this.guest = guest;
        this.description = description;
        this.rating = rating;
        this.date = date;
    }

    public User getGuest() {
        return guest;
    }

    public void setGuest(User guest) {
        this.guest = guest;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
