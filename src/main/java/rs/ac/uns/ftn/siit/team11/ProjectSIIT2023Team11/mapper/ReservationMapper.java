package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper;

import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Guest;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Reservation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO.ReservationDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO.ReservationForShowDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IAccommodationService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IUserService;

@Component
public class ReservationMapper {
    public static ReservationForShowDTO mapToReservationDTO(Reservation reservation) {
        return new ReservationForShowDTO(
                reservation.getId(),
                reservation.getAccommodation().getName(),
                reservation.getGuest().getEmail(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getNumberOfGuests(),
                reservation.getStatus(),
                reservation.getPrice()
        );
    }

    public static Reservation mapToReservation(ReservationDTO reservationDTO, IUserService userService, IAccommodationService accommodationService){
        return new Reservation(
                reservationDTO.getId(),
                accommodationService.findById(reservationDTO.getAccommodation()).get(),
                (Guest)userService.findById(reservationDTO.getGuest()).get(),
                reservationDTO.getStartDate(),
                reservationDTO.getEndDate(),
                reservationDTO.getNumberOfGuests(),
                reservationDTO.getStatus(),
                reservationDTO.getPrice()
        );
    }

}
