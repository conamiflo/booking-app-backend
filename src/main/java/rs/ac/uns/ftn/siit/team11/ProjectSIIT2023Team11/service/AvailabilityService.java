package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Availability;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Price;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IAvailabilityRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AvailabilityService implements IAvailabilityService{

    @Autowired
    IAvailabilityRepository availabilityRepository;

    public List<Availability> findAll() {
        return availabilityRepository.findAll();
    }

    public <S extends Availability> S save(S entity) {
        return availabilityRepository.save(entity);
    }

    public Optional<Availability> findById(Long aLong) {
        return availabilityRepository.findById(aLong);
    }

    public void deleteById(Long aLong) {
        availabilityRepository.deleteById(aLong);
    }

    public void deleteAvailabilities(List<Availability> availabilities){
        for (Availability availability : availabilities) {
            deleteById(availability.getId());
        }
        availabilities.clear();
    }
}
