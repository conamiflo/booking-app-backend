package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto;

import java.time.LocalDate;

public class AvailabilityForShowDTO {

    LocalDate from;
    LocalDate to;

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }
}
