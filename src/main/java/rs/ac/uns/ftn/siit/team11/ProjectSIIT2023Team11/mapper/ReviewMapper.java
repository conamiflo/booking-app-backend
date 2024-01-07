package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.AccommodationReview;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.OwnerReview;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Review;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReviewDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IAccommodationService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IUserService;

import java.util.ArrayList;
import java.util.Collection;

public class ReviewMapper {
    public static ReviewDTO mapToReviewDTO(Review review){
        if(review instanceof AccommodationReview){
            return new ReviewDTO(
                    review.getId(),
                    review.getGuest().getEmail(),
                    review.getDescription(),
                    review.getRating(),
                    review.getDate(),
                    review.isReported(),
                    null,
                    ((AccommodationReview) review).getAccommondation().getId(),
                    review.isApproved()
            );
        }else{
            return new ReviewDTO(
                    review.getId(),
                    review.getGuest().getEmail(),
                    review.getDescription(),
                    review.getRating(),
                    review.getDate(),
                    review.isReported(),
                    ((OwnerReview) review).getOwner().getEmail(),
                    null,
                    review.isApproved()
            );
        }
    }

    public static Collection<ReviewDTO> mapToReviewsDTO(Collection<Review> reviews){
        Collection<ReviewDTO> reviewDTOs = new ArrayList<>();
        for (Review review: reviews){
            reviewDTOs.add(mapToReviewDTO(review));
        }
        return  reviewDTOs;
    }

    public static Review mapDtoToReview(ReviewDTO reviewDTO, IUserService userService, IAccommodationService accommodationService){

        if(reviewDTO.getAccommodationId() == null){
            return new OwnerReview(
                    reviewDTO.getId(),
                    userService.findById(reviewDTO.getGuestEmail()).get(),
                    reviewDTO.getDescription(),
                    reviewDTO.getRating(),
                    reviewDTO.getDate(),
                    reviewDTO.isReported(),
                    reviewDTO.isApproved(),
                    userService.findById(reviewDTO.getOwnerEmail()).get()
            );
        }else{
            return new AccommodationReview(
                    reviewDTO.getId(),
                    userService.findById(reviewDTO.getGuestEmail()).get(),
                    reviewDTO.getDescription(),
                    reviewDTO.getRating(),
                    reviewDTO.getDate(),
                    reviewDTO.isReported(),
                    reviewDTO.isApproved(),
                    accommodationService.findById(reviewDTO.getAccommodationId()).get()
            );
        }
    }
}
