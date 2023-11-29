package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto;

import jakarta.persistence.Embedded;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.TimeSlot;

public class InputPriceDTO {
    @Embedded
    private TimeSlot timeSlot;
    private Double price;

    public InputPriceDTO(TimeSlot timeSlot, Double price) {
        this.timeSlot = timeSlot;
        this.price = price;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
