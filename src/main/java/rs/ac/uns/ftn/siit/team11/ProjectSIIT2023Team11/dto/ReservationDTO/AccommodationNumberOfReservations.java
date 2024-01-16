package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccommodationNumberOfReservations {
    private String accommodationName;
    private Integer numberOfReservations;

    public void increaseNumberOfReservations(){
        numberOfReservations++;
    }


    @Override
    public String toString() {
        return "AccommodationNumberOfReservations{" +
                "accommodationName='" + accommodationName + '\'' +
                ", numberOfReservations=" + numberOfReservations +
                '}';
    }
}
