package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Amenity;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IAmenityRepository;

import java.util.Collection;

@Service
public class AmenityService implements IAmenityService {

    private final IAmenityRepository amenityRepository;

    @Autowired
    public AmenityService(IAmenityRepository amenityRepository) {
        this.amenityRepository = amenityRepository;
    }

    @Override
    public Collection<Amenity> findAll() {
        return amenityRepository.findAll();
    }

    @Override
    public Amenity findById(Long id) {
        return amenityRepository.findById(id);
    }

    @Override
    public Amenity create(Amenity amenity) throws Exception {
        if (amenity.getId() != null) {
            throw new IllegalArgumentException("Amenity ID should be null for creation.");
        }

        // You can add additional validation or business logic here before saving
        return amenityRepository.save(amenity);
    }

    @Override
    public Amenity update(Amenity amenity) throws Exception {
        if (amenity.getId() == null) {
            throw new IllegalArgumentException("Amenity ID cannot be null for update.");
        }

        // Check if the amenity with the given ID exists before updating
        Amenity existingAmenity = amenityRepository.findById(amenity.getId());
        if (existingAmenity == null) {
            throw new IllegalArgumentException("Amenity with ID " + amenity.getId() + " not found.");
        }

        // You can add additional validation or business logic here before updating
        return amenityRepository.update(amenity);
    }

    @Override
    public void delete(Long id) {
        amenityRepository.delete(id);
    }
}
