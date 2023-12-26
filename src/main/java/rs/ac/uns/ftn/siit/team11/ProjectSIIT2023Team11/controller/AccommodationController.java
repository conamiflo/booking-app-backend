package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.catalina.valves.rewrite.InternalRewriteMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.*;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO.AccommodationDetailsDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO.AccommodationDetailsWithAmenitiesDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO.AccommodationPricesDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AmenityOutputDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.PriceDTO.InputPriceDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.PriceDTO.PriceForEditDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO.OwnerReservationDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.*;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.*;
import io.swagger.v3.oas.annotations.Operation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.AccommodationStatus;

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
    @Autowired
    private IAvailabilityService availabilityService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationDetailsDTO>> getAccommodations() {
        Collection<AccommodationDetailsDTO> accommodations = accommodationService.findAll().stream()
                .map(AccommodationMapper::mapToAccommodationDetailsDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_Owner','ROLE_Guest','ROLE_Admin')")
    @Operation(summary = "Get inactive accommodations", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(value = "/inactive", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationDetailsDTO>> getInactiveAccommodations() {
        Collection<AccommodationDetailsDTO> accommodations = accommodationService.findAccommodationsByPendingStatus().stream()
                .map(AccommodationMapper::mapToAccommodationDetailsDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }

    @GetMapping(value = "/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationDetailsDTO>> getActiveAccommodations() {
        Collection<AccommodationDetailsDTO> accommodations = accommodationService.findActiveAccommodations().stream()
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


    @PreAuthorize("hasAnyAuthority('ROLE_Owner','ROLE_Admin')")
    @Operation(summary = "Update accommodation", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationDetailsDTO> updateAccommodation(@RequestBody AccommodationDetailsDTO accommodation, @PathVariable Long id) throws Exception {
        Optional<Accommodation> existingAccommodation = accommodationService.findById(id);
        if (existingAccommodation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Accommodation accommodation2 = existingAccommodation.get();
        amenityService.deleteAmenities(accommodation2.getAmenities(),accommodation2);
        priceService.deletePrices(accommodation2.getPriceList());
        availabilityService.deleteAvailabilities(accommodation2.getAvailability());
        Accommodation updatedAccommodation = accommodationService.save(AccommodationMapper.mapDetailsDtoToAccommodation(accommodation, userService,accommodation2));
        return new ResponseEntity<>(AccommodationMapper.mapToAccommodationDetailsDto(updatedAccommodation), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_Owner','ROLE_Admin')")
    @Operation(summary = "Update accommodation", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping(value = "/activate/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> activateAccommodation(@PathVariable Long id) throws Exception {
        Optional<Accommodation> existingAccommodation = accommodationService.findById(id);
        if (existingAccommodation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Accommodation accommodation = existingAccommodation.get();
        accommodation.setStatus(AccommodationStatus.Active);
        accommodationService.save(accommodation);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_Owner')")
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

    @DeleteMapping(value = "/amenities/{id}")
    @PreAuthorize("hasAuthority('ROLE_Owner')")
    @Operation(summary = "Remove all amenities from accommodation")
    public ResponseEntity<Void> removeAllAmenitiesFromAccommodation(@PathVariable Long id) {
        Optional<Accommodation> accommodation = accommodationService.findById(id);

        if (accommodation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Accommodation existingAccommodation = accommodation.get();
        List<Amenity> amenities = existingAccommodation.getAmenities();
        for (Amenity amenity : amenities) {
            amenity.getAccommodations().remove(accommodation);
        }
        amenities.clear();
        accommodationService.save(existingAccommodation);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('ROLE_Owner')")
    @GetMapping(value = "/{id}/amenity", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get amenities from accommodation")
    public ResponseEntity<Collection<AmenityOutputDTO>> getAmenitiesFromAccommodation(@PathVariable Long id) {
        Optional<Accommodation> existingAccommodation = accommodationService.findById(id);
        if(existingAccommodation.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(AmenityMapper.mapAmenitiesToAmenityOutputDTOs(existingAccommodation.get().getAmenities()),HttpStatus.OK);
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

    @PreAuthorize("hasAuthority('ROLE_Owner')")
    @Operation(summary = "Create accommodation price", security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "/{id}/price", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AccommodationPricesDTO> createAccommodationPrice(@RequestBody InputPriceDTO inputPrice, @PathVariable("id") Long id) {
        Optional<Accommodation> accommodation = accommodationService.findById(id);
        if(accommodation.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        accommodation.get().getPriceList().add(PriceMapper.mapInputPriceToPrice(inputPrice));
        accommodationService.save(accommodation.get());
        AccommodationPricesDTO accommodationPrice = AccommodationMapper.mapAccommodationToAccommodationPriceDTO(accommodation.get());

        return new ResponseEntity<>(accommodationPrice, HttpStatus.CREATED);
    }


    @PreAuthorize("hasAuthority('ROLE_Owner')")
    @Operation(summary = "Delete accommodation price", security = @SecurityRequirement(name = "bearerAuth"))
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


    @GetMapping(value = "/prices/{id}")
    public ResponseEntity<Collection<PriceForEditDTO>> getAccommodationPrices(@PathVariable("id") Long id) {
        Optional<Accommodation> accommodation = accommodationService.findById(id);

        if (accommodation.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(accommodation.get().getPriceList().stream().map(PriceMapper::mapToPriceDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }
    @GetMapping(value = "/owner/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ROLE_Owner')")
    @Operation(summary = "Get accommodation by owner")
    public ResponseEntity<Collection<AccommodationDetailsDTO>> getAccommodationByOwner(@PathVariable("email") String email) {
        Collection<AccommodationDetailsDTO> accommodations = accommodationService.findByOwnersId(email).stream()
                .map(AccommodationMapper::mapToAccommodationDetailsDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }

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

    @GetMapping(value = "/search/detailed", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AccommodationDetailsDTO>> searchAccommodationsDetailed(
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
        List<AccommodationDetailsDTO> accommodationDetailsDTOS = new ArrayList<>();
        for (Accommodation accommodation: accommodations) {
            accommodationDetailsDTOS.add(AccommodationMapper.mapToAccommodationDetailsDto(accommodation));
        }

        return new ResponseEntity<>(accommodationDetailsDTOS, HttpStatus.OK);
    }
}
