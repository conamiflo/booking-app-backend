package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;

import java.util.Collection;

public interface IAccommodationService {

    Collection<Accommodation> findAll();

    Accommodation findById(Long id);

    Accommodation save(Accommodation accommodation);

    Accommodation update(Accommodation accommodation);

    void delete(Long id);

    Collection<Accommodation> search(String location, int guests, String startDate, String endDate);

}