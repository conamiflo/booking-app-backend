package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Amenity;

import java.util.List;
import java.util.Optional;

public interface IAmenityService {
    <S extends Amenity> S save(S entity);

    List<Amenity> findAll();

    Optional<Amenity> findById(Long aLong);

    void deleteById(Long aLong);

    void deleteAccommodationFromAmenities(Accommodation accommodation);
    void deleteAmenities( List<Amenity> amenities, Accommodation accommodation);
}
