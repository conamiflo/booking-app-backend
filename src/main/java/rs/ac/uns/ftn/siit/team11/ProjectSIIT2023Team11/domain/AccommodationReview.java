package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@DiscriminatorValue("accommodation_review")
public class AccommodationReview extends Review{
    @ManyToOne(cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    private Accommodation accommondation;
}
