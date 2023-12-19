package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.PriceType;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="accommodation_requests")
@Entity
public class AccommodationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private Owner owner;
    private String name;
    private String description;
    private String location;
    @JoinTable(
            name = "accommodations_amenities_request",
            joinColumns = @JoinColumn(name = "accommodation_id_request"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id_request"))
    @ManyToMany
    private List<Amenity> amenities;
    @JoinTable(name = "priceList_request")
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
    private int cancelationDays;
    private LocalDate created;
    private PriceType priceType;
    private String creationType;
    private Long editedAccommodation;

    public AccommodationRequest(String creationType, Long editedAccommodation) {
        this.creationType = creationType;
        this.editedAccommodation = editedAccommodation;
    }
}
