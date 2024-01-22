package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.controller;

// Remove the following import
// import org.junit.runner.RunWith;

// Use the correct imports for JUnit 5
import com.fasterxml.jackson.databind.ObjectMapper;
import org.checkerframework.checker.nullness.Opt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;

// Remove this import
// import org.mockito.junit.jupiter.MockitoExtension;

// Use the correct import for JUnit 5
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.*;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO.OwnerReservationDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO.ReservationDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.OwnerReservationMapper;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.security.JwtTokenUtil;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.AccommodationService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.AvailabilityService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.ReservationService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.UserService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.ReservationStatus;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@WebMvcTest(ReservationController.class)
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReservationControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ReservationService reservationService;
    @MockBean
    private AccommodationService accommodationService;
    @MockBean
    private UserService userService;
    @MockBean
    private AvailabilityService availabilityService;
    @MockBean
    private JwtTokenUtil jwtTokenUtil;


    @Autowired
    ObjectMapper objectMapper;
    private ReservationDTO reservationDTO;
    private Reservation reservation;

    private static final Optional<User> GUEST = Optional.of(new Guest());

    private static final Long START_DATE  = LocalDateTime.of(2024, 1, 20, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
    private static final Long END_DATE =  LocalDateTime.of(2024, 1, 25, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
    private static final Long DAY_DURATION = (long) (24 * 60 * 60);


    private Optional<Accommodation> accommodation;

    @BeforeEach
    public void init(){
        reservation = Reservation.builder().numberOfGuests(2).build();
        reservationDTO = ReservationDTO.builder().numberOfGuests(2).startDate(START_DATE+DAY_DURATION).endDate(END_DATE-DAY_DURATION).build();

        List<Availability> availabilityList = new ArrayList<>(){{add(new Availability(1L,new TimeSlot(START_DATE, END_DATE)));}};

        accommodation =  Optional.of(Accommodation.builder().minGuests(1).maxGuests(3).availability(availabilityList).build());
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    public void ReservationController_CreateReservation_NonExistingGuest() throws Exception{

        ReservationDTO validReservationDTO = ReservationDTO.builder().numberOfGuests(2).startDate(START_DATE).endDate(END_DATE).build();
        Optional<Reservation> validReservation = Optional.of(Reservation.builder().numberOfGuests(2).startDate(START_DATE).endDate(END_DATE).accommodation(accommodation.get()).build());

        when(reservationService.createNewReservation(any())).thenReturn(validReservation);
        when(accommodationService.findById(any())).thenReturn(accommodation);
        when(userService.findById(any())).thenReturn(Optional.empty());

        ResultActions response = mockMvc.perform(post("/api/reservations")
                .contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8")
                .content(objectMapper.writeValueAsString(validReservationDTO)));


        response.andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    public void ReservationController_CreateReservation_NonExistingAccommodation() throws Exception{

        ReservationDTO validReservationDTO = ReservationDTO.builder().numberOfGuests(2).startDate(START_DATE).endDate(END_DATE).build();
        Optional<Reservation> validReservation = Optional.of(Reservation.builder().numberOfGuests(2).startDate(START_DATE).endDate(END_DATE).guest((Guest)GUEST.get()).build());

        when(reservationService.createNewReservation(any())).thenReturn(validReservation);
        when(accommodationService.findById(any())).thenReturn(Optional.empty());
        when(userService.findById(any())).thenReturn(GUEST);


        ResultActions response = mockMvc.perform(post("/api/reservations")
                .contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8")
                .content(objectMapper.writeValueAsString(validReservationDTO)));


        response.andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    public void ReservationController_CreateReservation_NonExistingGuestAndAccommodation() throws Exception{

        ReservationDTO validReservationDTO = ReservationDTO.builder().numberOfGuests(2).startDate(START_DATE).endDate(END_DATE).build();
        Optional<Reservation> validReservation = Optional.of(Reservation.builder().numberOfGuests(2).startDate(START_DATE).endDate(END_DATE).build());

        when(reservationService.createNewReservation(any())).thenReturn(validReservation);
        when(accommodationService.findById(any())).thenReturn(Optional.empty());
        when(userService.findById(any())).thenReturn(Optional.empty());


        ResultActions response = mockMvc.perform(post("/api/reservations")
                .contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8")
                .content(objectMapper.writeValueAsString(validReservationDTO)));


        response.andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    public void ReservationController_CreateReservation_ReturnCreatedGoodAvailability() throws Exception{

        ReservationDTO validReservationDTO = ReservationDTO.builder().numberOfGuests(2).startDate(START_DATE+DAY_DURATION).endDate(END_DATE-DAY_DURATION).build();
        Optional<Reservation> validReservation = Optional.of(Reservation.builder().numberOfGuests(2).startDate(START_DATE+DAY_DURATION).endDate(END_DATE-DAY_DURATION).accommodation(accommodation.get()).guest((Guest)GUEST.get()).build());

        when(reservationService.createNewReservation(any())).thenReturn(validReservation);
        when(accommodationService.findById(any())).thenReturn(accommodation);
        when(userService.findById(any())).thenReturn(GUEST);


        ResultActions response = mockMvc.perform(post("/api/reservations")
                .contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8")
                .content(objectMapper.writeValueAsString(validReservationDTO)));


        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    public void ReservationController_CreateReservation_ReturnCreatedAvailabilityBorderCase() throws Exception{

        ReservationDTO validReservationDTO = ReservationDTO.builder().numberOfGuests(2).startDate(START_DATE).endDate(END_DATE).build();
        Optional<Reservation> validReservation = Optional.of(Reservation.builder().numberOfGuests(2).startDate(START_DATE).endDate(END_DATE).accommodation(accommodation.get()).guest((Guest)GUEST.get()).build());

        when(reservationService.createNewReservation(any())).thenReturn(validReservation);
        when(accommodationService.findById(any())).thenReturn(accommodation);
        when(userService.findById(any())).thenReturn(GUEST);


        ResultActions response = mockMvc.perform(post("/api/reservations")
                .contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8")
                .content(objectMapper.writeValueAsString(validReservationDTO)));


        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    public void ReservationController_CreateReservation_DontReturnCreatedNotAvailable() throws Exception{

        ReservationDTO validReservationDTO = ReservationDTO.builder().numberOfGuests(2).startDate(START_DATE-DAY_DURATION).endDate(END_DATE+DAY_DURATION).build();
        Optional<Reservation> validReservation = Optional.of(Reservation.builder().numberOfGuests(2).startDate(START_DATE-DAY_DURATION).endDate(END_DATE+DAY_DURATION).accommodation(accommodation.get()).guest((Guest)GUEST.get()).build());

        when(reservationService.createNewReservation(any())).thenReturn(validReservation);
        when(accommodationService.findById(any())).thenReturn(accommodation);
        when(userService.findById(any())).thenReturn(GUEST);


        ResultActions response = mockMvc.perform(post("/api/reservations")
                .contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8")
                .content(objectMapper.writeValueAsString(validReservationDTO)));


        response.andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    public void ReservationController_AcceptReservation_NotFoundExistingReservation() throws Exception {
        Long nonExistingReservationId = 0L;

        when(reservationService.findById(any(Long.class))).thenReturn(Optional.empty());

        ResultActions response = mockMvc.perform(put("/api/reservations/accept/{reservationId}", nonExistingReservationId)
                .contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"));

        response.andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    public void ReservationController_AcceptReservation_ExistingReservation() throws Exception{
        Long existingReservationId = 1L;
        OwnerReservationDTO reservationDTO = OwnerReservationDTO.builder().build();
        Optional<Reservation> validReservation = Optional.of(Reservation.builder().id(existingReservationId)
                .startDate(START_DATE).endDate(END_DATE).accommodation(accommodation.get()).status(ReservationStatus.Waiting).build());

        Optional<Reservation> validReservationAccepted = Optional.of(Reservation.builder().id(existingReservationId)
                .startDate(START_DATE).endDate(END_DATE).accommodation(accommodation.get()).status(ReservationStatus.Accepted).guest(mock(Guest.class)).build());

        when(reservationService.findById(existingReservationId)).thenReturn(validReservation);
        when(reservationService.save(validReservation.get())).thenReturn(validReservationAccepted.get());
        try(MockedStatic<OwnerReservationMapper> mockedMapper = Mockito.mockStatic(OwnerReservationMapper.class,Mockito.CALLS_REAL_METHODS)){
            mockedMapper.when(() -> OwnerReservationMapper.mapToOwnerReservationDTO(validReservationAccepted.get())).thenReturn(reservationDTO);
        }

        ResultActions response = mockMvc.perform(put("/api/reservations/accept/{reservationId}", existingReservationId)
                .contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8"));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("Accepted"))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));


        verify(reservationService, times(1)).findById(existingReservationId);
        verify(reservationService, times(1)).save(validReservation.get());
        verify(reservationService, times(1))
                .declineWaitingReservations(validReservation.get().getStartDate(), validReservation.get().getEndDate(), validReservation.get().getAccommodation().getId());
        verify(availabilityService, times(1))
                .fitAcceptedReservation(validReservation.get().getStartDate(), validReservation.get().getEndDate(), validReservation.get().getAccommodation());

    }


}
