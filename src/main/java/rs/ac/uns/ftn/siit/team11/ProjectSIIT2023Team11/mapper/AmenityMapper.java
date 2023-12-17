package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Amenity;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AmenityInputDTO;

import java.util.ArrayList;

public class AmenityMapper {
    public static Amenity mapAmenityInputDTOToAmenity(AmenityInputDTO amenityInputDTO){
        return new Amenity(0L,amenityInputDTO.amenityName(),new ArrayList<Accommodation>());
    }



}
