package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class AvailabilityDTO {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
}
