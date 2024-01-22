package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.http.*;
import org.springframework.test.context.TestPropertySource;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.User;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO.OwnerReservationDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.UserDTO.AuthResponseDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.UserDTO.UserLoginDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.security.JwtTokenUtil;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.ReservationStatus;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration")
@AutoConfigureMockMvc(addFilters = false)
@TestPropertySource(
        locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class ReservationControllerIntegrationTests {
    @Autowired
    private TestRestTemplate restTemplate;

    private String jwt;

    @BeforeEach
    public  void loginOwner() {
        HttpHeaders headers = new HttpHeaders();
        UserLoginDTO userLoginDTO = new UserLoginDTO("o@gmail.com","123");

        HttpEntity<UserLoginDTO> requestEntity = new HttpEntity<>(userLoginDTO, headers);
        ResponseEntity<AuthResponseDTO> responseEntity = restTemplate.exchange(
                "/api/auth/logIn",
                HttpMethod.POST,
                requestEntity,
                AuthResponseDTO.class);
        this.jwt = responseEntity.getBody().getToken();
    }

    @Test
    @DisplayName("Accept Reservation not found reservation")
    public void ReservationController_AcceptReservation_NotFoundExistingReservation() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization","Bearer "+ jwt);
        HttpEntity<Object> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<Void> responseEntity = restTemplate.exchange(
                "/reservations/accept/{reservationId}",
                HttpMethod.PUT,
                requestEntity,
                Void.TYPE,
                0L);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Accept Reservation valid")
    public void ReservationController_AcceptReservation_ExistingReservation() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //headers.set("Authorization","Bearer "+ jwt);
        HttpEntity<Object> requestEntity = new HttpEntity<>(null, headers);

        ResponseEntity<OwnerReservationDTO> responseEntity = restTemplate.exchange(
                "/reservations/accept/{reservationId}",
                HttpMethod.PUT,
                requestEntity,
                OwnerReservationDTO.class,
                1L);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        OwnerReservationDTO responseOwner = responseEntity.getBody();
        assertEquals(responseOwner.getStatus(),ReservationStatus.Accepted);
    }


}
