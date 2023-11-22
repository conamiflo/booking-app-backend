package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;

import java.time.LocalDate;
@Getter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Review {

    @Id
    private int id;
    private String guest;
    private String description;
    private int rating;
    private LocalDate date;
    private boolean reported;

    public Review(int id, String guest, String description, int rating, LocalDate date,boolean reported
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
    public void setId(int id) {
        this.id = id;
    }

    public void setGuest(String guest) {
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
}
