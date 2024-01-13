package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Review;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReviewDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.AccommodationMapper;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.ReviewMapper;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IAccommodationService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IReservationService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IReviewService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IUserService;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/reviews")
public class ReviewController {
    @Autowired
    private IReviewService reviewService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IAccommodationService accommodationService;

    @Autowired
    IReservationService reservationService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ReviewDTO>> getReviews() {
        Collection<Review> reviews = reviewService.findAll();
        return new ResponseEntity<Collection<ReviewDTO>>(ReviewMapper.mapToReviewsDTO(reviews), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable("id") Long id) {
        Optional<Review> review = reviewService.findById(id);
        if (review.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ReviewMapper.mapToReviewDTO(review.get()), HttpStatus.OK);
    }

    @GetMapping(value = "/owner/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ReviewDTO>> getApprovedReviewsByOwnerEmail(@PathVariable("email") String email) {
        Collection<Review> reviews = reviewService.findAllApprovedByOwnerEmail(email);
        if (reviews.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Collection<ReviewDTO> reviewDTOs = reviews.stream()
                .map(ReviewMapper::mapToReviewDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(reviewDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/accommodation/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ReviewDTO>> getApprovedReviewsByAccommodationId(@PathVariable("id") Long id) {
        Collection<Review> reviews = reviewService.findAllApprovedByAccommodationId(id);
        if (reviews.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Collection<ReviewDTO> reviewDTOs = reviews.stream()
                .map(ReviewMapper::mapToReviewDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(reviewDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/owner/notapproved/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ReviewDTO>> getNotApprovedReviewsByOwnerEmail(@PathVariable("email") String email) {
        Collection<Review> reviews = reviewService.findAllNotApprovedByOwnerEmail(email);
        if (reviews.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Collection<ReviewDTO> reviewDTOs = reviews.stream()
                .map(ReviewMapper::mapToReviewDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(reviewDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/accommodation/notapproved/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ReviewDTO>> getNotApprovedReviewsByAccommodationId(@PathVariable("id") Long id) {
        Collection<Review> reviews = reviewService.findAllNotApprovedByAccommodationId(id);
        if (reviews.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Collection<ReviewDTO> reviewDTOs = reviews.stream()
                .map(ReviewMapper::mapToReviewDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(reviewDTOs, HttpStatus.OK);
    }
    @PreAuthorize("hasAuthority('ROLE_Guest')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createReview(@RequestBody ReviewDTO reviewDTO) throws Exception {
        if(!reviewService.canReviewOwnerOrAccommodation(reviewDTO.getGuestEmail(),reviewDTO.getOwnerEmail())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You already sent a review!");
        }
        else if(!reviewDTO.getOwnerEmail().equals("") && !reservationService.hasUnCancelledReservation(reviewDTO.getGuestEmail(),reviewDTO.getOwnerEmail())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You can't review owner without having reservations in his apartments! ");
        }
        else if (reviewDTO.getOwnerEmail().equals("") && !reservationService.canReviewAccommodation(reviewDTO.getGuestEmail(),reviewDTO.getAccommodationId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You don't have reservations in the past 7 days! ");
        }
        try{
            reviewService.save(ReviewMapper.mapDtoToReview(reviewDTO, userService, accommodationService));
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception exception){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewDTO> updateReview(@RequestBody ReviewDTO reviewDTO, @PathVariable Long id) throws Exception {
        Optional<Review> existingReview = reviewService.findById(id);
        if (existingReview.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Review updatedReview = reviewService.save(ReviewMapper.mapDtoToReview(reviewDTO, userService, accommodationService));
        return new ResponseEntity<>(ReviewMapper.mapToReviewDTO(updatedReview), HttpStatus.OK);
    }


    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable("id") Long id) {
        reviewService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
