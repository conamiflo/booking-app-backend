package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Owner;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO.AccommodationCardDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO.AccommodationDetailsDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO.AccommodationDetailsWithAmenitiesDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO.AccommodationPricesDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IUserService;

import java.util.ArrayList;
import java.util.Collection;

public class AccommodationMapper {
    public static AccommodationCardDTO mapToAccommodationDto(Accommodation accommodation){
        return new AccommodationCardDTO(
                accommodation.getId(),
                accommodation.getName(),
                accommodation.getDescription(),
                accommodation.getLocation(),
                accommodation.calculatePrice(),
                accommodation.getPhotos(),
                accommodation.getMinGuests(),
                accommodation.getMaxGuests()
        );
    }

    public static Collection<AccommodationCardDTO> mapToAccommodationsDto(Collection<Accommodation> accommodations){

        Collection<AccommodationCardDTO> accommodationCardDTOS = new ArrayList<>();
        for (Accommodation accommodation: accommodations){
            accommodationCardDTOS.add(mapToAccommodationDto(accommodation));
        }
        return  accommodationCardDTOS;
    }
    public static AccommodationDetailsDTO mapToAccommodationDetailsDto(Accommodation accommodation){
        return new AccommodationDetailsDTO(
                accommodation.getId(),
                accommodation.getOwner().getEmail(),
                accommodation.getName(),
                accommodation.getDescription(),
                accommodation.getLocation(),
                accommodation.calculatePrice(),
                accommodation.getPhotos(),
                accommodation.getMinGuests(),
                accommodation.getMaxGuests(),
                accommodation.getCreated(),
                accommodation.getType(),
                accommodation.getPriceType()
        );
    }
    public static Accommodation mapDetailsDtoToAccommodation(AccommodationDetailsDTO accommodationDetailsDTO, IUserService userService, Accommodation accommodation){
        accommodation.setOwner((Owner) userService.findById(accommodationDetailsDTO.ownerEmail()).get());
        accommodation.setName(accommodationDetailsDTO.name());
        accommodation.setDescription(accommodationDetailsDTO.description());
        accommodation.setLocation(accommodationDetailsDTO.location());
        accommodation.setMinGuests(accommodationDetailsDTO.minGuests());
        accommodation.setMaxGuests(accommodationDetailsDTO.maxGuests());
        accommodation.setDefaultPrice(accommodationDetailsDTO.defaultPrice());
        accommodation.setPhotos(accommodationDetailsDTO.photos());
        accommodation.setType(accommodationDetailsDTO.type());
        accommodation.setPriceType(accommodationDetailsDTO.priceType());
        return accommodation;
    }

    public static AccommodationDetailsWithAmenitiesDTO mapToAccommodationDetailsAmenityDto(Accommodation accommodation){
        return new AccommodationDetailsWithAmenitiesDTO(
                accommodation.getId(),
                accommodation.getOwner().getEmail(),
                accommodation.getName(),
                accommodation.getDescription(),
                accommodation.getLocation(),
                accommodation.calculatePrice(),
                accommodation.getPhotos(),
                accommodation.getMinGuests(),
                accommodation.getMaxGuests(),
                accommodation.getCreated(),
                accommodation.getType(),
                AmenityMapper.mapAmenitiesToAmenityOutputDTOs(accommodation.getAmenities())
        );
    }
    public static Accommodation mapDetailsAmenityDtoToAccommodation(AccommodationDetailsWithAmenitiesDTO accommodationDetailsDTO, IUserService userService, Accommodation accommodation){
        accommodation.setOwner((Owner) userService.findById(accommodationDetailsDTO.ownerEmail()).get());
        accommodation.setName(accommodationDetailsDTO.name());
        accommodation.setDescription(accommodationDetailsDTO.description());
        accommodation.setLocation(accommodationDetailsDTO.location());
        accommodation.setMinGuests(accommodationDetailsDTO.minGuests());
        accommodation.setMaxGuests(accommodationDetailsDTO.maxGuests());
        accommodation.setDefaultPrice(accommodationDetailsDTO.defaultPrice());
        accommodation.setPhotos(accommodationDetailsDTO.photos());
        accommodation.setType(accommodationDetailsDTO.type());
        return accommodation;
    }
    public static Collection<AccommodationDetailsWithAmenitiesDTO> mapToAccommodationsDetailsAmenityDto(Collection<Accommodation> accommodations){

        Collection<AccommodationDetailsWithAmenitiesDTO> accommodationDetailsDto = new ArrayList<>();
        for (Accommodation accommodation: accommodations){
            accommodationDetailsDto.add(mapToAccommodationDetailsAmenityDto(accommodation));
        }
        return  accommodationDetailsDto;
    }
    public static Collection<AccommodationDetailsDTO> mapToAccommodationsDetailsDto(Collection<Accommodation> accommodations){

        Collection<AccommodationDetailsDTO> accommodationDetailsDto = new ArrayList<>();
        for (Accommodation accommodation: accommodations){
            accommodationDetailsDto.add(mapToAccommodationDetailsDto(accommodation));
        }
        return  accommodationDetailsDto;
    }
    public static Accommodation mapDetailsDtoToNewAccommodation(AccommodationDetailsDTO accommodationDetailsDTO, Owner owner){
        Accommodation accommodation = new Accommodation();
        accommodation.setOwner(owner);
        accommodation.setName(accommodationDetailsDTO.name());
        accommodation.setDefaultPrice(accommodationDetailsDTO.defaultPrice());
        accommodation.setDescription(accommodationDetailsDTO.description());
        accommodation.setLocation(accommodationDetailsDTO.location());
        accommodation.setMinGuests(accommodationDetailsDTO.minGuests());
        accommodation.setMaxGuests(accommodationDetailsDTO.maxGuests());
        accommodation.setType(accommodationDetailsDTO.type());
        accommodation.setPhotos(accommodationDetailsDTO.photos());
        accommodation.setCreated(accommodationDetailsDTO.created());
        accommodation.setActive(false);
        accommodation.setPriceType(accommodationDetailsDTO.priceType());
        return accommodation;
    }

    public static AccommodationPricesDTO mapAccommodationToAccommodationPriceDTO(Accommodation accommodation){
        return new AccommodationPricesDTO(
                accommodation.getId(),
                accommodation.getPriceList());
    }
}
