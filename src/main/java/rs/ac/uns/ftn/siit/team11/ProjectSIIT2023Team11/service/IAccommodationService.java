package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.springframework.beans.factory.annotation.Autowired;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Amenity;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IAccommodationRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IAccommodationService {

    List<Accommodation> findAll();
    Optional<Accommodation> findById(Long id);
    void deleteById(Long id);
    void delete(Accommodation entity);
    Collection<Accommodation> search(String location, int guests, String startDate, String endDate);
    public <S extends Accommodation> S save(S entity);
}