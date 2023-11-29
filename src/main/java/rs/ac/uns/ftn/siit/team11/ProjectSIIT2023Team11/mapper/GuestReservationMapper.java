package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO.GuestReservationDTO;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Reservation;

@Component
public class GuestReservationMapper {
    public static GuestReservationDTO mapToGuestReservationDTO(Reservation reservation) {
        return new GuestReservationDTO(
                reservation.getId(),
                reservation.getAccommodation().getName(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getNumberOfGuests(),
                reservation.getStatus(),
                reservation.getPrice()
        );
    }
}
