package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Amenity;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO.AccommodationDetailsDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AmenityInputDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AmenityOutputDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.AccommodationMapper;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.AmenityMapper;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IAccommodationService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IAmenityService;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/amenities")
public class AmenityController {
    @Autowired
    private IAmenityService amenityService;

    @Autowired
    private IAccommodationService accommodationService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AmenityOutputDTO>> getAccommodations() {
        Collection<Amenity> amenities = amenityService.findAll();
        return new ResponseEntity<>(AmenityMapper.mapAmenitiesToAmenityOutputDTOs(amenities), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get amenity by id")
    public ResponseEntity<Amenity> getAccommodationById(@PathVariable("id") Long id) {
        Optional<Amenity> amenity = amenityService.findById(id);
        if (amenity.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(amenity.get(), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create amenity")
    public ResponseEntity<AmenityInputDTO> createAccommodation(@RequestBody AmenityInputDTO amenity) throws Exception {
        amenityService.save(AmenityMapper.mapAmenityInputDTOToAmenity(amenity));
        return new ResponseEntity<>(amenity, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update amenity")
    public ResponseEntity<Amenity> updateAmenity(@RequestBody Amenity amenity, @PathVariable Long id) throws Exception {
        Optional<Amenity> existingAmenity = amenityService.findById(id);
        if (existingAmenity.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Amenity updatedAmenity = amenityService.save(amenity);

        return new ResponseEntity<>(updatedAmenity, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete amenity")
    public ResponseEntity<Void> deleteAmenity(@PathVariable("id") Long id) {
        Optional<Amenity> existingAmenity = amenityService.findById(id);
        if(existingAmenity.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        accommodationService.deleteAmenityFromAccommodations(existingAmenity.get());

        amenityService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
