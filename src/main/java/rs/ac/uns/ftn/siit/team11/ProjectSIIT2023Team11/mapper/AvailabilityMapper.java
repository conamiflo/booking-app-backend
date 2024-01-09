package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper;

import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Availability;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.TimeSlot;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO.AccommodationAvailabilityDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO.AvailabilityDTO;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class AvailabilityMapper {

    public static AvailabilityDTO mapToAvailabilityDto(Availability availability) {
        return new AvailabilityDTO(
                availability.getId(),
                availability.getTimeSlot().getStartEpochTime(),
                availability.getTimeSlot().getEndEpochTime()
        );
    }

    public static AccommodationAvailabilityDTO mapToAccommodationAvailabilityDto(Availability availability) {
        return new AccommodationAvailabilityDTO(
                Instant.ofEpochSecond(availability.getTimeSlot().getStartEpochTime()).atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                Instant.ofEpochSecond(availability.getTimeSlot().getEndEpochTime()).atZone(ZoneId.systemDefault()).toLocalDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                );
    }
    public static Availability mapToAvailability(AvailabilityDTO dto) {
        Availability availability = new Availability();
        availability.setTimeSlot(new TimeSlot(dto.getStartDate(),dto.getEndDate()));
        return availability;
    }
}
