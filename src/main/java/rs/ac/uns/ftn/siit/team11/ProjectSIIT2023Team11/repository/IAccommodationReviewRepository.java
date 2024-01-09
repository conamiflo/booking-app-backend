package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.AccommodationReview;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Review;

import java.util.Collection;

@Repository
public interface IAccommodationReviewRepository extends JpaRepository<AccommodationReview,Long> {
    Collection<Review> findAllByAccommondation_Id(Long id);
}
