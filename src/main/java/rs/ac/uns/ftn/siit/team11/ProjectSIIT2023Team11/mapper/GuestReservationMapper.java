package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO.GuestReservationDTO;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Reservation;

import java.time.format.DateTimeFormatter;

@Component
public class GuestReservationMapper {
    public static GuestReservationDTO mapToGuestReservationDTO(Reservation reservation) {
        return new GuestReservationDTO(
                reservation.getId(),
                reservation.getAccommodation().getName(),
                reservation.getStartDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                reservation.getEndDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                reservation.getNumberOfGuests(),
                reservation.getStatus(),
                reservation.getPrice(),
                reservation.getAccommodation().getPhotos()
        );
    }
}
