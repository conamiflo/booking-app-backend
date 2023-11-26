package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDetailsDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.AccommodationMapper;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IAccommodationService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IUserService;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;



@RestController
@RequestMapping("/api/accommodations")
public class AccommodationController {
    @Autowired
    private IAccommodationService accommodationService;
    @Autowired
    private IUserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationDetailsDTO>> getAccommodations() {
        Collection<AccommodationDetailsDTO> accommodations = accommodationService.findAll().stream()
                .map(AccommodationMapper::mapToAccommodationDetailsDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDetailsDTO> getAccommodationById(@PathVariable("id") Long id) {
        Optional<Accommodation> accommodation = accommodationService.findById(id);
        if (accommodation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(AccommodationMapper.mapToAccommodationDetailsDto(accommodation.get()), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDetailsDTO> createAccommodation(@RequestBody AccommodationDetailsDTO accommodation) throws Exception {
        Optional<AccommodationDetailsDTO> newAccommodation = accommodationService.create(accommodation, userService);
        if(newAccommodation.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(newAccommodation.get(), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDetailsDTO> updateAccommodation(@RequestBody AccommodationDetailsDTO accommodation, @PathVariable Long id) throws Exception {
        Optional<Accommodation> existingAccommodation = accommodationService.findById(id);
        if (existingAccommodation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Accommodation updatedAccommodation = accommodationService.save(AccommodationMapper.mapDetailsDtoToAccommodation(accommodation, userService,existingAccommodation.get()));

        return new ResponseEntity<>(AccommodationMapper.mapToAccommodationDetailsDto(updatedAccommodation), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAccommodation(@PathVariable("id") Long id) {
        accommodationService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
