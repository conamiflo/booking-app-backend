package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.ReservationStatus;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class ReservationForShowDTO {

    private Long id;
    private String accommodation;
    private String guest;
    private String startDate;
    private String endDate;
    private int numberOfGuests;
    private ReservationStatus status;
    private Double price;
}
