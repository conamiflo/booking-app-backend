package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Availability;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.TimeSlot;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IAccommodationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class AccommodationServiceTests {
    @InjectMocks
    private AccommodationService accommodationService;

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
}
