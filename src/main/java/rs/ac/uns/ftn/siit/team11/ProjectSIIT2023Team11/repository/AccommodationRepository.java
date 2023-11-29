package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository;

import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;

import java.util.Collection;

@Repository
public class AccommodationRepository implements IAccommodationRepository {

    @Override
    public Collection<Accommodation> findAll() {
        // Implement logic to retrieve all accommodations
        return null;
    }

    @Override
    public Accommodation findById(Long id) {
        // Implement logic to find accommodation by ID
        return null;
    }

    @Override
    public Accommodation save(Accommodation accommodation) {
        // Implement logic to save a new accommodation
        return null;
    }

    @Override
    public Accommodation update(Accommodation accommodation) {
        // Implement logic to update an existing accommodation
        return null;
    }

    @Override
    public void delete(Long id) {
        // Implement logic to delete an accommodation by ID
    }

    @Override
    public Collection<Accommodation> search(String location, int guests, String startDate, String endDate) {
        // Implement logic to search for accommodations based on location, guests, start date, and end date
        return null;
    }
}