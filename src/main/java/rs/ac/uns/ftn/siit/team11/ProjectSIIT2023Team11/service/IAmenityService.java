package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Amenity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IAmenityService {

    List<Amenity> findAll();

    Optional<Amenity> findById(Long id);

    Amenity save(Amenity amenity);

    void deleteById(Long aLong);

}