package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AmenityOutputDTO;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public record AccommodationDetailsWithAmenitiesDTO (
        Long id,
        String ownerEmail,
        String name,
        String description,
        String location,
        Double defaultPrice,
        List<String> photos,
        int minGuests,
        int maxGuests,
        LocalDate created,
        String type,
        Collection<AmenityOutputDTO> amenities
){
    @Override
    public Collection<AmenityOutputDTO> amenities() {
        return amenities;
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
    public LocalDate created() {
        return created;
    }
}
