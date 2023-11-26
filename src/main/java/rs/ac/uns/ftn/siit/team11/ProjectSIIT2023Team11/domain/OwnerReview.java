package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@DiscriminatorValue("owner_review")
public class OwnerReview extends Review{
    @ManyToOne(cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    private User owner;
}
