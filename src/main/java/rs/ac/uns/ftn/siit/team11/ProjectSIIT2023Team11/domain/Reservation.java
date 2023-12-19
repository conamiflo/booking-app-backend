package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.ReservationStatus;

import java.time.LocalDate;

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
    private LocalDate startDate;
    private LocalDate endDate;
    private int numberOfGuests;
    private ReservationStatus status;
    private Double price;

    public Double calculatePrice() {
        Double totalPrice = 0.0;

        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            Double dailyPrice = findPriceForDate(currentDate);
            if (dailyPrice == null) {
                dailyPrice = accommodation.getDefaultPrice();
            }
            totalPrice += dailyPrice;
            currentDate = currentDate.plusDays(1); // Move to the next day
        }
        setPrice(totalPrice);
        return totalPrice;
    }

    private Double findPriceForDate(LocalDate date) {
        for (Price price : accommodation.getPriceList()) {
            if (price.getTimeSlot().contains(date)) {
                return price.getPrice();
            }
        }
        return null; // If no price is found for the date
    }

    public boolean isAvailable() {
        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
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
            currentDate = currentDate.plusDays(1); // Move to the next day
        }

        return true;
    }
}
