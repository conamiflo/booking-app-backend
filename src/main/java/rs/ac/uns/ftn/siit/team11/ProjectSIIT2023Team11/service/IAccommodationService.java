package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.springframework.data.jpa.repository.Query;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Amenity;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Availability;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Price;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO.AccommodationDetailsDTO;

import java.time.LocalDate;
import java.util.Collection;
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

    Collection<Accommodation> searchAccommodationsByCriteria(Integer guests, String location, Long startDate, Long endDate);

    void deleteAvailabilityFromAllAccommodations(Availability availability);
    Collection<Accommodation> findAccommodationsByPendingStatus();
    Collection<Accommodation> findActiveAccommodations();

}
