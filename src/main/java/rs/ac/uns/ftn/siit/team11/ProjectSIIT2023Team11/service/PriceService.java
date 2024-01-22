package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Availability;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Price;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.TimeSlot;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO.AccommodationPricesDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO.AvailabilityDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.PriceDTO.InputPriceDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.AccommodationMapper;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.PriceMapper;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IPriceRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class PriceService implements IPriceService{
    @Autowired
    IPriceRepository repository;

    @Autowired
    IAccommodationService accommodationService;

    public List<Price> findAll() {
        return repository.findAll();
    }

    public <S extends Price> S save(S entity) {
        return repository.save(entity);
    }

    public Optional<Price> findById(Long s) {
        return repository.findById(s);
    }

    public void deleteById(Long s) {
        repository.deleteById(s);
    }

    @Override
    public AccommodationPricesDTO createPrice(InputPriceDTO priceDTO, Long accommodationId) {
        AccommodationPricesDTO createdPrices = createPriceForAccommodation(accommodationService.findById(accommodationId).get(),priceDTO.getTimeSlot().getStartEpochTime(),
                priceDTO.getTimeSlot().getEndEpochTime(), priceDTO.getPrice());
        return createdPrices;
    }
    public boolean isOverlap(Long newStartDate, Long newEndDate, Long existingStartDate, Long existingEndDate) {
        return newStartDate <= existingEndDate && newEndDate >= existingStartDate;
    }

    public boolean isOverlapPrice(Long newStartDate, Long newEndDate, Long existingStartDate, Long existingEndDate) {
        return newStartDate <= existingEndDate && newEndDate >= existingStartDate;
    }


    private boolean isPriceOverlap(Accommodation accommodation, long newStartDate, long newEndDate) {
        for (Price existingPrice : accommodation.getPriceList()) {
            if (isOverlapPrice(newStartDate,newEndDate,existingPrice.getTimeSlot().getStartEpochTime(),existingPrice.getTimeSlot().getEndEpochTime())) {
                return true;
            }
        }
        return false;
    }

    public AccommodationPricesDTO createPriceForAccommodation(Accommodation accommodation, Long startDate, Long endDate, Double price) {
        if(validateInputDates(startDate,endDate) || isPriceOverlap(accommodation,startDate,endDate) || price <= 0){
            return null;
        }
        accommodation.getPriceList().add(PriceMapper.mapInputPriceToPrice(new InputPriceDTO(new TimeSlot(startDate,endDate),price)));
        accommodationService.save(accommodation);
        AccommodationPricesDTO accommodationPrice = AccommodationMapper.mapAccommodationToAccommodationPriceDTO(accommodation);
        return accommodationPrice;
    }

    public boolean validateInputDates(Long startDate, Long endDate){
        Instant currentInstant = Instant.now();
        long currentDate = currentInstant.toEpochMilli() / 1000;

        if (startDate == null || endDate == null || startDate < currentDate || endDate < currentDate || startDate > endDate) {
            return true;
        }
        return false;
    }



    public void deletePrices(List<Price> prices){
        for (Price price : prices) {
            deleteById(price.getId());
        }
        prices.clear();
    }

}
