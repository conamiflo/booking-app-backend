package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Availability;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Reservation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.TimeSlot;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO.AvailabilityDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IAvailabilityRepository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.ReservationStatus;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class AvailabilityService implements IAvailabilityService{

    @Autowired
    IAvailabilityRepository availabilityRepository;

    @Autowired
    IAccommodationService accommodationService;

    @Autowired
    IReservationService reservationService;

    public List<Availability> findAll() {
        return availabilityRepository.findAll();
    }

    public <S extends Availability> S save(S entity) {
        return availabilityRepository.save(entity);
    }

    public Optional<Availability> findById(Long aLong) {
        return availabilityRepository.findById(aLong);
    }

    public void deleteById(Long aLong) {
        availabilityRepository.deleteById(aLong);
    }


    @Override
    public Availability createAvailability(AvailabilityDTO availabilityDTO, Long accommodationId) {
        Availability createdAvailability = createAvailabilityForAccommodation(accommodationService.findById(accommodationId).get(), availabilityDTO.getStartDate(), availabilityDTO.getEndDate());
        return createdAvailability;
    }
    @Override
    public boolean isOverlap(Long newStartDate, Long newEndDate, Long existingStartDate, Long existingEndDate) {
        return newStartDate <= existingEndDate && newEndDate >= existingStartDate;
    }
    @Override
    public Availability createAvailabilityForAccommodation(Accommodation accommodation, Long startDate, Long endDate) {
        if(validateInputDates(startDate,endDate) || isOverLappingWithReservations(startDate,endDate,accommodation.getId())){
            return null;
        }
        for (Availability availability : accommodation.getAvailability()) {
            if (isOverlap(startDate, endDate, availability.getTimeSlot().getStartEpochTime(), availability.getTimeSlot().getEndEpochTime())) {
                return null;
            }
        }
        Availability newAvailability = new Availability(0L, new TimeSlot(startDate, endDate));
        save(newAvailability);
        accommodation.getAvailability().add(newAvailability);
        accommodationService.save(accommodation);
        return newAvailability;
    }

    public boolean validateInputDates(Long startDate, Long endDate){
        Instant currentInstant = Instant.now();
        long currentDate = currentInstant.toEpochMilli() / 1000;

        if (startDate == null || endDate == null || startDate < currentDate || endDate < currentDate || startDate > endDate) {
            return true;
        }
        return false;
    }

    public boolean isOverLappingWithReservations(Long startDate, Long endDate,Long id){
        Collection<Reservation> reservations = reservationService.getReservationsByAccommodationId(id);
        for (Reservation reservation : reservations) {
            if (reservation.getStatus() == ReservationStatus.Accepted && isOverlap(startDate, endDate, reservation.getStartDate(), reservation.getEndDate())) {
                return true;
            }
        }
        return false;
    }

    public void deleteAvailabilities(List<Availability> availabilities){
        for (Availability availability : availabilities) {
            deleteById(availability.getId());
        }
        availabilities.clear();
    }

    @Override
    public void fitAcceptedReservation(Long startDate, Long endDate, Accommodation accommodation) {
        for (int i = 0; i <  accommodation.getAvailability().size(); i++) {
            Availability availability = accommodation.getAvailability().get(i);
            if (availability.getTimeSlot().contains(startDate)) {

                if (startDate > availability.getTimeSlot().getStartEpochTime()) {
                    Availability newAvailabilityBefore = new Availability(0L, new TimeSlot(availability.getTimeSlot().getStartEpochTime(), startDate - (1 * 24 * 60 * 60)));
                    save(newAvailabilityBefore);
                    accommodation.getAvailability().add(newAvailabilityBefore);
                    accommodationService.save(accommodation);

                }

                if (endDate < availability.getTimeSlot().getEndEpochTime()) {
                    Availability newAvailabilityAfter = new Availability(0L, new TimeSlot(endDate + (1 * 24 * 60 * 60), availability.getTimeSlot().getEndEpochTime()));
                    save(newAvailabilityAfter);
                    accommodation.getAvailability().add(newAvailabilityAfter);
                    accommodationService.save(accommodation);

                }
                accommodation.getAvailability().remove(availability);
                accommodationService.save(accommodation);
                deleteById(availability.getId());

            }
        }
    }

    @Override
    public void returnCancelledAvailability(Long startDate, Long endDate, Accommodation accommodation){
        Availability canceledAvailability = new Availability(0L, new TimeSlot(startDate, endDate));
        List<Availability> overlappingAvailabilities = new ArrayList<>();
        for (Availability existingAvailability : accommodation.getAvailability()) {
            if ((canceledAvailability.getTimeSlot().getStartEpochTime() == existingAvailability.getTimeSlot().getEndEpochTime() + (1 * 24 * 60 * 60))
                || canceledAvailability.getTimeSlot().getEndEpochTime() == existingAvailability.getTimeSlot().getStartEpochTime() - (1 * 24 * 60 * 60)){
                overlappingAvailabilities.add(existingAvailability);
            }
        }

        accommodation.getAvailability().removeAll(overlappingAvailabilities);
        for(Availability availability : overlappingAvailabilities){
            deleteById(availability.getId());
        }
        accommodationService.save(accommodation);

        long minStartTime = canceledAvailability.getTimeSlot().getStartEpochTime();
        long maxEndTime = canceledAvailability.getTimeSlot().getEndEpochTime();

        for (Availability availability : overlappingAvailabilities) {
            minStartTime = Math.min(minStartTime, availability.getTimeSlot().getStartEpochTime());
            maxEndTime = Math.max(maxEndTime, availability.getTimeSlot().getEndEpochTime());
        }

        Availability newAvailability = new Availability(0L, new TimeSlot(minStartTime, maxEndTime));
        save(newAvailability);
        accommodation.getAvailability().add(newAvailability);
        accommodationService.save(accommodation);
    }
}
