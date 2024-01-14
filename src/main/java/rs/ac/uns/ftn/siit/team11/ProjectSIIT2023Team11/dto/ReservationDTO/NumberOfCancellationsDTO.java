package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NumberOfCancellationsDTO {
    private String guestId;
    private Integer numberOfCancellations;
}
