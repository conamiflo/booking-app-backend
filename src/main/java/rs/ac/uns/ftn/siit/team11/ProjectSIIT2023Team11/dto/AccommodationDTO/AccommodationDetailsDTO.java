package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Amenity;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AmenityOutputDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.AccommodationStatus;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.PriceType;

import java.time.LocalDate;
import java.util.List;

public record AccommodationDetailsDTO (
        Long id,
        String ownerEmail,
        String name,
        String description,
        String location,
        Double defaultPrice,
        List<String> photos,
        int minGuests,
        int maxGuests,
        Long created,
        String type,
        PriceType priceType,
        AccommodationStatus status
){

    @Override
    public PriceType priceType() {
        return priceType;
    }

    @Override
    public String type() {
        return type;
    }

    @Override
    public Long id() {
        return id;
    }

    @Override
    public String ownerEmail() {
        return ownerEmail;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public String location() {
        return location;
    }

    @Override
    public Double defaultPrice() {
        return defaultPrice;
    }

    @Override
    public List<String> photos() {
        return photos;
    }

    @Override
    public int minGuests() {
        return minGuests;
    }

    @Override
    public int maxGuests() {
        return maxGuests;
    }

    @Override
    public Long created() {
        return created;
    }

    @Override
    public AccommodationStatus status() {
        return status;
    }
}
