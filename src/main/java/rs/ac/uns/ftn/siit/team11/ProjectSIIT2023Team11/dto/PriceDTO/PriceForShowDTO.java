package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.PriceDTO;

import jakarta.persistence.Embedded;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.TimeSlot;

public class PriceForShowDTO {
    private Long id;
    @Embedded
    private TimeSlot timeSlot;
    private Double price;

    public PriceForShowDTO(Long id, TimeSlot timeSlot, Double price) {
        this.id = id;
        this.timeSlot = timeSlot;
        this.price = price;
    }

    public PriceForShowDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
