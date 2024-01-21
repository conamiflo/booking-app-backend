package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;
import com.beust.ah.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.security.core.parameters.P;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
public class ReservationTests {
    private Reservation reservation;
    private static final Long START_DATE1  = LocalDateTime.of(2024, 1, 20, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
    private static final Long END_DATE1 =  LocalDateTime.of(2024, 1, 25, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
    private static final Long START_DATE2  = LocalDateTime.of(2024, 1, 26, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
    private static final Long END_DATE2 =  LocalDateTime.of(2024, 1, 29, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);

    private static final Long START_DATE3  = LocalDateTime.of(2024, 2, 20, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
    private static final Long END_DATE3 =  LocalDateTime.of(2024, 2, 28, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
    private final static Price PRICE = new Price(1L,new TimeSlot(START_DATE1,END_DATE1),40.0);
    private static final Long DAY_DURATION = (long) (24 * 60 * 60);
    private Accommodation accommodation;

    @BeforeEach
    public void setup(){

        Availability availability1 = new Availability(1L, new TimeSlot(START_DATE1, END_DATE1));
        Availability availability2 = new Availability(2L, new TimeSlot(START_DATE2, END_DATE2));
        Availability availability3 = new Availability(3L, new TimeSlot(START_DATE3, END_DATE3));
        Price price1 = new Price(1L, new TimeSlot(START_DATE1+(DAY_DURATION*2), END_DATE1),40.0);

        List<Price> priceList = new ArrayList<>();
        priceList.add(price1);
        List<Availability> availabilityList = new ArrayList<>();
        availabilityList.add(availability1);
        availabilityList.add(availability2);
        availabilityList.add(availability3);

        accommodation = Accommodation.builder().availability(availabilityList).priceList(priceList).build();
        reservation = Reservation.builder().accommodation(accommodation).build();

    }

    @Test
    void Reservation_IsAvailable_ContainsWholeTimeSlot(){
        reservation = Reservation.builder().accommodation(accommodation).startDate(START_DATE1).endDate(END_DATE1).build();

        boolean isAvailable = reservation.isAvailable();

        assertTrue(isAvailable);

    }

    @Test
    void Reservation_IsAvailable_ContainsPartOfOneTimeSlot(){
        reservation = Reservation.builder().accommodation(accommodation).startDate(START_DATE1+DAY_DURATION).endDate(END_DATE1-DAY_DURATION).build();

        boolean isAvailable = reservation.isAvailable();

        assertTrue(isAvailable);

    }

    @Test
    void Reservation_IsAvailable_ContainsPartOfMultipleNeighbourTimeSlots(){
        reservation = Reservation.builder().accommodation(accommodation).startDate(START_DATE1+DAY_DURATION).endDate(END_DATE2-DAY_DURATION).build();

        boolean isAvailable = reservation.isAvailable();

        assertTrue(isAvailable);

    }
    @Test
    void Reservation_IsAvailable_ContainsWholeMultipleNeighbourTimeSlots(){
        reservation = Reservation.builder().accommodation(accommodation).startDate(START_DATE1).endDate(END_DATE2).build();

        boolean isAvailable = reservation.isAvailable();

        assertTrue(isAvailable);

    }

    @Test
    void Reservation_IsAvailable_ContainsWholeMultipleNonNeighbourTimeSlots(){
        reservation = Reservation.builder().accommodation(accommodation).startDate(START_DATE1).endDate(END_DATE3).build();

        boolean isAvailable = reservation.isAvailable();

        assertFalse(isAvailable);

    }

    @Test
    void Reservation_IsAvailable_ContainsPartOfMultipleNonNeighbourTimeSlots(){
        reservation = Reservation.builder().accommodation(accommodation).startDate(START_DATE1+DAY_DURATION).endDate(END_DATE3-DAY_DURATION).build();

        boolean isAvailable = reservation.isAvailable();

        assertFalse(isAvailable);

    }
}
