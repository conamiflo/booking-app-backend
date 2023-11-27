package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.ReservationStatus;

import java.time.LocalDate;
@Data
@AllArgsConstructor
public class GuestReservationDTO {
    private Long id;
    private String accommodation;
    private LocalDate startDate;
    private LocalDate endDate;
    private int numberOfGuests;
    private ReservationStatus status;
    private Double price;
}
