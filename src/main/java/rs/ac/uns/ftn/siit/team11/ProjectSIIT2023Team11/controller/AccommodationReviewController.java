package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.AccommodationReview;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReviewForShowDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.ReviewForShowMapper;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.AccommodationReviewService;

import java.util.Collection;
@RestController
@RequestMapping("/api/accommondationreviews")
public class AccommodationReviewController {


    @Autowired
    private AccommodationReviewService accommodationReviewService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ReviewForShowDTO>> getAllAccommodationReviews() {
        Collection<AccommodationReview> accommodationReviews = accommodationReviewService.findAll();
        return new ResponseEntity<>(ReviewForShowMapper.mapToAccommodationReviewsToShowDto(accommodationReviews), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewForShowDTO> getAccommodationReviewById(@PathVariable("id") int id) {
        AccommodationReview accommodationReview = accommodationReviewService.findById(id);
        if (accommodationReview == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ReviewForShowMapper.mapToReviewToShowDto(accommodationReview), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationReview> getReviewByAccommodation(@PathVariable("id") int id) {
        AccommodationReview accommodationReview = accommodationReviewService.findByAccommodation(id);
        if (accommodationReview == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accommodationReview, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationReview> createAccommodationReview(@RequestBody AccommodationReview accommodationReview) throws Exception {
        AccommodationReview newAccommodationReview = accommodationReviewService.create(accommodationReview);
        return new ResponseEntity<>(newAccommodationReview, HttpStatus.CREATED);
    }

}
