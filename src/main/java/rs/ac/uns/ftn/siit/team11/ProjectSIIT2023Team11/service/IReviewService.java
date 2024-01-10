package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Review;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IReviewService {
    List<Review> findAll();

    <S extends Review> S save(S entity);

    Optional<Review> findById(Long aLong);

    void deleteById(Long aLong);
    Collection<Review> findAllApprovedByOwnerEmail(String email);
    Collection<Review> findAllApprovedByAccommodationId(Long id);

    Collection<Review> findAllNotApprovedByOwnerEmail(String email);
    Collection<Review> findAllNotApprovedByAccommodationId(Long id);

    boolean canReviewOwnerOrAccommodation(String email,String ownerEmail);

}
