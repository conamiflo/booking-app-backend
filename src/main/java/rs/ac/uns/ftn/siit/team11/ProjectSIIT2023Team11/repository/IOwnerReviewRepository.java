package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.OwnerReview;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Review;

import java.util.Collection;

@Repository
public interface IOwnerReviewRepository extends JpaRepository<OwnerReview,Long> {
    Collection<Review> findAllByOwnerEmailAndApprovedFalse(String email);
    Collection<Review> findAllByOwnerEmailAndApprovedTrue(String email);
    Collection<OwnerReview> findAllByGuestEmail(String guestEmail);

}
