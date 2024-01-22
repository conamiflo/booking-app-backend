package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.*;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO.AvailabilityDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IAccommodationRepository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IAvailabilityRepository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IReservationRepository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IUserRepository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.AccommodationStatus;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.PriceType;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.ReservationStatus;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class AvailabilityServiceTest {

    @MockBean
    private AccommodationService accommodationService;
    @MockBean
    private UserService userService;
    @MockBean
    private ReservationService reservationService;
    @MockBean
    private IAvailabilityRepository availabilityRepository;

    @Autowired
    private AvailabilityService availabilityService;
    private Accommodation accommodation;

    @BeforeEach
    public void setUp() {

        MockitoAnnotations.openMocks(this);

        Long startDate = LocalDateTime.of(2024, 1, 24, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Owner owner = new Owner(); owner.setEmail("owner@gmail.com");

        accommodation = new Accommodation(0L,owner,"Accommodation test service","","",new ArrayList<Amenity>(),new ArrayList<Price>(),
                new ArrayList<Availability>(),new ArrayList<String>(), 1,3, "PentHouse", 50.0,false,true,3,
                startDate, PriceType.PerNight, AccommodationStatus.Pending);
    }

    @Test
    @DisplayName("It should return true for overlapping dates")
    public void isOverlapNonOverlappingDatesInvalid() {

        Long existingStartDate = LocalDateTime.of(2024, 2, 13, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long existingEndDate = LocalDateTime.of(2024, 2, 17, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long newStartDate = LocalDateTime.of(2024, 2, 14, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long newEndDate = LocalDateTime.of(2024, 2, 18, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);

        boolean result = availabilityService.isOverlap(newStartDate, newEndDate, existingStartDate, existingEndDate);

        assertTrue(result);
    }

    @Test
    @DisplayName("It should return false for non overlapping dates")
    public void isOverlapNonOverlappingDatesValid() {

        Long existingStartDate = LocalDateTime.of(2024, 2, 13, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long existingEndDate = LocalDateTime.of(2024, 2, 17, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long newStartDate = LocalDateTime.of(2024, 2, 18, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long newEndDate = LocalDateTime.of(2024, 2, 19, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);

        boolean result = availabilityService.isOverlap(newStartDate, newEndDate, existingStartDate, existingEndDate);
        assertFalse(result);
    }

    @Test
    @DisplayName("It should return true for overlapping reservations")
    public void isOverLappingWithReservationsInvalid() {

        Long newStartDate = LocalDateTime.of(2024, 2, 14, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long newEndDate = LocalDateTime.of(2024, 2, 18, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long reservationStartDate = LocalDateTime.of(2024, 2, 13, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long reservationEndDate = LocalDateTime.of(2024, 2, 17, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);

        Reservation overlappingReservation = new Reservation();
        overlappingReservation.setStatus(ReservationStatus.Accepted);
        overlappingReservation.setStartDate(reservationStartDate);
        overlappingReservation.setEndDate(reservationEndDate);
        overlappingReservation.setAccommodation(accommodation);

        List<Reservation> reservations = new ArrayList<>();
        reservations.add(overlappingReservation);

        when(reservationService.getReservationsByAccommodationId(anyLong())).thenReturn(reservations);
        boolean result = availabilityService.isOverLappingWithReservations(newStartDate, newEndDate, 1L);

        assertTrue(result);
    }

    @Test
    @DisplayName("It should return false for non overlapping reservations")
    public void isOverLappingWithReservationsValid() {

        Long newStartDate = LocalDateTime.of(2024, 2, 18, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long newEndDate = LocalDateTime.of(2024, 2, 21, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long reservationStartDate = LocalDateTime.of(2024, 2, 13, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long reservationEndDate = LocalDateTime.of(2024, 2, 17, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);

        Reservation nonOverlappingReservation = new Reservation();
        nonOverlappingReservation.setStatus(ReservationStatus.Accepted);
        nonOverlappingReservation.setStartDate(reservationStartDate);
        nonOverlappingReservation.setEndDate(reservationEndDate);

        List<Reservation> reservations = new ArrayList<>();
        reservations.add(nonOverlappingReservation);

        when(reservationService.getReservationsByAccommodationId(anyLong())).thenReturn(reservations);

        boolean result = availabilityService.isOverLappingWithReservations(newStartDate, newEndDate, 1L);

        assertFalse(result);
    }

    @Test
    @DisplayName("It should create availability for accommodation")
    public void createAvailabilityForAccommodationValid() {

        Long newStartDate = LocalDateTime.of(2024, 2, 18, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long newEndDate = LocalDateTime.of(2024, 2, 21, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);

        Availability availability = new Availability(15L,new TimeSlot(newStartDate,newEndDate));

        when(accommodationService.findById(accommodation.getId())).thenReturn(Optional.of(accommodation));
        when(availabilityService.save(availability)).thenReturn(availability);
        when(availabilityService.findById(availability.getId())).thenReturn(Optional.of(availability));

        Availability result = availabilityService.createAvailabilityForAccommodation(accommodation, newStartDate, newEndDate);

        assertNotNull(result);
        verify(availabilityService, times(1)).save(availability);
    }

    @Test
    @DisplayName("It should return null for overlapping dates with existing availability")
    public void createAvailabilityForAccommodationOverlappingDatesInvalid() {

        Long existingStartDate = LocalDateTime.of(2024, 1, 15, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long existingEndDate = LocalDateTime.of(2024, 1, 21, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);

        Long startDate = LocalDateTime.of(2024, 1, 17, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long endDate = LocalDateTime.of(2024, 1, 22, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);

        List<Availability> existingAvailabilities = new ArrayList<>();
        existingAvailabilities.add(new Availability(1L, new TimeSlot(existingStartDate, existingEndDate)));

        accommodation.setAvailability(existingAvailabilities);

        when(accommodationService.findById(anyLong())).thenReturn(java.util.Optional.of(accommodation));

        Availability result = availabilityService.createAvailabilityForAccommodation(accommodation, startDate, endDate);

        assertNull(result);
        verify(availabilityRepository, never()).save(any(Availability.class));
    }

    @Test
    @DisplayName("It should return null for overlapping dates with reservations")
    public void createAvailabilityForAccommodation_OverlappingReservations_ShouldReturnNull() {

        Long existingStartDate = LocalDateTime.of(2024, 1, 15, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long existingEndDate = LocalDateTime.of(2024, 1, 21, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);

        Long startDate = LocalDateTime.of(2024, 1, 17, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long endDate = LocalDateTime.of(2024, 1, 22, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);

        List<Reservation> reservations = new ArrayList<>();
        Reservation reservation = new Reservation();
        reservation.setStartDate(existingStartDate); reservation.setEndDate(existingEndDate); reservation.setAccommodation(accommodation);

        when(accommodationService.findById(anyLong())).thenReturn(java.util.Optional.of(accommodation));
        when(reservationService.getReservationsByAccommodationId(anyLong())).thenReturn(reservations);

        Availability result = availabilityService.createAvailabilityForAccommodation(accommodation, startDate, endDate);

        assertNull(result);
        verify(availabilityService, never()).save(any(Availability.class));
    }

    @Test
    @DisplayName("It should return null if end date is before start date")
    public void createAvailabilityForAccommodationEndDateBeforeStartDate() {

        Long startDate = LocalDateTime.of(2024, 1, 22, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long endDate = LocalDateTime.of(2024, 1, 17, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);

        when(accommodationService.findById(anyLong())).thenReturn(Optional.of(accommodation));

        Availability result = availabilityService.createAvailabilityForAccommodation(accommodation, startDate, endDate);

        assertNull(result);
        verify(availabilityRepository, never()).save(any(Availability.class));
    }

    @Test
    @DisplayName("It should return null if start date is before today")
    public void createAvailabilityForAccommodationStartDateBeforeToday() {

        Long startDate = LocalDateTime.of(2024, 1, 15, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long endDate = LocalDateTime.of(2024, 1, 17, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);

        when(accommodationService.findById(anyLong())).thenReturn(Optional.of(accommodation));

        Availability result = availabilityService.createAvailabilityForAccommodation(accommodation, startDate, endDate);

        assertNull(result);
        verify(availabilityRepository, never()).save(any(Availability.class));
    }

}
