package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="availabilities")
@Entity
public class Availability {

    @Id
    private Long id;
    @Embedded
    private TimeSlot timeSlot;
}

