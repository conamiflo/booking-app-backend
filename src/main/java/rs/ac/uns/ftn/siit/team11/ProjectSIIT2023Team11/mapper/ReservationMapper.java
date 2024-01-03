package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper;

import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Guest;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Reservation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO.ReservationDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO.ReservationForShowDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IAccommodationService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IUserService;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class ReservationMapper {
    public static ReservationForShowDTO mapToReservationDTO(Reservation reservation) {
        return new ReservationForShowDTO(
                reservation.getId(),
                reservation.getAccommodation().getName(),
                reservation.getGuest().getEmail(),
                Instant.ofEpochSecond(reservation.getStartDate()).atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                Instant.ofEpochSecond(reservation.getEndDate()).atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                reservation.getNumberOfGuests(),
                reservation.getStatus(),
                reservation.getPrice()
        );
    }

    public static Reservation mapToReservation(ReservationDTO reservationDTO, IUserService userService, IAccommodationService accommodationService){

        Reservation reservation = new Reservation();
        reservation.setId(reservationDTO.getId());
        reservation.setAccommodation(accommodationService.findById(reservationDTO.getAccommodation()).get());
        reservation.setGuest((Guest)userService.findById(reservationDTO.getGuest()).get());
        reservation.setStartDate(reservationDTO.getStartDate());
        reservation.setEndDate(reservationDTO.getEndDate());
        reservation.setNumberOfGuests(reservationDTO.getNumberOfGuests());

        return reservation;
    }

}
