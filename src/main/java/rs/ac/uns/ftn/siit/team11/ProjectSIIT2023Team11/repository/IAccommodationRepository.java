package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;

import java.util.Collection;
import java.util.List;
@Repository
public interface IAccommodationRepository extends JpaRepository<Accommodation,Long> {
    Collection<Accommodation> search(String location, int guests, String startDate, String endDate);
}