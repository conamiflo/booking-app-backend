package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Review;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IAccommodationReviewRepository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IOwnerReviewRepository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IReviewRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService implements IReviewService{
    @Autowired
    private IReviewRepository reviewRepository;

    @Autowired
    private IOwnerReviewRepository ownerReviewRepository;
    @Autowired
    private IAccommodationReviewRepository accommodationReviewRepository;

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

    @Override
    public Collection<Review> findAllApprovedByOwnerEmail(String email) {
        return ownerReviewRepository.findAllByOwnerEmailAndApprovedTrue(email);
    }
    @Override
    public Collection<Review> findAllApprovedByAccommodationId(Long id) {
        return accommodationReviewRepository.findAllByAccommondation_IdAndApprovedTrue(id);
    }

    @Override
    public Collection<Review> findAllNotApprovedByOwnerEmail(String email) {
        return ownerReviewRepository.findAllByOwnerEmailAndApprovedFalse(email);
    }

    @Override
    public Collection<Review> findAllNotApprovedByAccommodationId(Long id) {
        return accommodationReviewRepository.findAllByAccommondation_IdAndApprovedFalse(id);
    }

    @Override
    public boolean canReviewOwnerOrAccommodation(String email,String ownerEmail) {
        if(ownerEmail.equals("")){
            if (accommodationReviewRepository.findAllByGuestEmail(email).isEmpty()) {
                return true;
            }
        }else{
            if(ownerReviewRepository.findAllByGuestEmail(email).isEmpty()){
                return true;
            }
        }
        return false;
    }
}
