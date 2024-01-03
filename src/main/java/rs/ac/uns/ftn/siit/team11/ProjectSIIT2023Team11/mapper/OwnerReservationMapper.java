package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO.OwnerReservationDTO;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Reservation;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class OwnerReservationMapper {
    public static OwnerReservationDTO mapToOwnerReservationDTO(Reservation reservation) {
        return new OwnerReservationDTO(
                reservation.getId(),
                reservation.getAccommodation().getId(),
                reservation.getGuest().getEmail(),
                Instant.ofEpochSecond(reservation.getStartDate()).atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                Instant.ofEpochSecond(reservation.getEndDate()).atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                reservation.getNumberOfGuests(),
                reservation.getStatus(),
                reservation.getPrice(),
                0
        );
    }
}
