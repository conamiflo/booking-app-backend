package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Month;
import java.time.Year;

@Data
@AllArgsConstructor
public class AnalyticDTO {
    private Long id;
    private Long accommodationId;
    private Month month;

    private Year year;
    private int numberOfReservations;
    private float profit;
}
