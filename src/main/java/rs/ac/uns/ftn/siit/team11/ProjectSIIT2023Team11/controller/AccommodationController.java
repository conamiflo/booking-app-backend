package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationForShowDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.AccommodationForShowMapper;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.AccommodationService;

import java.util.Collection;

@RestController
@RequestMapping("/api/accommodations")
public class AccommodationController {

    @Autowired
    private AccommodationService accommodationService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationForShowDTO>> getAccommodations() {
        Collection<AccommodationForShowDTO> accommodations = AccommodationForShowMapper.mapToAccommodationsForShowDto(accommodationService.findAll());
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationForShowDTO> getAccommodationById(@PathVariable("id") int id) {
        Accommodation accommodation = accommodationService.findById(id);
        if (accommodation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(AccommodationForShowMapper.mapToAccommodationForShowDto(accommodation), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Accommodation> createAccommodation(@RequestBody Accommodation accommodation) throws Exception {
        Accommodation newAccommodation = accommodationService.create(accommodation);
        return new ResponseEntity<>(newAccommodation, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Accommodation> updateAccommodation(@RequestBody Accommodation accommodation, @PathVariable int id) throws Exception {
        Accommodation existingAccommodation = accommodationService.findById(id);
        if (existingAccommodation == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Accommodation updatedAccommodation = accommodationService.update(existingAccommodation);
        return new ResponseEntity<>(updatedAccommodation, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAccommodation(@PathVariable("id") int id) {
        accommodationService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Accommodation>> searchAccommodations(@RequestParam("location") String location,
                                                                           @RequestParam("guests") int guests,
                                                                           @RequestParam("startDate") String startDate,
                                                                           @RequestParam("endDate") String endDate) {
        Collection<Accommodation> foundAccommodations = accommodationService.search(location, guests, startDate, endDate);
        return new ResponseEntity<>(foundAccommodations, HttpStatus.OK);
    }
}