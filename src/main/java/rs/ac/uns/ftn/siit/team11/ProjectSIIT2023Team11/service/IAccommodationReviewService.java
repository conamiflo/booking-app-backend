package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.AccommodationReview;

import java.util.Collection;

public interface IAccommodationReviewService {
    Collection<AccommodationReview> findAll();

    AccommodationReview findByAccommodation(int id);
    AccommodationReview findById(int id);

    AccommodationReview create(AccommodationReview review);

    AccommodationReview update(AccommodationReview review) throws Exception;

    void delete(int id);
}
