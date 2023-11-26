package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;

import java.time.LocalDate;
import java.util.List;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @ManyToOne(cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
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
    private Double price;
    private boolean automaticApproval;
    private boolean active;
    private int cancelationDays;
    private LocalDate created;

}
