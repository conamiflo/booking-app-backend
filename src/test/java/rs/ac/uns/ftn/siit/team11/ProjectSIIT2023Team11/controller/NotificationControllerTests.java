package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.*;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.NotificationDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.security.JwtTokenUtil;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(NotificationController.class)
@ExtendWith({SpringExtension.class, MockitoExtension.class})
@AutoConfigureMockMvc(addFilters = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NotificationControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private NotificationService notificationService;
    @MockBean
    private UserService userService;
    @MockBean
    private ReservationService reservationService;
    @MockBean
    private AccommodationService accommodationService;
    @MockBean
    private AvailabilityService availabilityService;
    @MockBean
    private SimpMessagingTemplate simpMessagingTemplate;
    @MockBean
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    ObjectMapper objectMapper;


    private static final String INVALID_EMAIL = "invalid@gmail.com";
    private static final String VALID_EMAIL = "valid@gmail.com";

    @BeforeEach
    public void init(){
        when(userService.findById(INVALID_EMAIL)).thenReturn(Optional.empty());
        when(userService.findById(VALID_EMAIL)).thenReturn(Optional.of(User.builder().email(VALID_EMAIL).build()));

    }


    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    public void NotificationController_CreateNotification_NonExistingUser() throws Exception{

        NotificationDTO notificationDTO = NotificationDTO.builder().receiverEmail(INVALID_EMAIL).build();


        ResultActions response = mockMvc.perform(post("/api/notifications")
                .contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8")
                .content(objectMapper.writeValueAsString(notificationDTO)));


        response.andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    public void NotificationController_CreateNotification_ExistingUser() throws Exception{

        NotificationDTO notificationDTO = NotificationDTO.builder().receiverEmail(VALID_EMAIL).build();


        ResultActions response = mockMvc.perform(post("/api/notifications")
                .contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8")
                .content(objectMapper.writeValueAsString(notificationDTO)));


        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    public void NotificationController_CreateNotification_ExceptionInMessaging() throws Exception {
        // Arrange
        NotificationDTO notificationDTO = NotificationDTO.builder().receiverEmail(VALID_EMAIL).build();
        when(userService.findById(any())).thenReturn(Optional.of(User.builder().email(VALID_EMAIL).build()));

        doThrow(new RuntimeException("Messaging error")).when(simpMessagingTemplate).convertAndSend("socket-publisher/"+notificationDTO.getReceiverEmail(),notificationDTO);

//        doAnswer((i) -> {
//            System.out.println("Employee setName Argument = " + i.getArgument(0));
//            assertEquals("Pankaj", i.getArgument(0));
//            return null;
//        }).when(simpMessagingTemplate).convertAndSend(any());


        // Act
        ResultActions response = mockMvc.perform(post("/api/notifications")
                .contentType(MediaType.APPLICATION_JSON + ";charset=UTF-8")
                .content(objectMapper.writeValueAsString(notificationDTO)));

        // Assert
        response.andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}
