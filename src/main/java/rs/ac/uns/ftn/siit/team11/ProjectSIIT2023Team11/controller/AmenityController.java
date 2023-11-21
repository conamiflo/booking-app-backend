package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Amenity;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IAmenityService;

import java.util.Collection;

@RestController
@RequestMapping("/api/amenities")
public class AmenityController {

    private final IAmenityService amenityService;

    @Autowired
    public AmenityController(IAmenityService amenityService) {
        this.amenityService = amenityService;
    }

    @GetMapping
    public ResponseEntity<Collection<Amenity>> getAllAmenities() {
        Collection<Amenity> amenities = amenityService.findAll();
        return new ResponseEntity<>(amenities, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Amenity> getAmenityById(@PathVariable Long id) {
        Amenity amenity = amenityService.findById(id);
        if (amenity != null) {
            return new ResponseEntity<>(amenity, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Amenity> createAmenity(@RequestBody Amenity amenity) {
        try {
            Amenity createdAmenity = amenityService.create(amenity);
            return new ResponseEntity<>(createdAmenity, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Amenity> updateAmenity(@PathVariable Long id, @RequestBody Amenity amenity) {
        try {
            Amenity updatedAmenity = amenityService.update(amenity);
            return new ResponseEntity<>(updatedAmenity, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAmenity(@PathVariable Long id) {
        amenityService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}