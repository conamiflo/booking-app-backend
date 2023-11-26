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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long ownerId;
    private String name;
    private String description;
    private String location;
    @OneToMany(cascade = {CascadeType.ALL})
    private List<Amenity> amenities;
    @OneToMany(cascade = {CascadeType.ALL})
    private List<Price> priceList;
    @ElementCollection
    private List<String> photos;
    private int minGuests;
    private int maxGuests;
    private String type;
    private double price;
    private boolean automaticApproval;
    private boolean active;
    private LocalDate created;

}
