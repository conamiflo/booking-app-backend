package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;

import jakarta.persistence.*;
import lombok.Data;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.NotificationType;

@Data
@Entity
@Table(name="notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    private User receiver;
    private NotificationType type;
    private String message;

}