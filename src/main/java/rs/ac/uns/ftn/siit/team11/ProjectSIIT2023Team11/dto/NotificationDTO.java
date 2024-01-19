package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto;

import lombok.*;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.NotificationType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificationDTO {
    private Long id;
    private String receiverEmail;
    private NotificationType type;
    private String message;
}
