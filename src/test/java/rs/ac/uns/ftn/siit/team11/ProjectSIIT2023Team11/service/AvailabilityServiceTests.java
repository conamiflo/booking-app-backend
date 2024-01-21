package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.*;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IAvailabilityRepository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IReservationRepository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.PriceType;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.ReservationStatus;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class AvailabilityServiceTests {
    @Mock
    private IAvailabilityRepository availabilityRepository;
    @Mock
    private AccommodationService accommodationService;
    @InjectMocks
    private AvailabilityService availabilityService;

    private final static Accommodation AUTOMATIC_APPROVAL_ACCOMMODATION = Accommodation.builder().automaticApproval(true).build();

    private final static Accommodation MANUAL_APPROVAL_ACCOMMODATION = Accommodation.builder().automaticApproval(false).build();
    private static final Long START_DATE1  = LocalDateTime.of(2024, 1, 20, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
    private static final Long END_DATE1 =  LocalDateTime.of(2024, 1, 25, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
    private static final Long START_DATE2  = LocalDateTime.of(2024, 1, 26, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
    private static final Long END_DATE2 =  LocalDateTime.of(2024, 1, 29, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
    private final static Price PRICE = new Price(1L,new TimeSlot(START_DATE1,END_DATE1),40.0);
    private static final Long DAY_DURATION = (long) (24 * 60 * 60);

    @Before
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void AvailabilityService_FitAcceptedReservation_FitWholeAvailability(){
        List<Availability> availabilities = new ArrayList<>();
        availabilities.add(new Availability(1L, new TimeSlot(START_DATE1, END_DATE1)));
        availabilities.add(new Availability(1L, new TimeSlot(START_DATE2, END_DATE2)));
        Accommodation accommodation = Accommodation.builder().availability(availabilities).build();
        TimeSlot timeSlotToFit = new TimeSlot(START_DATE1, END_DATE1);

        availabilityService.fitAcceptedReservation(START_DATE1, END_DATE1, accommodation);

        assertEquals(1,accommodation.getAvailability().size());

    }

    @Test
    public void AvailabilityService_FitAcceptedReservation_FitMiddlePartOfOneAvailability(){
        List<Availability> availabilities = new ArrayList<>();
        availabilities.add(new Availability(1L, new TimeSlot(START_DATE1, END_DATE1)));
        availabilities.add(new Availability(1L, new TimeSlot(START_DATE2, END_DATE2)));
        Accommodation accommodation = Accommodation.builder().availability(availabilities).build();
        TimeSlot timeSlotToFit = new TimeSlot(START_DATE1, END_DATE1);

        availabilityService.fitAcceptedReservation(START_DATE1+DAY_DURATION, END_DATE1-DAY_DURATION, accommodation);

        assertEquals(3,accommodation.getAvailability().size());

    }

    @Test
    public void AvailabilityService_FitAcceptedReservation_FitStartSidePartOfOneAvailability(){
        List<Availability> availabilities = new ArrayList<>();
        availabilities.add(new Availability(1L, new TimeSlot(START_DATE1, END_DATE1)));
        availabilities.add(new Availability(1L, new TimeSlot(START_DATE2, END_DATE2)));
        Accommodation accommodation = Accommodation.builder().availability(availabilities).build();
        TimeSlot timeSlotToFit = new TimeSlot(START_DATE1, END_DATE1);

        availabilityService.fitAcceptedReservation(START_DATE1, END_DATE1-(2*DAY_DURATION), accommodation);

        assertEquals(2,accommodation.getAvailability().size());

    }

    @Test
    public void AvailabilityService_FitAcceptedReservation_FitEndSidePartOfOneAvailability(){
        List<Availability> availabilities = new ArrayList<>();
        availabilities.add(new Availability(1L, new TimeSlot(START_DATE1, END_DATE1)));
        availabilities.add(new Availability(1L, new TimeSlot(START_DATE2, END_DATE2)));
        Accommodation accommodation = Accommodation.builder().availability(availabilities).build();
        TimeSlot timeSlotToFit = new TimeSlot(START_DATE1, END_DATE1);

        availabilityService.fitAcceptedReservation(START_DATE1+(2*DAY_DURATION), END_DATE1, accommodation);

        assertEquals(2,accommodation.getAvailability().size());

    }

    @Test
    public void AvailabilityService_FitAcceptedReservation_FitOneSidePartOfMultipleNeighbourAvailabilities(){
        List<Availability> availabilities = new ArrayList<>();
        availabilities.add(new Availability(1L, new TimeSlot(START_DATE1, END_DATE1)));
        availabilities.add(new Availability(1L, new TimeSlot(START_DATE2, END_DATE2)));
        Accommodation accommodation = Accommodation.builder().availability(availabilities).build();
        TimeSlot timeSlotToFit = new TimeSlot(START_DATE1, END_DATE1);

        availabilityService.fitAcceptedReservation(START_DATE1, END_DATE1-(2*DAY_DURATION), accommodation);

        assertEquals(2,accommodation.getAvailability().size());

    }


}
