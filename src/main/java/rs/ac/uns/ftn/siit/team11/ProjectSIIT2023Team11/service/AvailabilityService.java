package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Availability;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Price;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.TimeSlot;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IAvailabilityRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AvailabilityService implements IAvailabilityService{

    @Autowired
    IAvailabilityRepository availabilityRepository;

    @Autowired
    IAccommodationService accommodationService;

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
}
