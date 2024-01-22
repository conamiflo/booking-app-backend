package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.controller;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.http.*;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Availability;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.TimeSlot;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO.AccommodationDetailsDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO.AvailabilityDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.PriceDTO.InputPriceDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.UserDTO.AuthResponseDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.UserDTO.UserLoginDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.AccommodationStatus;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.AccommodationType;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.PriceType;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class AvailabilityControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;
    private String jwt;

    @BeforeEach
    public  void loginOwner() {
        HttpHeaders headers = new HttpHeaders();
        UserLoginDTO userLoginDTO = new UserLoginDTO("owner@gmail.com","123");

        HttpEntity<UserLoginDTO> requestEntity = new HttpEntity<>(userLoginDTO, headers);
        ResponseEntity<AuthResponseDTO> responseEntity = restTemplate.exchange(
                "/api/auth/logIn",
                HttpMethod.POST,
                requestEntity,
                AuthResponseDTO.class);
        this.jwt = responseEntity.getBody().getToken();
    }

    @Test
    @DisplayName("It should create availability for accommodation")
    public void createAvailabilityValidData() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("bearerAuth", this.jwt);

        Long startDate = LocalDateTime.of(2024, 2, 26, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long endDate = LocalDateTime.of(2024, 2, 27, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);

        AvailabilityDTO availabilityDTO = new AvailabilityDTO();
        availabilityDTO.setStartDate(startDate);
        availabilityDTO.setEndDate(endDate);

        HttpEntity<AvailabilityDTO> requestEntity = new HttpEntity<>(availabilityDTO, headers);

        ResponseEntity<Availability> responseEntity = restTemplate.exchange(
                "/api/availabilities/accommodation/{accommodation_id}",
                HttpMethod.POST,
                requestEntity,
                Availability.class,
                1
        );
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("It shouldnt create availability for non existing accommodation")
    public void createAvailabilityInvalidAccommodation() {
        // Given
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("Authorization", "Bearer " + this.jwt);

        Long startDate = LocalDateTime.of(2024, 2, 26, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long endDate = LocalDateTime.of(2024, 2, 27, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);

        AvailabilityDTO availabilityDTO = new AvailabilityDTO();
        availabilityDTO.setStartDate(startDate);
        availabilityDTO.setEndDate(endDate);

        HttpEntity<AvailabilityDTO> requestEntity = new HttpEntity<>(availabilityDTO, headers);

        ResponseEntity<Availability> responseEntity = restTemplate.exchange(
                "/api/availabilities/accommodation/{accommodation_id}",
                HttpMethod.POST,
                requestEntity,
                Availability.class,
                54
        );
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }


    @ParameterizedTest
    @MethodSource("invalidAvailabilityDatesProvider")
    @DisplayName("It shouldnt create availabilities for accommodation with invalid dates")
    public void createAvailabilityInvalidData(AvailabilityDTO availabilityDTO) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AvailabilityDTO> requestEntity = new HttpEntity<>(availabilityDTO, headers);

        ResponseEntity<Availability> responseEntity = restTemplate.exchange(
                "/api/availabilities/accommodation/{accommodation_id}",
                HttpMethod.POST,
                requestEntity,
                Availability.class,
                1
        );
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
    private static Stream<Arguments> invalidAvailabilityDatesProvider() {

        Long startDate = LocalDateTime.of(2024, 1, 29, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long endDate = LocalDateTime.of(2024, 1, 31, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);

        Long startDateBeforeToday = LocalDateTime.of(2024, 1, 21, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long endDateBeforeToday = LocalDateTime.of(2024, 1, 22, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);

        Long startDateReservation = LocalDateTime.of(2024, 2, 15, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long endDateReservation = LocalDateTime.of(2024, 2, 17, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);

        return Stream.of(
                Arguments.arguments( new AvailabilityDTO(0L, startDate,endDate)),
                Arguments.arguments( new AvailabilityDTO(0L, endDate, startDate)),
                Arguments.arguments( new AvailabilityDTO(0L, startDateReservation, endDateReservation)),
                Arguments.arguments( new AvailabilityDTO(0L, startDateBeforeToday, endDateBeforeToday)
                ));

    }


    @Test
    @DisplayName("It should create valid price for accommodation")
    public void createPriceValidData() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("bearerAuth", this.jwt);

        // ubaciti create-drop da ne pada jer je vec kreiran
        Long startDate = LocalDateTime.of(2024, 2, 27, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long endDate = LocalDateTime.of(2024, 2, 28, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);

        InputPriceDTO priceDTO = new InputPriceDTO(new TimeSlot(startDate,endDate),(double) 50);

        HttpEntity<InputPriceDTO> requestEntity = new HttpEntity<>(priceDTO, headers);

        ResponseEntity<InputPriceDTO> responseEntity = restTemplate.exchange(
                "/api/accommodations/{id}/price",
                HttpMethod.POST,
                requestEntity,
                InputPriceDTO.class,
                1

        );
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    private static Stream<Arguments> invalidPriceDatesProvider() {

        //Already has a price in that range
        Long startDate = LocalDateTime.of(2024, 2, 27, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long endDate = LocalDateTime.of(2024, 2, 28, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);

        Long startDateBeforeToday = LocalDateTime.of(2024, 1, 21, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long endDateBeforeToday = LocalDateTime.of(2024, 1, 22, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);

        Long validForPriceStart = LocalDateTime.of(2024, 3, 11, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        Long validForPriceEnd = LocalDateTime.of(2024, 3, 12, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);

        return Stream.of(
                Arguments.arguments( new InputPriceDTO(new TimeSlot(startDate,endDate),50.0)),
                Arguments.arguments( new InputPriceDTO(new TimeSlot(startDateBeforeToday,endDateBeforeToday),50.0)),
                Arguments.arguments( new InputPriceDTO(new TimeSlot(endDate,startDate),50.0)),
                Arguments.arguments( new InputPriceDTO(new TimeSlot(validForPriceStart,validForPriceEnd),0.0)),
                Arguments.arguments( new InputPriceDTO(new TimeSlot(validForPriceStart,validForPriceEnd),-5.5))
        );

    }

    @ParameterizedTest
    @MethodSource("invalidPriceDatesProvider")
    @DisplayName("It shouldnt create prices for accommodation with invalid dates and price")
    public void createPriceInvalidData(InputPriceDTO priceDTO) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<InputPriceDTO> requestEntity = new HttpEntity<>(priceDTO, headers);

        ResponseEntity<InputPriceDTO> responseEntity = restTemplate.exchange(
                "/api/accommodations/{id}/price",
                HttpMethod.POST,
                requestEntity,
                InputPriceDTO.class,
                1

        );
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }


//    @Test
//    @DisplayName("It should get availabilities for accommodation")
//    public void getAvailabilitiesForAccommodation() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
////        headers.set("Authorization", "Bearer " + this.jwt);
//        ResponseEntity<Collection<AvailabilityDTO>> responseEntity = restTemplate.exchange(
//                "/api/availabilities/available/{accommodation_id}",
//                HttpMethod.GET,
//                new HttpEntity<>(headers),
//                new ParameterizedTypeReference<Collection<AvailabilityDTO>>() {},
//                1
//        );
//        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//    }

    @Test
    @DisplayName("It should create accommodation with valid cancellation days! ")
    public void createAccommodationWithValidCancellationDays() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("bearerAuth", this.jwt);

        Long startDate = LocalDateTime.of(2024, 1, 24, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        AccommodationDetailsDTO accommodation = new AccommodationDetailsDTO(
                0L,"owner@gmail.com","Testing accommodation","","",50.0,new ArrayList<String>(),
                1,3,startDate, "PentHouse", PriceType.PerNight, AccommodationStatus.Pending, 3);

        HttpEntity<AccommodationDetailsDTO> requestEntity = new HttpEntity<>(accommodation, headers);

        ResponseEntity<AccommodationDetailsDTO> responseEntity = restTemplate.exchange(
                "/api/accommodations",
                HttpMethod.POST,
                requestEntity,
                AccommodationDetailsDTO.class
        );
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    private static Stream<Arguments> invalidCancellationDays() {

        Long startDate = LocalDateTime.of(2024, 1, 24, 0, 0, 0).toEpochSecond(ZoneOffset.UTC);
        return Stream.of(
                Arguments.arguments(new AccommodationDetailsDTO(
                        0L,"owner@gmail.com","Testing fail 1 cancellation days","","",50.0,new ArrayList<String>(),
                        1,3,startDate, "PentHouse", PriceType.PerNight, AccommodationStatus.Pending, 0)),
                Arguments.arguments(new AccommodationDetailsDTO(
                        0L,"owner@gmail.com","Testing fail 2 cancellation days","","",50.0,new ArrayList<String>(),
                        1,3,startDate, "PentHouse", PriceType.PerNight, AccommodationStatus.Pending, -5))
        );

    }

    @ParameterizedTest
    @MethodSource("invalidCancellationDays")
    @DisplayName("It shouldnt create accommodation with invalid cancellation days! ")
    public void createAccommodationWithInvalidCancellationDays(AccommodationDetailsDTO accommodation) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("bearerAuth", this.jwt);

        HttpEntity<AccommodationDetailsDTO> requestEntity = new HttpEntity<>(accommodation, headers);

        ResponseEntity<AccommodationDetailsDTO> responseEntity = restTemplate.exchange(
                "/api/accommodations",
                HttpMethod.POST,
                requestEntity,
                AccommodationDetailsDTO.class
        );
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

}
