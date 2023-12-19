package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Availability;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.TimeSlot;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO.AccommodationAvailabilityDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO.AvailabilityDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.AvailabilityMapper;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IAccommodationService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IAvailabilityService;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/availabilities")
public class AvailabilityController {

    @Autowired
    private IAvailabilityService availabilityService;
    @Autowired
    private IAccommodationService accommodationService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AvailabilityDTO>> getAllAvailabilities() {
        Collection<Availability> availabilities = availabilityService.findAll();
        return new ResponseEntity<>(availabilities.stream()
                .map(AvailabilityMapper::mapToAvailabilityDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AvailabilityDTO> getAvailabilityById(@PathVariable("id") Long id) {
        Optional<Availability> availability = availabilityService.findById(id);
        return new ResponseEntity<>(AvailabilityMapper.mapToAvailabilityDto(availability.get()), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Availability> createAvailability(@RequestBody AvailabilityDTO availability) {
        return new ResponseEntity<>(availabilityService.save(AvailabilityMapper.mapToAvailability(availability)), HttpStatus.CREATED);
    }
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Availability> updateAvailability(@RequestBody AvailabilityDTO availability) {
        Optional<Availability> existingAvailability = availabilityService.findById(availability.getId());
        if (existingAvailability.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(availabilityService.save(AvailabilityMapper.mapToAvailability(availability)), HttpStatus.OK);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAvailability(@PathVariable("id") Long id) {
        Optional<Availability> availability = availabilityService.findById(id);
        if(availability.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        accommodationService.deleteAvailabilityFromAllAccommodations(availability.get());
        availabilityService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping( value = "/accommodation/{accommodation_id}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Availability> createAvailability(@RequestBody AvailabilityDTO availability, @PathVariable("accommodation_id") Long accommodationId) {
        Optional<Accommodation> accommodation = accommodationService.findById(accommodationId);
        if(accommodation.isEmpty())
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(accommodation.get().AddAvailability(new TimeSlot(availability.getStartDate().plusDays(1), availability.getEndDate().plusDays(1)))) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Availability createdAvailability = availabilityService.save(AvailabilityMapper.mapToAvailability(availability));

        accommodation.get().getAvailability().add(createdAvailability);
        accommodationService.save(accommodation.get());

        return new ResponseEntity<>(createdAvailability, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ROLE_Owner')")
    @Operation(summary = "Get availabilities", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(value = "/available/{accommodation_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationAvailabilityDTO>> getAvailabilitiesForAccommodation(@PathVariable("accommodation_id") Long accommodationId) {
        Optional<Accommodation> accommodation = accommodationService.findById(accommodationId);
        if (accommodation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accommodation.get().getAvailability().stream().map(AvailabilityMapper::mapToAccommodationAvailabilityDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }


    @DeleteMapping(value = "/{id}/accommodation/{accommodationId}")
    public ResponseEntity<Void> deleteAccommodationAvailability(@PathVariable("id") Long id, @PathVariable("accommodationId") Long accommodationId) {

        Optional<Accommodation> accommodation = accommodationService.findById(accommodationId);
        Optional<Availability> availability = availabilityService.findById(id);
        if(accommodation.isEmpty() || availability.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        while(accommodation.get().getAvailability().contains(availability.get())){
            accommodation.get().getAvailability().remove(availability.get());

        }

        availabilityService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

}
