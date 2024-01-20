package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;

import jakarta.persistence.*;
import lombok.*;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.AccommodationStatus;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.PriceType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Getter
@Setter
@Builder
@NoArgsConstructor
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
    private Long created;
    private PriceType priceType;
    private AccommodationStatus status;

    public Accommodation(Long id, Owner owner, String name, String description, String location, List<Amenity> amenities, List<Price> priceList, List<Availability> availability, List<String> photos, int minGuests, int maxGuests, String type, Double defaultPrice, boolean automaticApproval, boolean active, int cancelationDays, Long created, PriceType priceType, AccommodationStatus status) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.description = description;
        this.location = location;
        this.amenities = amenities;
        this.priceList = priceList;
        this.availability = availability;
        this.photos = photos;
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
        this.type = type;
        this.defaultPrice = defaultPrice;
        this.automaticApproval = automaticApproval;
        this.active = active;
        this.cancelationDays = cancelationDays;
        this.created = created;
        this.priceType = priceType;
        this.status = AccommodationStatus.Pending;
    }

    public Double calculatePrice() {
        Long today = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
        if (priceList == null){
            priceList = new ArrayList<Price>();
        }else{
            for (Price price : priceList) {
                if (price.getTimeSlot().contains(today)) {
                    return price.getPrice();
                }
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

    public boolean AddAvailability(TimeSlot newAvailability) {
        for (Availability availability :
                availability) {
            if(newAvailability.overlapsWith(availability.getTimeSlot())) return true;
        }

        return false;
    }
}
