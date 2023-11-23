package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Amenity;

import java.util.Collection;

public interface IAccommodationService {

    Collection<Accommodation> findAll();

    Accommodation findById(int id);

    Accommodation save(Accommodation accommodation);

    Accommodation update(Accommodation accommodation);

    void delete(int id);

    Collection<Accommodation> search(String location, int guests, String startDate, String endDate);
    Accommodation create(Accommodation accommodation);
}