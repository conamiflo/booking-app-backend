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
import java.util.*;

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

    @Test
    public void ReservationService_AcceptReservation_DeclineWaitingReservations_NoReservations() {

        List<Reservation> emptyReservationList = Collections.emptyList();
        when(reservationRepository.findAll()).thenReturn(emptyReservationList);

        reservationService.declineWaitingReservations(START_DATE, END_DATE, 1L);

        verify(reservationRepository, never()).save(any(Reservation.class));
    }

    @Test
    public void ReservationService_AcceptReservation_DeclineWaitingReservations_NoOverlappingDates() {
        Accommodation accommodation = Accommodation.builder().id(1L).build();
        Reservation reservation1 = Reservation.builder().startDate(LocalDateTime.of(2024, 1, 20, 0, 0, 0).toEpochSecond(ZoneOffset.UTC))
                .endDate(LocalDateTime.of(2024, 1, 23, 0, 0, 0).toEpochSecond(ZoneOffset.UTC)).id(1L)
                .accommodation(accommodation).build();
        Reservation reservation2 = Reservation.builder().startDate(LocalDateTime.of(2024, 1, 27, 0, 0, 0).toEpochSecond(ZoneOffset.UTC))
                .endDate(LocalDateTime.of(2024, 1, 29, 0, 0, 0).toEpochSecond(ZoneOffset.UTC)).id(2L)
                .accommodation(accommodation).build();


        List<Reservation> reservations = Arrays.asList(reservation1, reservation2);
        when(reservationRepository.findAll()).thenReturn(reservations);


        reservationService.declineWaitingReservations(LocalDateTime.of(2024, 1, 24, 0, 0, 0).toEpochSecond(ZoneOffset.UTC),
                LocalDateTime.of(2024, 1, 26, 0, 0, 0).toEpochSecond(ZoneOffset.UTC),
                accommodation.getId());

        verify(reservationRepository, never()).save(any(Reservation.class));
    }

    @Test
    public void ReservationService_AcceptReservation_DeclineWaitingReservations_SpecificAccommodation() {
        Accommodation accommodation1 = Accommodation.builder().id(1L).build();
        Accommodation accommodation2 = Accommodation.builder().id(2L).build();
        Reservation reservation1 = Reservation.builder().startDate(LocalDateTime.of(2024, 1, 19, 0, 0, 0).toEpochSecond(ZoneOffset.UTC))
                .endDate(LocalDateTime.of(2024, 1, 21, 0, 0, 0).toEpochSecond(ZoneOffset.UTC)).id(1L)
                .accommodation(accommodation1).status(ReservationStatus.Waiting).build();
        Reservation reservation2 = Reservation.builder().startDate(LocalDateTime.of(2024, 1, 22, 0, 0, 0).toEpochSecond(ZoneOffset.UTC))
                .endDate(LocalDateTime.of(2024, 1, 24, 0, 0, 0).toEpochSecond(ZoneOffset.UTC)).id(1L)
                .accommodation(accommodation1).status(ReservationStatus.Waiting).build();
        Reservation reservation3 = Reservation.builder().startDate(LocalDateTime.of(2024, 1, 19, 0, 0, 0).toEpochSecond(ZoneOffset.UTC))
                .endDate(LocalDateTime.of(2024, 1, 23, 0, 0, 0).toEpochSecond(ZoneOffset.UTC)).id(1L)
                .accommodation(accommodation2).status(ReservationStatus.Waiting).build();

        List<Reservation> reservations = Arrays.asList(reservation1, reservation2, reservation3);
        when(reservationRepository.findAll()).thenReturn(reservations);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(null);

        reservationService.declineWaitingReservations(LocalDateTime.of(2024, 1, 20, 0, 0, 0).toEpochSecond(ZoneOffset.UTC),
                LocalDateTime.of(2024, 1, 23, 0, 0, 0).toEpochSecond(ZoneOffset.UTC),
                accommodation1.getId());


        verify(reservationRepository, times(2)).save(any(Reservation.class));  // Adjust the expected number accordingly
    }

    @Test
    public void ReservationService_AcceptReservation_DeclineWaitingReservations_OtherStatus() {
        Accommodation accommodation = Accommodation.builder().id(1L).build();
        Reservation waitingReservation = Reservation.builder().startDate(LocalDateTime.of(2024, 1, 19, 0, 0, 0).toEpochSecond(ZoneOffset.UTC))
                .endDate(LocalDateTime.of(2024, 1, 21, 0, 0, 0).toEpochSecond(ZoneOffset.UTC)).id(1L)
                .accommodation(accommodation).status(ReservationStatus.Waiting).build();
        Reservation acceptedReservation = Reservation.builder().startDate(LocalDateTime.of(2024, 1, 22, 0, 0, 0).toEpochSecond(ZoneOffset.UTC))
                .endDate(LocalDateTime.of(2024, 1, 24, 0, 0, 0).toEpochSecond(ZoneOffset.UTC)).id(1L)
                .accommodation(accommodation).status(ReservationStatus.Accepted).build();
        Reservation declinedReservation = Reservation.builder().startDate(LocalDateTime.of(2024, 1, 19, 0, 0, 0).toEpochSecond(ZoneOffset.UTC))
                .endDate(LocalDateTime.of(2024, 1, 23, 0, 0, 0).toEpochSecond(ZoneOffset.UTC)).id(1L)
                .accommodation(accommodation).status(ReservationStatus.Declined).build();

        List<Reservation> reservations = Arrays.asList(waitingReservation, acceptedReservation, declinedReservation);
        when(reservationRepository.findAll()).thenReturn(reservations);

        when(reservationRepository.save(any(Reservation.class))).thenReturn(null);

        reservationService.declineWaitingReservations(LocalDateTime.of(2024, 1, 20, 0, 0, 0).toEpochSecond(ZoneOffset.UTC),
                LocalDateTime.of(2024, 1, 23, 0, 0, 0).toEpochSecond(ZoneOffset.UTC),
                accommodation.getId());

        verify(reservationRepository, times(1)).save(waitingReservation);
        verify(reservationRepository, times(0)).save(acceptedReservation);
        verify(reservationRepository, times(0)).save(declinedReservation);
    }

    @Test
    public void ReservationService_AcceptReservation_DeclineWaitingReservations_WaitingStatus() {
        Accommodation accommodation = Accommodation.builder().id(1L).build();
        Reservation waitingReservation1 = Reservation.builder().startDate(LocalDateTime.of(2024, 1, 19, 0, 0, 0).toEpochSecond(ZoneOffset.UTC))
                .endDate(LocalDateTime.of(2024, 1, 21, 0, 0, 0).toEpochSecond(ZoneOffset.UTC)).id(1L)
                .accommodation(accommodation).status(ReservationStatus.Waiting).build();
        Reservation waitingReservation2 = Reservation.builder().startDate(LocalDateTime.of(2024, 1, 22, 0, 0, 0).toEpochSecond(ZoneOffset.UTC))
                .endDate(LocalDateTime.of(2024, 1, 24, 0, 0, 0).toEpochSecond(ZoneOffset.UTC)).id(1L)
                .accommodation(accommodation).status(ReservationStatus.Waiting).build();

        List<Reservation> reservations = Arrays.asList(waitingReservation1, waitingReservation2);
        when(reservationRepository.findAll()).thenReturn(reservations);


        when(reservationRepository.save(any(Reservation.class))).thenReturn(null);


        reservationService.declineWaitingReservations(LocalDateTime.of(2024, 1, 20, 0, 0, 0).toEpochSecond(ZoneOffset.UTC),
                LocalDateTime.of(2024, 1, 23, 0, 0, 0).toEpochSecond(ZoneOffset.UTC),
                accommodation.getId());


        verify(reservationRepository, times(1)).save(waitingReservation1);
        verify(reservationRepository, times(1)).save(waitingReservation2);

        assertEquals(ReservationStatus.Declined, waitingReservation1.getStatus());
        assertEquals(ReservationStatus.Declined, waitingReservation2.getStatus());
    }

}
