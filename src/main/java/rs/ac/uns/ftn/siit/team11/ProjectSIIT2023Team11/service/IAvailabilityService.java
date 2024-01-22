package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Availability;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.TimeSlot;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO.AvailabilityDTO;

import java.util.List;
import java.util.Optional;

public interface IAvailabilityService {

    List<Availability> findAll();

    <S extends Availability> S save(S entity);
    Optional<Availability> findById(Long aLong);

    void deleteById(Long aLong);

    void deleteAvailabilities(List<Availability> availabilities);

    void fitAcceptedReservation(Long startDate, Long endDate, Accommodation accommodation);

    void returnCancelledAvailability(Long startDate, Long endDate, Accommodation accommodation);

    Availability createAvailability(AvailabilityDTO availabilityDTO, Long accommodationId);

    boolean isOverlap(Long newStartDate, Long newEndDate, Long existingStartDate, Long existingEndDate);

    Availability createAvailabilityForAccommodation(Accommodation accommodation, Long startDate, Long endDate);

}
