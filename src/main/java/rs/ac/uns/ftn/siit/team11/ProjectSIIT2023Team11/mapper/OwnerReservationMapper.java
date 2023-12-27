package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO.OwnerReservationDTO;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Reservation;

import java.time.format.DateTimeFormatter;

@Component
public class OwnerReservationMapper {
    public static OwnerReservationDTO mapToOwnerReservationDTO(Reservation reservation) {
        return new OwnerReservationDTO(
                reservation.getId(),
                reservation.getAccommodation().getId(),
                reservation.getGuest().getEmail(),
                reservation.getStartDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                reservation.getEndDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                reservation.getNumberOfGuests(),
                reservation.getStatus(),
                reservation.getPrice(),
                0
        );
    }
}
