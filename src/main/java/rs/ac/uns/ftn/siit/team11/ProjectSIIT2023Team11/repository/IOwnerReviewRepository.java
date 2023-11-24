package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.OwnerReview;

import java.util.List;

public interface IOwnerReviewRepository extends JpaRepository<OwnerReview,Long> {

    List<OwnerReview> findAll();

    OwnerReview findById(int id);

    OwnerReview findByOwner(String email);
    OwnerReview create(OwnerReview review);

    OwnerReview update(OwnerReview review) throws Exception;

    void delete(int id);
}
