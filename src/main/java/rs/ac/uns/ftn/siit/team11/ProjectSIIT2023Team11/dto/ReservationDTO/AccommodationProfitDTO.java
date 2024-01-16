package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccommodationProfitDTO {
    private String accommodationName;
    private Double profit;
    public void IncreaseProfit(Double increase){
        profit+=increase;
    }

    @Override
    public String toString() {
        return "AccommodationProfitDTO{" +
                "accommodationName='" + accommodationName + '\'' +
                ", profit=" + profit +
                '}';
    }
}
