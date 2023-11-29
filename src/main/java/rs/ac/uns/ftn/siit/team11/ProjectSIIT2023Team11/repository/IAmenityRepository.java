package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Amenity;

import java.util.Collection;

public interface IAmenityRepository {

    Collection<Amenity> findAll();

    Amenity findById(Long id);

    Amenity save(Amenity amenity);

    Amenity update(Amenity amenity);

    void delete(Long id);
}