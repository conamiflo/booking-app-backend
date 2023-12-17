package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Amenity;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Price;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO.AccommodationDetailsDTO;

import java.util.List;
import java.util.Optional;

public interface IAccommodationService {
    <S extends Accommodation> S save(S entity);

    List<Accommodation> findAll();

    Optional<Accommodation> findById(Long aLong);

    void deleteById(Long aLong);

    Optional<AccommodationDetailsDTO> create(AccommodationDetailsDTO accommodation, IUserService userService);

    void deletePriceFromAllAccommodations(Price price);

    List<Accommodation> findByOwnersId(String email);

    void deleteAccommodations(List<Accommodation> ownersAccommodations);

    void deleteAmenityFromAccommodations(Amenity existingAmenity);
}
