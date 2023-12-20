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
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.AccommodationRequest;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO.AccommodationDetailsDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO.AccommodationDetailsWithAmenitiesDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO.AccommodationRequestsDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.AccommodationMapper;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IAccommodationRequestService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IAccommodationService;

import java.util.Collection;
import java.util.Optional;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/accommodationRequests")
public class AccommodationRequestController {

    @Autowired
    private IAccommodationRequestService accommodationRequestService;
    @Autowired
    private IAccommodationService accommodationService;
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationRequest>> getAccommodationRequests() {
        Collection<AccommodationRequest> accommodations = accommodationRequestService.findAll();
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get accommodationRequest by id")
    public ResponseEntity<AccommodationRequest> getAccommodationRequestById(@PathVariable("id") Long id) {
        Optional<AccommodationRequest> accommodation = accommodationRequestService.findById(id);
        if (accommodation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accommodation.get(), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_Owner')")
    @Operation(summary = "Create accommodation request", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<AccommodationRequestsDTO> createAccommodationRequest(@RequestBody AccommodationRequestsDTO accommodationRequest) {
        Optional<AccommodationRequest> accRequest = Optional.ofNullable(accommodationRequestService.create(accommodationRequest));
        if(accRequest.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(accommodationRequest, HttpStatus.CREATED);
    }


}
