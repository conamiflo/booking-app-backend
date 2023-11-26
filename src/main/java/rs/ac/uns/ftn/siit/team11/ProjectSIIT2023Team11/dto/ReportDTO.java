package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.ReviewStatus;

@Data
@AllArgsConstructor
public class ReportDTO {
    private Long id;
    private String senderEmail;
    private String receiverEmail;
    private String content;
    private ReviewStatus status;
}
