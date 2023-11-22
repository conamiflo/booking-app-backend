package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.OwnerReview;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.OwnerReviewRepository;

import java.util.Collection;

@Service
public class OwnerReviewService implements IOwnerReviewService{

    @Autowired
    private OwnerReviewRepository ownerReviewRepository;
    @Override
    public Collection<OwnerReview> findAll() {
        return null;
    }

    @Override
    public OwnerReview findById(int id) {
        return null;
    }

    @Override
    public OwnerReview findByOwner(String email) {
        return null;
    }

    @Override
    public OwnerReview create(OwnerReview review) {
        return null;
    }

    @Override
    public OwnerReview update(OwnerReview review) throws Exception {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
