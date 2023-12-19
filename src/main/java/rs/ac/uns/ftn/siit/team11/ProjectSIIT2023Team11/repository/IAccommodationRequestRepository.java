package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.AccommodationRequest;

@Service
public interface IAccommodationRequestRepository extends JpaRepository<AccommodationRequest,Long> {


}
