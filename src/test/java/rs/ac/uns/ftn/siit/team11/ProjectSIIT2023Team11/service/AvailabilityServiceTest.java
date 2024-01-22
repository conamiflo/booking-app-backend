package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.junit.Test;
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
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IAccommodationRepository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IAvailabilityRepository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IReservationRepository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IUserRepository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.AccommodationStatus;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.PriceType;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class AvailabilityServiceTest {
    @MockBean
    private IAccommodationRepository accommodationRepository;

    @MockBean
    private IReservationRepository reservationRepository;

    @MockBean
    private IUserRepository userRepository;

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
    @DisplayName("It should create availability for accommodation")
    public void createAvailabilityForAccommodation() {
        // Arrange
        Long startDate = LocalDateTime.of(2024, 2, 13, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long endDate = LocalDateTime.of(2024, 2, 16, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);


        when(accommodationRepository.findById(accommodation.getId())).thenReturn(Optional.of(accommodation));
        when(availabilityService.save(any(Availability.class))).thenReturn(new Availability());
        when(availabilityService.findAll()).thenReturn(new ArrayList<>());

        Availability result = availabilityService.createAvailabilityForAccommodation(accommodation, startDate, endDate);

        assertNotNull(result);
        verify(availabilityService, times(1)).save(any(Availability.class));
    }
}
