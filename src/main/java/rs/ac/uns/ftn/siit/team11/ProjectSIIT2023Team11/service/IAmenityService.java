package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Amenity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IAmenityService {

    public List<Amenity> findAll();

    public Optional<Amenity> findById(Long id);

    public Amenity save(Amenity amenity);

    public void deleteById(Long aLong);

}