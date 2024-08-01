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
    private Long startEpochTime;
    private Long endEpochTime;


    public boolean contains(Long epochTime) {
        return epochTime >= startEpochTime && epochTime <= endEpochTime;
    }

    public boolean overlapsWith(TimeSlot timeSlot) {
        return (startEpochTime < timeSlot.endEpochTime && endEpochTime > timeSlot.startEpochTime) ||
                (timeSlot.startEpochTime < endEpochTime && timeSlot.endEpochTime > startEpochTime);
    }
}
