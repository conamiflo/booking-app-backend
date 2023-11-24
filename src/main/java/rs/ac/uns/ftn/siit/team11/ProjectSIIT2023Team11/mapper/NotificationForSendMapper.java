package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Notification;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.NotificationForSendDTO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public class NotificationForSendMapper {
    public static NotificationForSendDTO mapToNotificationForSendDto(Notification notification){
        return new NotificationForSendDTO(
                notification.getReceiver(),
                notification.getType(),
                notification.getMessage()
        );
    }

    public static Collection<NotificationForSendDTO> mapToNotificationsForSendDto(Collection<Notification> notifications) {
        Collection<NotificationForSendDTO> accommodationsForShow = new ArrayList<>();
        for (Notification notification: notifications){
            accommodationsForShow.add(mapToNotificationForSendDto(notification));
        }
        return  accommodationsForShow;

    }

}
