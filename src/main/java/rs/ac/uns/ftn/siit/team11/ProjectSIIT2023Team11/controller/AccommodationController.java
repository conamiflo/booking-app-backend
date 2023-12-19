package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.catalina.valves.rewrite.InternalRewriteMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Amenity;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Price;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Reservation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO.AccommodationDetailsDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO.AccommodationDetailsWithAmenitiesDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO.AccommodationPricesDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AmenityOutputDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.PriceDTO.InputPriceDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO.OwnerReservationDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.AccommodationMapper;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.AmenityMapper;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.OwnerReservationMapper;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.PriceMapper;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IAccommodationService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IAmenityService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IPriceService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.lowerCase;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/accommodations")
public class AccommodationController {
    @Autowired
    private IAccommodationService accommodationService;
    @Autowired
    private IAmenityService amenityService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IPriceService priceService;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationDetailsDTO>> getAccommodations() {
        Collection<AccommodationDetailsDTO> accommodations = accommodationService.findAll().stream()
                .map(AccommodationMapper::mapToAccommodationDetailsDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }

    @GetMapping(value = "/amenities", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationDetailsWithAmenitiesDTO>> getAccommodationsWithAmenities() {
        Collection<AccommodationDetailsWithAmenitiesDTO> accommodations = accommodationService.findAll().stream()
                .map(AccommodationMapper::mapToAccommodationDetailsAmenityDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get accommodation by id")
    public ResponseEntity<AccommodationDetailsDTO> getAccommodationById(@PathVariable("id") Long id) {
        Optional<Accommodation> accommodation = accommodationService.findById(id);
        if (accommodation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(AccommodationMapper.mapToAccommodationDetailsDto(accommodation.get()), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_Owner')")
    @Operation(summary = "Create accommodation", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<AccommodationDetailsDTO> createAccommodation(@RequestBody AccommodationDetailsDTO accommodation) throws Exception {
        Optional<AccommodationDetailsDTO> newAccommodation = accommodationService.create(accommodation, userService);
        if(newAccommodation.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(newAccommodation.get(), HttpStatus.CREATED);
    }


    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_Owner')")
    @Operation(summary = "Update accommodation", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<AccommodationDetailsDTO> updateAccommodation(@RequestBody AccommodationDetailsDTO accommodation, @PathVariable Long id) throws Exception {
        Optional<Accommodation> existingAccommodation = accommodationService.findById(id);
        if (existingAccommodation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Accommodation updatedAccommodation = accommodationService.save(AccommodationMapper.mapDetailsDtoToAccommodation(accommodation, userService,existingAccommodation.get()));

        return new ResponseEntity<>(AccommodationMapper.mapToAccommodationDetailsDto(updatedAccommodation), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/amenity/{amenity_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Add amenity to accommodation")
    public ResponseEntity<AccommodationDetailsDTO> addAmenityToAccommodation(@PathVariable("amenity_id") Long amenityId, @PathVariable Long id) {
        Optional<Accommodation> existingAccommodation = accommodationService.findById(id);
        Optional<Amenity> existingAmenity = amenityService.findById(amenityId);
        if (existingAccommodation.isEmpty() || existingAmenity.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if (!existingAccommodation.get().getAmenities().contains(existingAmenity.get())){
            existingAccommodation.get().getAmenities().add(existingAmenity.get());
            existingAmenity.get().getAccommodations().add(existingAccommodation.get());
            Accommodation updatedAccommodation = accommodationService.save(existingAccommodation.get());
            amenityService.save(existingAmenity.get());
            return new ResponseEntity<>(AccommodationMapper.mapToAccommodationDetailsDto(updatedAccommodation), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @GetMapping(value = "/{id}/amenity", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get amenities from accommodation")
    public ResponseEntity<Collection<AmenityOutputDTO>> getAmenitiesFromAccommodation(@PathVariable Long id) {
        Optional<Accommodation> existingAccommodation = accommodationService.findById(id);
        if(existingAccommodation.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(AmenityMapper.mapAmenitiesToAmenityOutputDTOs(existingAccommodation.get().getAmenities()),HttpStatus.BAD_REQUEST);

    }


    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('ROLE_Owner')")
    @Operation(summary = "Delete accommodation", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Void> deleteAccommodation(@PathVariable("id") Long id) {
        Optional<Accommodation> accommodation = accommodationService.findById(id);
        if(accommodation.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        amenityService.deleteAccommodationFromAmenities(accommodation.get());
        accommodationService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/{id}/price", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationPricesDTO> createAccommodationPrice(@RequestBody InputPriceDTO inputPrice, @PathVariable("id") Long id) throws Exception {
        Optional<Accommodation> accommodation = accommodationService.findById(id);
        if(accommodation.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        accommodation.get().getPriceList().add(PriceMapper.mapInputPriceToPrice(inputPrice));
        accommodationService.save(accommodation.get());
        AccommodationPricesDTO accommodationPrice = AccommodationMapper.mapAccommodationToAccommodationPriceDTO(accommodation.get());

        return new ResponseEntity<>(accommodationPrice, HttpStatus.CREATED);
    }
    @DeleteMapping(value = "/{id}/prices/{priceId}")
    public ResponseEntity<Void> deleteAccommodationPrice(@PathVariable("id") Long id, @PathVariable("priceId") Long priceId) {

        Optional<Accommodation> accommodation = accommodationService.findById(id);
        Optional<Price> price = priceService.findById(priceId);
        if(accommodation.isEmpty() || price.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if(accommodation.get().containsPrice(price.get())){
            accommodation.get().getPriceList().remove(priceService.findById(priceId).get());
            priceService.deleteById(priceId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @Operation(summary = "Search accommodation")
    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AccommodationDetailsWithAmenitiesDTO>> searchAccommodations(
            @RequestParam(value = "guests", required = false) Integer guests,
            @RequestParam(value = "location", required = false) String location,
            @RequestParam(value = "startDate", required = false) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) LocalDate endDate) {

        Collection<Accommodation> accommodations = accommodationService.searchAccommodationsByCriteria(
                guests,
                lowerCase(location),
                startDate,
                endDate
        );
        List<AccommodationDetailsWithAmenitiesDTO> accommodationDetailsDTOS = new ArrayList<>();
        for (Accommodation accommodation: accommodations) {
            accommodationDetailsDTOS.add(AccommodationMapper.mapToAccommodationDetailsAmenityDto(accommodation));
        }

        return new ResponseEntity<>(accommodationDetailsDTOS, HttpStatus.OK);
    }
}
