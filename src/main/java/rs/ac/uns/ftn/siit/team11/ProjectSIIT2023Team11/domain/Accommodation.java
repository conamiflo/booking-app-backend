package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.PriceType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="accommodations")
@Entity
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private Owner owner;
    private String name;
    private String description;
    private Double xMapsPosition;
    private Double yMapsPosition;
    private String location;
    @JoinTable(
            name = "accommodations_amenities",
            joinColumns = @JoinColumn(name = "accommodation_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id"))
    @ManyToMany
    private List<Amenity> amenities;
    @JoinTable(name = "priceList")
    @OneToMany(cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
    private List<Price> priceList;
    @OneToMany(cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
    private List<Availability> availability;
    @ElementCollection
    private List<String> photos;
    private int minGuests;
    private int maxGuests;
    private String type;
    private Double defaultPrice;
    private boolean automaticApproval;
    private boolean active;
    private int cancelationDays;
    private LocalDate created;
    private PriceType priceType;

    public Double calculatePrice() {
        LocalDate today = LocalDate.now();
        if (priceList == null){
            priceList = new ArrayList<Price>();
        }
        for (Price price : priceList) {
            if (price.getTimeSlot().contains(today)) {
                return price.getPrice();
            }
        }
        return defaultPrice;
    }

    public Boolean containsPrice(Price price){
        for (Price accommodationPrice : priceList) {
            if (Objects.equals(price.getId(), accommodationPrice.getId())){
                return true;
            }
        }
        return false;
    }

}
