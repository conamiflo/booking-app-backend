package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;

import java.util.Date;
import java.util.List;

public class Accommodation {
    private Long id;
    private Long ownerId;
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
    private Date created;
}
