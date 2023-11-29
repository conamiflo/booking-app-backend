package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Price;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDetailsDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.InputPriceDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.PriceForShowDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.AccommodationMapper;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.PriceMapper;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IAccommodationService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IPriceService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/price")
public class PriceController {
    @Autowired
    private IPriceService priceService;
    @Autowired
    private IAccommodationService accommodationService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<PriceForShowDTO>> getPrices() {
        Collection<PriceForShowDTO> prices = priceService.findAll().stream().map(PriceMapper::mapToPriceForShowDto).toList();
        return new ResponseEntity<>(prices, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PriceForShowDTO> getPriceById(@PathVariable("id") Long id) {
        Optional<Price> price = priceService.findById(id);
        if(price.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(PriceMapper.mapToPriceForShowDto(price.get()),HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PriceForShowDTO> createPrice(@RequestBody InputPriceDTO priceInput) {
        priceService.save(PriceMapper.mapInputPriceToPrice(priceInput));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InputPriceDTO> updateAccommodation(@RequestBody InputPriceDTO updatedPrice, @PathVariable Long id){
        Optional<Price> existingPrice = priceService.findById(id);
        if(existingPrice.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        PriceMapper.mapPriceForShowToPrice(updatedPrice, existingPrice.get());
        priceService.save(existingPrice.get());
        return new ResponseEntity<>(updatedPrice, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletePrice(@PathVariable("id") Long id) {
        Optional<Price> price = priceService.findById(id);
        if(price.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Accommodation> accommodations = accommodationService.findAll();
        for(Accommodation accommodation : accommodations){
            if(accommodation.containsPrice(price.get())){
                accommodation.getPriceList().remove(price.get());
            }
        }
        priceService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
