package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Amenity;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IAccommodationRepository;

import java.util.Collection;

@Service
public class AccommodationService implements IAccommodationService {

    private final IAccommodationRepository accommodationRepository;

    @Autowired
    public AccommodationService(IAccommodationRepository accommodationRepository) {
        this.accommodationRepository = accommodationRepository;
    }

    @Override
    public Collection<Accommodation> findAll() {
        return accommodationRepository.findAll();
    }

    @Override
    public Accommodation findById(int id) {
        return accommodationRepository.findById(id);
    }

    @Override
    public Accommodation save(Accommodation accommodation) {
        return accommodationRepository.save(accommodation);
    }

    @Override
    public Accommodation update(Accommodation accommodation) {
        return accommodationRepository.update(accommodation);
    }

    @Override
    public void delete(int id) {
        accommodationRepository.delete(id);
    }

    @Override
    public Collection<Accommodation> search(String location, int guests, String startDate, String endDate) {
        return accommodationRepository.search(location, guests, startDate, endDate);
    }

    @Override
    public Accommodation create(Accommodation accommodation) {
//        if (accommodation.getId() != null) {
//            throw new IllegalArgumentException("Amenity ID should be null for creation.");
//        }
//
//        return accommodationRepository.save(accommodation);
        return null;
    }

}