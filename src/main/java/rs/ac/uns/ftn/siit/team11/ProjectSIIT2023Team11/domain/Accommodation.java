package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.PriceType;

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
    private String location;
    @JoinTable(name = "amenities")
    @OneToMany(cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
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



}
