package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO.GuestReservationDTO;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Reservation;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class GuestReservationMapper {
    public static GuestReservationDTO mapToGuestReservationDTO(Reservation reservation) {
        return new GuestReservationDTO(
                reservation.getId(),
                reservation.getAccommodation().getId(),
                Instant.ofEpochSecond(reservation.getStartDate()).atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                Instant.ofEpochSecond(reservation.getEndDate()).atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                reservation.getNumberOfGuests(),
                reservation.getStatus(),
                reservation.getPrice(),
                reservation.getAccommodation().getPhotos()
        );
    }
}
