package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.NotificationDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO.ReservationDTO;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NotificationControllerIntegratedTest {
    @Autowired
    private TestRestTemplate restTemplate;
    private static final String GUEST_EMAIL = "guest@example.com";

    @Test
    @DisplayName("CREATE notifications - /api/notifications")
    public void ReservationController_CreateNotification_Created() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        NotificationDTO notificationDTO = NotificationDTO.builder().receiverEmail(GUEST_EMAIL).build();

        HttpEntity<NotificationDTO> requestEntity = new HttpEntity<>(notificationDTO, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange("/api/notifications",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

    }

    @Test
    @DisplayName("CREATE notifications - /api/notifications")
    public void ReservationController_CreateNotification_InvalidUser() throws Exception {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        NotificationDTO notificationDTO = NotificationDTO.builder().receiverEmail("nonexisting@gmail.com").build();

        HttpEntity<NotificationDTO> requestEntity = new HttpEntity<>(notificationDTO, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange("/api/notifications",
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {
                });

        assertTrue(responseEntity.getStatusCode().is4xxClientError());

    }

}
