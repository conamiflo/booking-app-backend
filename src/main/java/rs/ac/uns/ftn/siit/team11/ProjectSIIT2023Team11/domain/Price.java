package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.PriceType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="prices")
@Entity
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private TimeSlot timeSlot;
    private Double price;
    private PriceType type;
}
