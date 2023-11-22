package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Review;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationForShowDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReviewForShowDTO;

import java.util.ArrayList;
import java.util.Collection;

public class ReviewForShowMapper {
    public static ReviewForShowDTO mapToReviewToShowDto(Review review){
        return new ReviewForShowDTO(
                review.getGuest(),
                review.getDescription(),
                review.getRating(),
                review.getDate()

        );
    }

    public static Collection<ReviewForShowDTO> mapToReviewsToShowDto(Collection<Review> reviews) {
        Collection<ReviewForShowDTO> reviewsForShow = new ArrayList<>();
        for (Review review: reviews){
            reviewsForShow.add(mapToReviewToShowDto(review));
        }
        return  reviewsForShow;

    }
}
