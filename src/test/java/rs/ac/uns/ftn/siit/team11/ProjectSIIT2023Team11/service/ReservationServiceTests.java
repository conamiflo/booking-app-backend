package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;


import org.assertj.core.api.OptionalAssert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Price;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Reservation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.TimeSlot;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IReservationRepository;
import org.junit.jupiter.api.BeforeAll;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.PriceType;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.ReservationStatus;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;



public class ReservationServiceTests {
    @Mock
    private IReservationRepository reservationRepository;
    @Mock
    private AvailabilityService availabilityService;
    @InjectMocks
    private ReservationService reservationService;

    private final static Accommodation AUTOMATIC_APPROVAL_ACCOMMODATION = Accommodation.builder().automaticApproval(true).build();

    private final static Accommodation MANUAL_APPROVAL_ACCOMMODATION = Accommodation.builder().automaticApproval(false).build();
    private static final Long START_DATE  = LocalDateTime.of(2024, 1, 20, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
    private static final Long END_DATE =  LocalDateTime.of(2024, 1, 25, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
    private final static Price PRICE = new Price(1L,new TimeSlot(START_DATE,END_DATE),40.0);
    private static final Long DAY_DURATION = (long) (24 * 60 * 60);

    @Before
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }


    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    public void ReservationService_CreateReservation_IsAutomaticApproval(){

        List<Price> priceList = new ArrayList<>();
        priceList.add(PRICE);
        Accommodation accommodation = Accommodation.builder().defaultPrice(50.0).priceType(PriceType.PerGuest).priceList(priceList).automaticApproval(true).build();
        Reservation reservation = Reservation.builder().accommodation(accommodation).numberOfGuests(2).startDate(START_DATE-DAY_DURATION).endDate(START_DATE+(2*DAY_DURATION)).build();


        when(reservationService.save(reservation)).thenReturn(reservation);
        doNothing().when(availabilityService).fitAcceptedReservation(reservation.getStartDate(), reservation.getEndDate(), reservation.getAccommodation());

        Optional<Reservation> createdReservation = reservationService.createNewReservation(reservation);


        verify(availabilityService, times(1)).fitAcceptedReservation(reservation.getStartDate(), reservation.getEndDate(), reservation.getAccommodation());
        assertEquals(ReservationStatus.Accepted, createdReservation.get().getStatus());
        assertEquals(2*170.0, createdReservation.get().getPrice());

    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    public void ReservationService_CreateReservation_IsNotAutomaticApproval(){

        List<Price> priceList = new ArrayList<>();
        priceList.add(PRICE);
        Accommodation accommodation = Accommodation.builder().defaultPrice(50.0).priceType(PriceType.PerGuest).priceList(priceList).automaticApproval(false).build();
        Reservation reservation = Reservation.builder().accommodation(accommodation).numberOfGuests(2).startDate(START_DATE-DAY_DURATION).endDate(START_DATE+(2*DAY_DURATION)).build();


        when(reservationService.save(reservation)).thenReturn(reservation);
        doNothing().when(availabilityService).fitAcceptedReservation(reservation.getStartDate(), reservation.getEndDate(), reservation.getAccommodation());

        Optional<Reservation> createdReservation = reservationService.createNewReservation(reservation);


        verifyNoInteractions(availabilityService);
        assertEquals(ReservationStatus.Waiting, createdReservation.get().getStatus());
        assertEquals(2*170.0, createdReservation.get().getPrice());

    }
}
