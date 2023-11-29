package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto;

import java.util.Date;
import java.util.List;

public class AccommodationForShowDTO {

    private String name;
    private String description;
    private String location;
    private List<String> amenities;
    private int minGuests;
    private int maxGuests;
    private String type;
    private double price;
    private Date created;

    public AccommodationForShowDTO(String name, String description, String location, List<String> amenities, int minGuests, int maxGuests, String type, double price, Date created) {
        this.name = name;
        this.description = description;
        this.location = location;
        this.amenities = amenities;
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
        this.type = type;
        this.price = price;
        this.created = created;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<String> amenities) {
        this.amenities = amenities;
    }

    public int getMinGuests() {
        return minGuests;
    }

    public void setMinGuests(int minGuests) {
        this.minGuests = minGuests;
    }

    public int getMaxGuests() {
        return maxGuests;
    }

    public void setMaxGuests(int maxGuests) {
        this.maxGuests = maxGuests;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
