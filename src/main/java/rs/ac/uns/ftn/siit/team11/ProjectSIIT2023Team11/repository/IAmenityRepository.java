package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Amenity;

import java.util.List;
import java.util.Optional;

public interface IAmenityRepository extends JpaRepository<Amenity,Long> {

    List<Amenity> findAll();

    Optional<Amenity> findById(Long id);

    Amenity save(Amenity amenity);

    Amenity update(Amenity amenity);

    void delete(Long id);
}