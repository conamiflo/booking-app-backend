package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Amenity;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IAccommodationRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class AccommodationService implements IAccommodationService {

    @Autowired
    private IAccommodationRepository accommodationRepository;

    public List<Accommodation> findAll() {
        return new ArrayList<Accommodation>();
        //return accommodationRepository.findAll();
    }

    public Optional<Accommodation> findById(Long id) {
        return accommodationRepository.findById(id);
    }

    public void deleteById(Long id) {
        accommodationRepository.deleteById(id);
    }

    public void delete(Accommodation entity) {
        accommodationRepository.delete(entity);
    }


    @Override
    public Collection<Accommodation> search(String location, int guests, String startDate, String endDate) {
        return null;
    }

    public <S extends Accommodation> S save(S entity) {
        return accommodationRepository.save(entity);
    }
}