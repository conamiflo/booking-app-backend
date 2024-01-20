package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Guest;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Reservation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.User;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO.ReservationDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.security.JwtTokenUtil;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.AccommodationService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.AvailabilityService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.ReservationService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.UserService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(ReservationController.class)
@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
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

    private Optional<User> optionalUser;
    private Optional<Accommodation> optionalAccommodation;

    private Optional<Reservation> optionalReservation;
    @BeforeEach
    public void init(){
        reservation = Reservation.builder().numberOfGuests(2).build();
        reservationDTO = ReservationDTO.builder().numberOfGuests(2).build();
        optionalReservation = Optional.of(reservation);
        optionalUser = Optional.of( User.builder().build());
        optionalAccommodation = Optional.of(Accommodation.builder().build());
    }


    @Test
    public void ReservationController_CreateReservation_ReturnCreated() throws Exception{
        reservation = Reservation.builder().numberOfGuests(2).build();
        reservationDTO = ReservationDTO.builder().numberOfGuests(2).build();
        optionalReservation = Optional.of(reservation);
        optionalUser = Optional.of(new Guest());
        optionalAccommodation = Optional.of(Accommodation.builder().build());


        when(reservationService.createNewReservation(any())).thenReturn(optionalReservation);
        when(accommodationService.findById(any())).thenReturn(optionalAccommodation);
        when(userService.findById(any())).thenReturn(optionalUser);


        ResultActions response = mockMvc.perform(post("/api/reservations")
                .contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8")
                .content(objectMapper.writeValueAsString(reservationDTO)));
        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }

}
