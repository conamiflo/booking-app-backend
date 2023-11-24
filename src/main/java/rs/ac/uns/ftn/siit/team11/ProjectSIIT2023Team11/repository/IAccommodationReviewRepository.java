package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.AccommodationReview;

import java.util.List;

public interface IAccommodationReviewRepository extends JpaRepository<AccommodationReview,Long>{

    List<AccommodationReview> findAll();

    AccommodationReview findByAcommodation(int id);
    AccommodationReview findById(int id);

    AccommodationReview create(AccommodationReview review);

    AccommodationReview update(AccommodationReview review) throws Exception;

    void delete(int id);
}
