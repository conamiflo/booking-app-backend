package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;

import jakarta.persistence.*;
import lombok.Data;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.ReviewStatus;

@Data
@Entity
@Table(name="reports")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    private User sender;
    @ManyToOne(cascade={CascadeType.ALL}, fetch = FetchType.LAZY)
    private User receiver;
    private String content;
    private ReviewStatus status;
}
