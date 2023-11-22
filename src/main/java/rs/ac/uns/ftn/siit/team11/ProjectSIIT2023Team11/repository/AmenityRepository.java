package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository;

import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Amenity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class AmenityRepository implements IAmenityRepository {

    private final Map<Long, Amenity> amenities = new HashMap<>();
    private long nextId = 1;

    @Override
    public Collection<Amenity> findAll() {
        return amenities.values();
    }

    @Override
    public Amenity findById(Long id) {
        return amenities.get(id);
    }

    @Override
    public Amenity save(Amenity amenity) {
        if (amenity.getId() == null) {
            // If the amenity doesn't have an ID, assign a new one
            amenity.setId(nextId);
            nextId++;
        }
        amenities.put(amenity.getId(), amenity);
        return amenity;
    }

    @Override
    public Amenity update(Amenity amenity) {
        if (amenities.containsKey(amenity.getId())) {
            // If the amenity with the given ID exists, update it
            amenities.put(amenity.getId(), amenity);
            return amenity;
        }
        // If the amenity with the given ID doesn't exist, you might want to handle this case
        return null;
    }

    @Override
    public void delete(Long id) {
        amenities.remove(id);
    }
}
