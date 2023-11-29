package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("accommodation_review")
public class AccommodationReview extends Review{
    @ManyToOne(fetch = FetchType.LAZY)
    private Accommodation accommondation;

    public AccommodationReview(Long id, User user, String description, int rating, LocalDate date, boolean reported, Accommodation accommodation) {
        super(id, user, description, rating, date, reported);
        this.accommondation = accommodation;
    }
}
