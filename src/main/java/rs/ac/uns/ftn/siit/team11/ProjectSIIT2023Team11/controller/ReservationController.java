package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.controller;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO.GuestReservationDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO.OwnerReservationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Reservation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO.ReservationDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO.ReservationForShowDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.GuestReservationMapper;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.OwnerReservationMapper;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.ReservationMapper;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IAccommodationService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IReservationService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IUserService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.ReservationStatus;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
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

    @GetMapping(value = "/guest/filter",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GuestReservationDTO>> filterGuestReservations(
            @RequestParam(value ="status",required = false) ReservationStatus status,
            @RequestParam("email") String email) {
        Collection<Reservation> reservations = reservationService.findByStatusAndGuestEmail(status, email);
        return new ResponseEntity<>(reservations.stream()
                .map(GuestReservationMapper::mapToGuestReservationDTO)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(value = "/owner/filter",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<OwnerReservationDTO>> filterOwnerReservations(
            @RequestParam(value ="status",required = false) ReservationStatus status,
            @RequestParam("email") String email) {
        Collection<Reservation> reservations = reservationService.findByStatusAndOwnerEmail(status, email);
        return new ResponseEntity<>(reservations.stream()
                .map(OwnerReservationMapper::mapToOwnerReservationDTO)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(value = "/guest/search",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<OwnerReservationDTO>> searchGuestReservations(
            @RequestParam(value ="startDate",required = false) LocalDate startDate,
            @RequestParam(value ="endDate",required = false) LocalDate endDate,
            @RequestParam(value ="accommodationName",required = false) String accommodationName,
            @RequestParam("email") String email){
        Collection<Reservation> reservations = reservationService.searchOwnerReservations(startDate,endDate,accommodationName, email);
        return new ResponseEntity<>(reservations.stream()
                .map(OwnerReservationMapper::mapToOwnerReservationDTO)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(value = "/owner/search",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<GuestReservationDTO>> searchOwnerReservations(
            @RequestParam(value ="startDate",required = false) LocalDate startDate,
            @RequestParam(value ="endDate",required = false) LocalDate endDate,
            @RequestParam(value ="accommodationName",required = false) String accommodationName,
            @RequestParam("email") String email){
        Collection<Reservation> reservations = reservationService.searchGuestReservations(startDate,endDate,accommodationName, email);
        return new ResponseEntity<>(reservations.stream()
                .map(GuestReservationMapper::mapToGuestReservationDTO)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("reservationId") Long reservationId) {
        reservationService.deleteById(reservationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}