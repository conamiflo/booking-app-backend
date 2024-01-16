package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccommodationYearlyProfitDTO {
    private String accommodationName;
    private List<Double> monthlyProfits;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Accommodation: ").append(accommodationName).append("    ");

        List<String> months = List.of("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec");

        for (int i = 0; i < months.size(); i++) {
            builder.append(months.get(i)).append(": ").append(monthlyProfits.get(i)).append("$ ");
        }

        return builder.toString();
    }
}
