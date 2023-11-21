package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Amenity;

import java.util.Collection;

public interface IAmenityService {

    Collection<Amenity> findAll();

    Amenity findById(Long id);

    Amenity create(Amenity amenity) throws Exception;

    Amenity update(Amenity amenity) throws Exception;

    void delete(Long id);

}