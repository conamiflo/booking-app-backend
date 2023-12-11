package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Availability;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO.AvailabilityDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.AvailabilityMapper;
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
        availabilityService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
