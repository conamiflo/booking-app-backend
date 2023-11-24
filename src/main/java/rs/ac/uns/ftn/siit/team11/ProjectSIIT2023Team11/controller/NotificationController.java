package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Notification;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.NotificationForSendDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.NotificationForSendMapper;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.NotificationService;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<NotificationForSendDTO>> getNotifications() {
        Collection<NotificationForSendDTO> notifications = NotificationForSendMapper.mapToNotificationsForSendDto(notificationService.findAll());
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NotificationForSendDTO> getNotificationById(@PathVariable("id") Long id) {
        Optional<Notification> notification = notificationService.findById(id);
        if (notification.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(NotificationForSendMapper.mapToNotificationForSendDto(notification.get()), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) throws Exception {
        Notification newNotification = notificationService.save(notification);
        return new ResponseEntity<>(newNotification, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Notification> updateNotification(@RequestBody Notification notification, @PathVariable Long id) throws Exception {
        Optional<Notification> existingNotification = notificationService.findById(id);
        if (existingNotification.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Notification updatedNotification = notificationService.save(existingNotification.get());
        return new ResponseEntity<>(updatedNotification, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable("id") Long id) {
        notificationService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Notification>> searchNotifications(@RequestParam("receiver") String receiver
                                                                           , @RequestParam("type") String type) {
        Collection<Notification> foundNotifications = notificationService.search(receiver, type);
        return new ResponseEntity<>(foundNotifications, HttpStatus.OK);
    }
}