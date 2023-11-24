package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Amenity;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IAmenityRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class AmenityService implements IAmenityService {


    @Autowired
    IAmenityRepository amenityRepository;

    @Override
    public List<Amenity> findAll() {
        return amenityRepository.findAll();
    }

    @Override
    public Optional<Amenity> findById(Long id) {
        return amenityRepository.findById(id);
    }

    public Amenity save(Amenity amenity) {
        return amenityRepository.save(amenity);
    }

    public void deleteById(Long aLong) {
        amenityRepository.deleteById(aLong);
    }
}
