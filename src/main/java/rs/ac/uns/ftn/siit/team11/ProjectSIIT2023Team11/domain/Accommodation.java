package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tbl_accomondations")
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private User owner;
    private String name;
    private String description;
    private String location;
    private List<String> amenities;
    private List<String> photos;
    private int minGuests;
    private int maxGuests;
    private String type;
    private double price;
    private boolean automaticApproval;
    private boolean active;
    private LocalDate created;

}
