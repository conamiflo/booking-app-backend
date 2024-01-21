package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Notification;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.NotificationDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.NotificationMapper;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.INotificationService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IUserService;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/notifications")
public class NotificationController {
    @Autowired
    private INotificationService notificationService;

    @Autowired
    private IUserService userService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<NotificationDTO>> getNotifications() {
        Collection<Notification> notifications = notificationService.findAll();
        return new ResponseEntity<>(NotificationMapper.mapToNotificationsDTO(notifications), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NotificationDTO> getNotificationById(@PathVariable("id") Long id) {
        Optional<Notification> notification = notificationService.findById(id);
        if (notification.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(NotificationMapper.mapToNotificationDTO(notification.get()), HttpStatus.OK);
    }

    @GetMapping(value = "/user/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<NotificationDTO>> getNotificationByUserEmail(@PathVariable("email") String email) {
        List<Notification> notifications = notificationService.findAllUsersNotifications(email);

        return new ResponseEntity<>(NotificationMapper.mapToNotificationsDTO(notifications), HttpStatus.OK);
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NotificationDTO> createNotification(@RequestBody NotificationDTO notificationDTO){

        if (userService.findById(notificationDTO.getReceiverEmail()).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try{
            notificationService.save(NotificationMapper.mapDtoToNotification(notificationDTO, userService));
            simpMessagingTemplate.convertAndSend("socket-publisher/"+notificationDTO.getReceiverEmail(),notificationDTO);

            return new ResponseEntity<>(notificationDTO, HttpStatus.CREATED);
        } catch (Exception exception){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NotificationDTO> updateNotification(@RequestBody NotificationDTO notificationDTO, @PathVariable Long id) throws Exception {
        Optional<Notification> existingNotification = notificationService.findById(id);
        if (existingNotification.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Notification updatedNotification = notificationService.save(NotificationMapper.mapDtoToNotification(notificationDTO, userService));
        return new ResponseEntity<>(NotificationMapper.mapToNotificationDTO(updatedNotification), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable("id") Long id) {
        notificationService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
