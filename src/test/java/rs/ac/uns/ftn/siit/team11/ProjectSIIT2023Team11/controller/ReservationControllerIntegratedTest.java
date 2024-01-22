/*package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.*;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO.ReservationDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO.ReservationForShowDTO;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReservationControllerIntegratedTest {
    @Autowired
    private TestRestTemplate restTemplate;

    private ReservationDTO reservationDTO;
    private Reservation reservation;

    private static final Optional<User> GUEST = Optional.of(new Guest());

    private static final Long START_DATE  = LocalDateTime.of(2024, 1, 20, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
    private static final Long END_DATE =  LocalDateTime.of(2024, 1, 25, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
    private static final Long DAY_DURATION = (long) (24 * 60 * 60);


    private Optional<Accommodation> accommodation;

//    @Test
//    public void testAllEmployees()
//    {
//        ResponseEntity<List<ReservationForShowDTO>> responseEntity = restTemplate.exchange(
//                "h/api/reservations",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<ReservationForShowDTO>>() {});
//        assertEquals(1, responseEntity.getBody().size());
//    }
//
////    @Test
////    public void testAddEmployee() {
////        Employee employee = new Employee("Lokesh", "Gupta", "howtodoinjava@gmail.com");
////        ResponseEntity<String> responseEntity = this.restTemplate
////                .postForEntity("http://localhost:" + port + "/employees", employee, String.class);
////        assertEquals(201, responseEntity.getStatusCodeValue());
////    }
    @BeforeEach
    public void init(){
        reservation = Reservation.builder().numberOfGuests(2).build();
        reservationDTO = ReservationDTO.builder().numberOfGuests(2).startDate(START_DATE+DAY_DURATION).endDate(END_DATE-DAY_DURATION).build();

        List<Availability> availabilityList = new ArrayList<>(){{add(new Availability(1L,new TimeSlot(START_DATE, END_DATE)));}};

        accommodation =  Optional.of(Accommodation.builder().minGuests(1).maxGuests(3).availability(availabilityList).build());
    }
    @Test
    @DisplayName("Should List All Reservations When making GET request to endpoint - /api/reservations")
    public void shouldRetrieveAllReservations() {
        ResponseEntity<List<ReservationForShowDTO>> responseEntity = restTemplate.exchange("/api/reservations",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ReservationForShowDTO>>() {
                });

        List<ReservationForShowDTO> comments = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, comments.size());
    }

    @Test
    @DisplayName("CREATE reservation - /api/reservations")
    public void ReservationController_CreateReservation_NonExistingGuest() throws Exception{

        ReservationDTO validReservationDTO = ReservationDTO.builder().numberOfGuests(2).startDate(START_DATE).endDate(END_DATE).build();
        Optional<Reservation> validReservation = Optional.of(Reservation.builder().numberOfGuests(2).startDate(START_DATE).endDate(END_DATE).accommodation(accommodation.get()).build());

        ResultActions response = mockMvc.perform(post("/api/reservations")
                .contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8")
                .content(objectMapper.writeValueAsString(validReservationDTO)));


        response.andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}
*/