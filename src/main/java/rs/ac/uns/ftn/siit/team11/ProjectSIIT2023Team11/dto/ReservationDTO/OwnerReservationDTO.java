package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.ReservationStatus;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class OwnerReservationDTO {
    private Long id;
    private Long accommodation;
    private String guest;
    private String startDate;
    private String endDate;
    private int numberOfGuests;
    private ReservationStatus status;
    private Double price;
    private int cancelledReservations;
}
