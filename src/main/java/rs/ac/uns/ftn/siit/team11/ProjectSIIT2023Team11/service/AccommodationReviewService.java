package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.AccommodationReview;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IAccommodationReviewRepository;

import java.util.Collection;

@Service
public class AccommodationReviewService implements IAccommodationReviewService {

    @Autowired
    private IAccommodationReviewRepository accommodationReviewRepository;
    @Override
    public Collection<AccommodationReview> findAll() {
        return null;
    }

    @Override
    public AccommodationReview findByAccommodation(int id) {
        return null;
    }

    @Override
    public AccommodationReview findById(int id) {
        return null;
    }

    @Override
    public AccommodationReview create(AccommodationReview review) {
        return null;
    }

    @Override
    public AccommodationReview update(AccommodationReview review) throws Exception {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
