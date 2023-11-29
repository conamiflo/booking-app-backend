package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository;

import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.OwnerReview;

import java.util.Collection;

@Repository
public class OwnerReviewRepository implements IOwnerReviewRepository{
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
