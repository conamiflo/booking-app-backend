package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewDTO {
    private Long id;
    private String guestEmail;
    private String description;
    private int rating;
    private Long date;
    private boolean reported;
    private String ownerEmail;
    private Long accommodationId;
}
