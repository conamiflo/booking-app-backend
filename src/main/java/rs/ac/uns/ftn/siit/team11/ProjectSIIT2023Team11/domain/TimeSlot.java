package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;

import java.time.LocalDate;
@Embeddable
public class TimeSlot {
    private LocalDate startDate;
    private LocalDate endDate;

}
