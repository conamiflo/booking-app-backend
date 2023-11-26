package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.User;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IAccommodationRepository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IUserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AccommodationService implements IAccommodationService{

    @Autowired
    private IAccommodationRepository accommodationRepository;
    public <S extends Accommodation> S save(S entity) {
        return accommodationRepository.save(entity);
    }

    public List<Accommodation> findAll() {
        return accommodationRepository.findAll();
    }

    public Optional<Accommodation> findById(Long aLong) {
        return accommodationRepository.findById(aLong);
    }

    public void deleteById(Long aLong) {
        accommodationRepository.deleteById(aLong);
    }


}
