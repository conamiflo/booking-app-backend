package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Availability;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Owner;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.TimeSlot;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO.AccommodationDetailsDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IAccommodationRepository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.AccommodationStatus;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.PriceType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class AccommodationServiceTests {
    @InjectMocks
    private AccommodationService accommodationService;

    @Mock
    private UserService userService;
    @Mock
    private IAccommodationRepository accommodationRepository;
    private final static Long VALID_ID = 1L;

    private final static Long INVALID_ID = 0L;
    Optional<Accommodation> accommodation = Optional.of(Accommodation.builder().build());
    @Before
    public void setup(){

        MockitoAnnotations.openMocks(this);

        when(accommodationRepository.findById(VALID_ID)).thenReturn(accommodation);
        when(accommodationRepository.findById(INVALID_ID)).thenReturn(Optional.empty());
    }

    @Test
    public void AccommodationService_FindById_ValidId(){
        Optional<Accommodation> optionalAccommodation = accommodationService.findById(VALID_ID);
        assertEquals(accommodation, optionalAccommodation);
    }

    @Test
    public void AccommodationService_FindById_InvalidId(){
        Optional<Accommodation> optionalAccommodation = accommodationService.findById(INVALID_ID);
        assertEquals(Optional.empty(), optionalAccommodation);
    }

    @Test
    @DisplayName("It should return empty optional for invalid cancellation days 0")
    public void createAccommodationCancellationDaysZero() {
        AccommodationDetailsDTO invalidAccommodationDetails = new AccommodationDetailsDTO(
                1L,
                "valid.owner@gmail.com",
                "Valid Accommodation",
                "Description",
                "Location",
                100.0,
                List.of("photo1.jpg", "photo2.jpg"),
                2,
                4,
                System.currentTimeMillis(),
                "Penthouse",
                PriceType.PerNight,
                AccommodationStatus.Active,
                0
        );

        when(userService.findById("valid.owner@gmail.com")).thenReturn(Optional.of(new Owner()));
        Optional<AccommodationDetailsDTO> result = accommodationService.create(invalidAccommodationDetails, userService);
        assertTrue(result.isEmpty());
        verify(accommodationRepository, never()).save(any(Accommodation.class));
    }

    @Test
    @DisplayName("It should return empty optional for invalid cancellation days -")
    public void createAccommodationCancellationDaysMinus() {
        Owner owner = new Owner(); owner.setEmail("valid.owner@gmail.com");
        AccommodationDetailsDTO invalidAccommodationDetails = new AccommodationDetailsDTO(
                1L,
                "valid.owner@gmail.com",
                "Valid Accommodation",
                "Description",
                "Location",
                100.0,
                List.of(),
                2,
                4,
                System.currentTimeMillis(),
                "Penthouse",
                PriceType.PerNight,
                AccommodationStatus.Active,
                -5
        );

        when(userService.findById("valid.owner@gmail.com")).thenReturn(Optional.of(owner));
        Optional<AccommodationDetailsDTO> result = accommodationService.create(invalidAccommodationDetails, userService);
        assertTrue(result.isEmpty());
        verify(accommodationRepository, never()).save(any(Accommodation.class));
    }


    @Test
    @DisplayName("It should return valid accommodation")
    public void createAccommodationCancellationDaysValid() {
        Owner owner = new Owner(); owner.setEmail("valid.owner@gmail.com");
        AccommodationDetailsDTO validAccommodationDetails = new AccommodationDetailsDTO(
                1L,
                "valid.owner@gmail.com",
                "Valid Accommodation",
                "Description",
                "Location",
                100.0,
                List.of(),
                2,
                4,
                System.currentTimeMillis(),
                "Penthouse",
                PriceType.PerNight,
                AccommodationStatus.Active,
                3
        );
        when(userService.findById("valid.owner@gmail.com")).thenReturn(Optional.of(owner));
        when(accommodationRepository.save(any(Accommodation.class))).thenReturn(new Accommodation());

        Optional<AccommodationDetailsDTO> result = accommodationService.create(validAccommodationDetails, userService);
        assertTrue(result.isPresent());
        verify(accommodationRepository, times(1)).save(any(Accommodation.class));
    }



}
