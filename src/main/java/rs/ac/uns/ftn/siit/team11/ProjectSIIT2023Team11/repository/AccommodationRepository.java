package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository;

import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class AccommodationRepository implements IAccommodationRepository {

    private Collection<Accommodation> accommodations = new ArrayList<Accommodation>();

    public AccommodationRepository() {
        Accommodation accommodation = new Accommodation(
                1,
                "johndoe@gmail.com",
                "Cozy Apartment",
                "A comfortable apartment in the heart of the city.",
                "City Center",
                List.of("WiFi", "Air Conditioning", "Kitchen"),
                List.of("photo1.jpg", "photo2.jpg"),
                2,
                4,
                "Apartment",
                80.0,
                true,
                true,
                LocalDate.now()
        );
        accommodations.add(accommodation);
    }
    @Override
    public Collection<Accommodation> findAll() {
        return this.accommodations;
    }

    @Override
    public Accommodation findById(int id) {
        for(Accommodation accommodation : accommodations){
            if (accommodation.getId() == id){
                return accommodation;
            }
        }
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
    public void delete(int id) {
        // Implement logic to delete an accommodation by ID
    }

    @Override
    public Collection<Accommodation> search(String location, int guests, String startDate, String endDate) {
        // Implement logic to search for accommodations based on location, guests, start date, and end date
        return null;
    }
}