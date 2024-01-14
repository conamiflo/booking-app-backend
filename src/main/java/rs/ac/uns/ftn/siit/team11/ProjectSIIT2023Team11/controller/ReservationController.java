package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.User;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Reservation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.GuestReservationMapper;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.OwnerReservationMapper;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.ReservationMapper;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IAccommodationService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IAvailabilityService;
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

    @Autowired
    private IAvailabilityService availabilityService;

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
    public ResponseEntity<ReservationForShowDTO> createReservation(@RequestBody ReservationDTO reservation) {
        Optional<User> user = userService.findById(reservation.getGuest());
        Optional<Accommodation> accommodation = accommodationService.findById(reservation.getAccommodation());
        if(user.isEmpty() || accommodation.isEmpty()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Reservation newReservationEntry = ReservationMapper.mapToReservation(reservation, userService, accommodationService);

        if(!newReservationEntry.isAvailable()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Reservation> newReservation = reservationService.createNewReservation(newReservationEntry);

        return new ResponseEntity<>(ReservationMapper.mapToReservationDTO(newReservation.get()), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{reservationId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationForShowDTO> updateReservation(@PathVariable("reservationId") Long reservationId) {
        Optional<Reservation> existingReservation = reservationService.findById(reservationId);
        if (existingReservation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Reservation updatedReservation = reservationService.save(existingReservation.get());
        return new ResponseEntity<>(ReservationMapper.mapToReservationDTO(updatedReservation), HttpStatus.OK);
    }

    @PutMapping(value = "accept/{reservationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OwnerReservationDTO> acceptReservation(@PathVariable("reservationId") Long reservationId) {
        Optional<Reservation> existingReservation = reservationService.findById(reservationId);
        if (existingReservation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        reservationService.declineWaitingReservations(existingReservation.get().getStartDate(), existingReservation.get().getEndDate(), existingReservation.get().getAccommodation().getId());
        availabilityService.fitAcceptedReservation(existingReservation.get().getStartDate(), existingReservation.get().getEndDate(), existingReservation.get().getAccommodation());

        existingReservation.get().setStatus(ReservationStatus.Accepted);
        Reservation updatedReservation = reservationService.save(existingReservation.get());
        return new ResponseEntity<>(OwnerReservationMapper.mapToOwnerReservationDTO(updatedReservation), HttpStatus.OK);
    }

    @PutMapping(value = "decline/{reservationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OwnerReservationDTO> declineReservation(@PathVariable("reservationId") Long reservationId) {
        Optional<Reservation> existingReservation = reservationService.findById(reservationId);
        if (existingReservation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingReservation.get().setStatus(ReservationStatus.Declined);
        Reservation updatedReservation = reservationService.save(existingReservation.get());
        return new ResponseEntity<>(OwnerReservationMapper.mapToOwnerReservationDTO(updatedReservation), HttpStatus.OK);
    }

    @PutMapping(value = "cancel/{reservationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GuestReservationDTO> cancelReservation(@PathVariable("reservationId") Long reservationId) {
        Optional<Reservation> existingReservation = reservationService.findById(reservationId);
        if (existingReservation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(existingReservation.get().getStatus() == ReservationStatus.Accepted){
            availabilityService.returnCancelledAvailability(existingReservation.get().getStartDate(), existingReservation.get().getEndDate(), existingReservation.get().getAccommodation());
        }
        existingReservation.get().setStatus(ReservationStatus.Cancelled);
        Reservation updatedReservation = reservationService.save(existingReservation.get());
        return new ResponseEntity<>(GuestReservationMapper.mapToGuestReservationDTO(updatedReservation), HttpStatus.OK);
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
            @RequestParam(value ="startDate",required = false) Long startDate,
            @RequestParam(value ="endDate",required = false) Long endDate,
            @RequestParam(value ="accommodationName",required = false) String accommodationName,
            @RequestParam("email") String email){
        Collection<Reservation> reservations = reservationService.searchGuestReservations(startDate,endDate,accommodationName, email);
        return new ResponseEntity<>(reservations.stream()
                .map(OwnerReservationMapper::mapToOwnerReservationDTO)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(value = "/owner/search",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<OwnerReservationDTO>> searchOwnerReservations(
            @RequestParam(value ="startDate",required = false) Long startDate,
            @RequestParam(value ="endDate",required = false) Long endDate,
            @RequestParam(value ="accommodationName",required = false) String accommodationName,
            @RequestParam("email") String email){
        Collection<Reservation> reservations = reservationService.searchOwnerReservations(startDate,endDate,accommodationName, email);
        return new ResponseEntity<>(reservations.stream()
                .map(OwnerReservationMapper::mapToOwnerReservationDTO)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("reservationId") Long reservationId) {
        reservationService.deleteById(reservationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/guest/{guestId}/cancellations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NumberOfCancellationsDTO> countCancellationsForGuest(@PathVariable("guestId") String guestId) {
        int numberOfCancellations = reservationService.countCancellationsForGuest(guestId);

        // Create and return the DTO
        NumberOfCancellationsDTO cancellationsDTO = new NumberOfCancellationsDTO(guestId, numberOfCancellations);
        return new ResponseEntity<>(cancellationsDTO, HttpStatus.OK);
    }
}