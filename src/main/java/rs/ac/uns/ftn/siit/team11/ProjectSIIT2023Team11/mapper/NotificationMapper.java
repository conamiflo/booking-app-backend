package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Notification;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.NotificationDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IUserService;

import java.util.ArrayList;
import java.util.Collection;

public class NotificationMapper {
    public static NotificationDTO mapToNotificationDTO(Notification notification){
        return new NotificationDTO(
                notification.getId(),
                notification.getReceiver().getEmail(),
                notification.getType(),
                notification.getMessage()
        );

    }
    public static Collection<NotificationDTO> mapToNotificationsDTO(Collection<Notification> notifications){
        Collection<NotificationDTO> notificationDTOs = new ArrayList<>();
        for (Notification notification: notifications){
            notificationDTOs.add(mapToNotificationDTO(notification));
        }
        return  notificationDTOs;
    }

    public static Notification mapDtoToNotification(NotificationDTO notificationDTO, IUserService userService){

        return new Notification(
                notificationDTO.getId(),
                userService.findById(notificationDTO.getReceiverEmail()).get(),
                notificationDTO.getType(),
                notificationDTO.getMessage()
        );
    }
}
