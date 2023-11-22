package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.OwnerReview;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.OwnerReviewService;

import java.util.Collection;

@RestController
@RequestMapping("/api/ownerreviews")
public class OwnerReviewController {

    @Autowired
    private OwnerReviewService ownerReviewService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<OwnerReview>> getAllOwnerReviews() {
        Collection<OwnerReview> ownerReviews = ownerReviewService.findAll();
        return new ResponseEntity<>(ownerReviews, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OwnerReview> getOwnerReviewById(@PathVariable("id") int id) {
        OwnerReview ownerReview = ownerReviewService.findById(id);
        if (ownerReview == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ownerReview, HttpStatus.OK);
    }

    @GetMapping(value = "/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OwnerReview> getReviewByOwner(@PathVariable("email") String email) {
        OwnerReview ownerReview = ownerReviewService.findByOwner(email);
        if (ownerReview == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ownerReview, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OwnerReview> createOwnerReview(@RequestBody OwnerReview ownerReview) throws Exception {
        OwnerReview newOwnerReview = ownerReviewService.create(ownerReview);
        return new ResponseEntity<>(newOwnerReview, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteOwnerReview(@PathVariable("id") int id) {
        OwnerReview ownerReview = ownerReviewService.findById(id);
        if (ownerReview == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ownerReviewService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
