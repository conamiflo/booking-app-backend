package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.NotificationType;

@Data
@AllArgsConstructor
public class NotificationDTO {
    private Long id;
    private String receiverEmail;
    private NotificationType type;
    private String message;
}
