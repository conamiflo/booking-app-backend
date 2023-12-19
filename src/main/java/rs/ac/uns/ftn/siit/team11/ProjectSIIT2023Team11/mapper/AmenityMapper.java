package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Amenity;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AmenityInputDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AmenityOutputDTO;

import java.util.ArrayList;
import java.util.Collection;


public class AmenityMapper {
    public static Amenity mapAmenityInputDTOToAmenity(AmenityInputDTO amenityInputDTO){
        return new Amenity(0L,amenityInputDTO.amenityName(),new ArrayList<Accommodation>());
    }
    public static Collection<AmenityOutputDTO> mapAmenitiesToAmenityOutputDTOs(Collection<Amenity> amenities){
        Collection<AmenityOutputDTO> amenityOutputDTOS = new ArrayList<>();
        for (Amenity amenity :
                amenities) {
            amenityOutputDTOS.add(new AmenityOutputDTO(amenity.getId(),amenity.getName()));
        }

        return amenityOutputDTOS;
    }

    public static AmenityOutputDTO mapAmenityToAmenityOutputDTO(Amenity amenity){
        return new AmenityOutputDTO(
                amenity.getId(),
                amenity.getName());
    }
}
