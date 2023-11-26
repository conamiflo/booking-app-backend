package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Owner;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Reservation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.GuestReservationDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.OwnerReservationDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationForShowDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.GuestReservationMapper;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.OwnerReservationMapper;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.ReservationMapper;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IAccommodationService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IReservationService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IUserService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.ReservationStatus;

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

    @GetMapping(value = "/guest/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GuestReservationDTO>> getReservationsByUserEmail(@PathVariable("email") String email) {
        Collection<Reservation> reservations = reservationService.findAllByGuestEmail(email);
        return new ResponseEntity<>(reservations.stream()
                .map(GuestReservationMapper::mapToGuestReservationDTO)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(value = "/owner/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<OwnerReservationDTO>> getReservationsByOwnerEmail(@PathVariable("email") String email) {
        Collection<Reservation> reservations = reservationService.findAllByAccommodationOwnerEmail(email);
        return new ResponseEntity<>(reservations.stream()
                .map(OwnerReservationMapper::mapToOwnerReservationDTO)
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

    @GetMapping(value = "/owner/{name}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<OwnerReservationDTO>> getOwnerReservationsByAccommodationName(@PathVariable("accommodationName") String accommodationName) {
        Collection<Reservation> reservations = reservationService.findAllByAccommodationName(accommodationName);
        return new ResponseEntity<>(reservations.stream()
                .map(OwnerReservationMapper::mapToOwnerReservationDTO)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(value = "/guest/{name}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GuestReservationDTO>> getGuestReservationsByAccommodationName(@PathVariable("accommodationName") String accommodationName) {
        Collection<Reservation> reservations = reservationService.findAllByAccommodationName(accommodationName);
        return new ResponseEntity<>(reservations.stream()
                .map(GuestReservationMapper::mapToGuestReservationDTO)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(value = "/guest/filter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GuestReservationDTO>> filterGuestReservations(
            @RequestParam("status") ReservationStatus status,
            @RequestParam("email") String email) {
        Collection<Reservation> reservations = reservationService.findByStatusAndGuestEmail(status, email);
        return new ResponseEntity<>(reservations.stream()
                .map(GuestReservationMapper::mapToGuestReservationDTO)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(value = "/owner/filter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<OwnerReservationDTO>> filterOwnerReservations(
            @RequestParam("status") ReservationStatus status,
            @RequestParam("email") String email) {
        Collection<Reservation> reservations = reservationService.findByStatusAndGuestEmail(status, email);
        return new ResponseEntity<>(reservations.stream()
                .map(OwnerReservationMapper::mapToOwnerReservationDTO)
                .collect(Collectors.toList()), HttpStatus.OK);
    }


    @DeleteMapping(value = "/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("reservationId") Long reservationId) {
        reservationService.deleteById(reservationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}