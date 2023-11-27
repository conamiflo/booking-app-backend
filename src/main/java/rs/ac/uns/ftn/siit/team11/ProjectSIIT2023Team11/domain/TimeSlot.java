package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class TimeSlot {
    private LocalDate startDate;
    private LocalDate endDate;

    public boolean contains(LocalDate date){
        return !date.isBefore(startDate) && !date.isAfter(endDate);
    }
    public boolean overlapsWith(TimeSlot timeSlot){
        return (startDate.isBefore(timeSlot.endDate) && endDate.isAfter(timeSlot.startDate)) ||
                (timeSlot.startDate.isBefore(endDate) && timeSlot.endDate.isAfter(startDate));
    }
}
