package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.*;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO.AccommodationPricesDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO.AvailabilityDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.*;
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


public class PriceServiceTest {
    @Mock
    private IPriceRepository priceRepository;

    @Mock
    private IAccommodationService accommodationService;

    @InjectMocks
    private PriceService priceService;

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
    @DisplayName("It should create price for accommodation")
    public void createPriceForAccommodationValid() {

        Long startDate = LocalDateTime.of(2024, 2, 13, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long endDate = LocalDateTime.of(2024, 2, 23, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);

        when(accommodationService.findById(anyLong())).thenReturn(Optional.of(accommodation));
        when(priceRepository.save(any(Price.class))).thenReturn(new Price());

        AccommodationPricesDTO result = priceService.createPriceForAccommodation(accommodation, startDate, endDate, 50.0);

        assertNotNull(result);
        verify(priceRepository, times(1)).save(any(Price.class));
        verify(accommodationService, times(1)).save(any(Accommodation.class));
    }

    @Test
    @DisplayName("It should return null for overlapping prices")
    public void createPriceForAccommodationOverlappingPrice() {

        Long startDate = LocalDateTime.of(2024, 2, 24, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long endDate = LocalDateTime.of(2024, 2, 27, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long existingStartDate = LocalDateTime.of(2024, 2, 21, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long existingEndDate = LocalDateTime.of(2024, 2, 28, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);

        List<Price> existingPrices = new ArrayList<>();
        existingPrices.add(new Price(1L, new TimeSlot(existingStartDate, existingEndDate), 50.0));

        accommodation.setPriceList(existingPrices);

        when(accommodationService.findById(anyLong())).thenReturn(Optional.of(accommodation));

        AccommodationPricesDTO result = priceService.createPriceForAccommodation(accommodation, startDate, endDate, 50.0);

        assertNull(result);
        verify(priceRepository, never()).save(any(Price.class));
        verify(accommodationService, never()).save(any(Accommodation.class));
    }

    @Test
    @DisplayName("It should return null for invalid input dates")
    public void createPriceForAccommodationInvalidDate() {

        Long startDate = LocalDateTime.of(2024, 2, 27, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long endDate = LocalDateTime.of(2024, 2, 24, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);

        AccommodationPricesDTO result = priceService.createPriceForAccommodation(accommodation, startDate, endDate, 50.0);
        assertNull(result);
        verify(priceRepository, never()).save(any(Price.class));
        verify(accommodationService, never()).save(any(Accommodation.class));
    }

    @Test
    @DisplayName("It should return null for invalid first or second date")
    public void createPriceForAccommodationInvalidDateToday() {

        Long startDate = Instant.now().getEpochSecond();
        Long endDate = LocalDateTime.of(2024, 1, 14, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);

        when(accommodationService.findById(accommodation.getId())).thenReturn(Optional.of(accommodation));
        AccommodationPricesDTO result = priceService.createPriceForAccommodation(accommodation, startDate, endDate, 50.0);

        assertNull(result);
        verify(priceRepository, never()).save(any(Price.class));
        verify(accommodationService, never()).save(any(Accommodation.class));
    }

    @Test
    @DisplayName("It should return true for overlapping dates")
    public void isOverlapNonOverlappingDatesInvalid() {

        Long existingStartDate = LocalDateTime.of(2024, 2, 13, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long existingEndDate = LocalDateTime.of(2024, 2, 17, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long newStartDate = LocalDateTime.of(2024, 2, 14, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long newEndDate = LocalDateTime.of(2024, 2, 18, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);

        boolean result = priceService.isOverlap(newStartDate, newEndDate, existingStartDate, existingEndDate);

        assertTrue(result);
    }

    @Test
    @DisplayName("It should return false for non overlapping dates")
    public void isOverlapNonOverlappingDatesValid() {

        Long existingStartDate = LocalDateTime.of(2024, 2, 13, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long existingEndDate = LocalDateTime.of(2024, 2, 17, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long newStartDate = LocalDateTime.of(2024, 2, 18, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long newEndDate = LocalDateTime.of(2024, 2, 19, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);

        boolean result = priceService.isOverlap(newStartDate, newEndDate, existingStartDate, existingEndDate);
        assertFalse(result);
    }


    @Test
    @DisplayName("It should return null for invalid price -")
    public void createPriceForAccommodationInvalidPriceMinus() {

        Long startDate = LocalDateTime.of(2024, 2, 18, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long endDate = LocalDateTime.of(2024, 2, 19, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Double invalidPrice = -100.0;
        AccommodationPricesDTO result = priceService.createPriceForAccommodation(accommodation, startDate, endDate, invalidPrice);
        assertNull(result);
        verify(priceRepository, never()).save(any(Price.class));
        verify(accommodationService, never()).save(any(Accommodation.class));
    }

    @Test
    @DisplayName("It should return null for invalid price 0 ")
    public void createPriceForAccommodationInvalidPriceZero() {

        Long startDate = LocalDateTime.of(2024, 2, 18, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long endDate = LocalDateTime.of(2024, 2, 19, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Double invalidPrice = 0.0;
        AccommodationPricesDTO result = priceService.createPriceForAccommodation(accommodation, startDate, endDate, invalidPrice);
        assertNull(result);
        verify(priceRepository, never()).save(any(Price.class));
        verify(accommodationService, never()).save(any(Accommodation.class));
    }

}
