package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
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

    public AccommodationReview(Long id, User user, String description, int rating, Long date, boolean reported, boolean approved, Accommodation accommodation) {
        super(id, user, description, rating, date, reported, approved);
        this.accommondation = accommodation;
    }
}
