package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.OwnerReview;

import java.util.Collection;

public interface IOwnerReviewRepository {

    Collection<OwnerReview> findAll();

    OwnerReview findById(int id);

    OwnerReview findByOwner(String email);
    OwnerReview create(OwnerReview review);

    OwnerReview update(OwnerReview review) throws Exception;

    void delete(int id);
}
