package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Review;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IReviewRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService implements IReviewService{
    @Autowired
    private IReviewRepository reviewRepository;


    @Override
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    @Override
    public <S extends Review> S save(S entity) {
        return reviewRepository.save(entity);
    }

    @Override
    public Optional<Review> findById(Long aLong) {
        return reviewRepository.findById(aLong);
    }

    @Override
    public void deleteById(Long aLong) {
        reviewRepository.deleteById(aLong);
    }
}
