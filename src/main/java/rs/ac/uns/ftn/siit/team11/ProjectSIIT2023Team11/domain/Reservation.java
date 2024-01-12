package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.PriceType;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.ReservationStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="reservations")
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Accommodation accommodation;
    @ManyToOne(fetch = FetchType.LAZY)
    private Guest guest;
    private Long startDate;
    private Long endDate;
    private int numberOfGuests;
    private ReservationStatus status;
    private Double price;

    public Double calculatePrice() {
        Double totalPrice = 0.0;

        Long currentDate = startDate;
        while (currentDate < endDate) {
            Double dailyPrice = findPriceForDate(currentDate);
            if (dailyPrice == null) {
                dailyPrice = accommodation.getDefaultPrice();
            }
            if(accommodation.getPriceType().equals(PriceType.PerGuest)){
                totalPrice += dailyPrice*numberOfGuests;
            }
            else {
                totalPrice += dailyPrice;
            }
            currentDate = currentDate + (1 * 24 * 60 * 60); // Move to the next day
        }
        setPrice(totalPrice);
        return totalPrice;
    }

    private Double findPriceForDate(Long date) {
        for (Price price : accommodation.getPriceList()) {
            if (price.getTimeSlot().contains(date)) {
                return price.getPrice();
            }
        }
        return null; // If no price is found for the date
    }

    public boolean isAvailable() {
        Long currentDate = startDate;
        while (currentDate < endDate) {
            boolean isAvailable = false;
            for (Availability availability : accommodation.getAvailability()) {
                if (availability.getTimeSlot().contains(currentDate)) {
                    isAvailable = true;
                    break; // No need to check other availabilities once found available
                }
            }
            if (!isAvailable) {
                return false; // If not available for any date, return false
            }
            currentDate = currentDate + (1 * 24 * 60 * 60); // Move to the next day
        }

        return true;
    }
}
