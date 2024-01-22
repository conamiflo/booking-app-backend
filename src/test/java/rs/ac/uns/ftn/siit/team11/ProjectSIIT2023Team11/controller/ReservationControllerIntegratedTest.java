package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.*;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO.AccommodationDetailsDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO.ReservationDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO.ReservationForShowDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.UserDTO.UserForShowDTO;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    private static final String GUEST_EMAIL = "guest@example.com";
    private static final Long START_DATE  = 1642972800000L;
    private static final Long END_DATE = 1643673600000L;
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

        reservationDTO = ReservationDTO.builder().numberOfGuests(2).startDate(START_DATE+DAY_DURATION).endDate(END_DATE-DAY_DURATION).build();

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
    @DisplayName("Should List All Reservations When making GET request to endpoint - /api/reservations")
    public void shouldRetrieveAllGuests() {
        ResponseEntity<List<UserForShowDTO>> responseEntity = restTemplate.exchange("/api/users",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<UserForShowDTO>>() {
                });

        List<UserForShowDTO> comments = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(2, comments.size());
    }

    @Test
    @DisplayName("Should List All Reservations When making GET request to endpoint - /api/reservations")
    public void shouldRetrieveAllAccommodation() {
        ResponseEntity<List<AccommodationDetailsDTO>> responseEntity = restTemplate.exchange("/api/accommodations",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AccommodationDetailsDTO>>() {
                });

        List<AccommodationDetailsDTO> comments = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(1, comments.size());
    }


    @Test
    @DisplayName("CREATE reservation - /api/reservations")
    public void ReservationController_CreateReservation_Created() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ReservationDTO validReservation = ReservationDTO.builder()
                .guest(GUEST_EMAIL)
                .numberOfGuests(2)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .accommodation(1L)
                .build();

        HttpEntity<ReservationDTO> requestEntity = new HttpEntity<>(validReservation, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange("/api/reservations",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

    }


    @Test
    @DisplayName("CREATE reservation - /api/reservations")
    public void ReservationController_CreateReservation_NotAvailable() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ReservationDTO validReservation = ReservationDTO.builder()
                .guest(GUEST_EMAIL)
                .numberOfGuests(2)
                .startDate(START_DATE-3000)
                .endDate(END_DATE)
                .accommodation(1L)
                .build();

        HttpEntity<ReservationDTO> requestEntity = new HttpEntity<>(validReservation, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange("/api/reservations",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        assertTrue(responseEntity.getStatusCode().is4xxClientError());

    }

    @Test
    @DisplayName("CREATE reservation - /api/reservations")
    public void ReservationController_CreateReservation_GuestNotExisting() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ReservationDTO validReservation = ReservationDTO.builder()
                .guest("invalid@gmail.com")
                .numberOfGuests(2)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .accommodation(1L)
                .build();

        HttpEntity<ReservationDTO> requestEntity = new HttpEntity<>(validReservation, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange("/api/reservations",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        assertTrue(responseEntity.getStatusCode().is4xxClientError());

    }

    @Test
    @DisplayName("CREATE reservation - /api/reservations")
    public void ReservationController_CreateReservation_AccommodationNotExisting() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ReservationDTO validReservation = ReservationDTO.builder()
                .guest(GUEST_EMAIL)
                .numberOfGuests(2)
                .startDate(START_DATE)
                .endDate(END_DATE)
                .accommodation(0L)
                .build();

        HttpEntity<ReservationDTO> requestEntity = new HttpEntity<>(validReservation, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange("/api/reservations",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        assertTrue(responseEntity.getStatusCode().is4xxClientError());

    }

}
