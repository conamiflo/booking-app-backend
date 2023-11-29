package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Reservation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.GuestReservationDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationForShowDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.GuestReservationMapper;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.ReservationMapper;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IAccommodationService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IReservationService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IUserService;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    IReservationService reservationService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IAccommodationService accommodationService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ReservationForShowDTO>> getReservations() {
        Collection<Reservation> reservations = reservationService.findAll();
        return new ResponseEntity<>(reservations.stream()
                .map(ReservationMapper::mapToReservationDTO)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(value = "/{guestEmail}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GuestReservationDTO>> getReservationsByUserEmail(@PathVariable("guestEmail") String email) {
        Collection<Reservation> reservations = reservationService.findReservationsByGuestEmail(email);
        return new ResponseEntity<>(reservations.stream()
                .map(GuestReservationMapper::mapToGuestReservationDTO)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(value = "/{ownerEmail}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GuestReservationDTO>> getReservationsByOwnerEmail(@PathVariable("ownerEmail") String email) {
        Collection<Reservation> reservations = reservationService.findReservationsByAccommodation_Owner_Email(email);
        return new ResponseEntity<>(reservations.stream()
                .map(GuestReservationMapper::mapToGuestReservationDTO)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationDTO reservation) {
        Optional<Reservation> newReservation = Optional.ofNullable(reservationService.save(ReservationMapper.mapToReservation(reservation, userService, accommodationService)));
        return new ResponseEntity<>(newReservation.get(), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{reservationId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reservation> updateReservation(@PathVariable("reservationId") Long reservationId) {
        Optional<Reservation> existingReservation = reservationService.findById(reservationId);
        if (existingReservation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Reservation updatedReservation = reservationService.save(existingReservation.get());
        return new ResponseEntity<>(updatedReservation, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("reservationId") Long reservationId) {
        reservationService.deleteById(reservationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}