package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationForShowDTO;

import java.util.ArrayList;
import java.util.Collection;

public class AccommodationForShowMapper {
    public static AccommodationForShowDTO mapToAccommodationForShowDto(Accommodation accommodation){
        return new AccommodationForShowDTO(
                accommodation.getName(),
                accommodation.getDescription(),
                accommodation.getLocation(),
                accommodation.getAmenities(),
                accommodation.getMinGuests(),
                accommodation.getMaxGuests(),
                accommodation.getType(),
                accommodation.getPrice(),
                accommodation.getCreated()
        );
    }

    public static Collection<AccommodationForShowDTO> mapToAccommodationsForShowDto(Collection<Accommodation> accommodations) {
        Collection<AccommodationForShowDTO> accommodationsForShow = new ArrayList<>();
        for (Accommodation accommodation: accommodations){
            accommodationsForShow.add(mapToAccommodationForShowDto(accommodation));
        }
        return  accommodationsForShow;

    }

}
