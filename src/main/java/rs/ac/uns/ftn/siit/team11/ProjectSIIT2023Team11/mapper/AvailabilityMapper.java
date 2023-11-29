package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper;

import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Availability;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.TimeSlot;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO.AccommodationAvailabilityDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO.AvailabilityDTO;

@Component
public class AvailabilityMapper {

    public static AvailabilityDTO mapToAvailabilityDto(Availability availability) {
        return new AvailabilityDTO(
                availability.getId(),
                availability.getTimeSlot().getStartDate(),
                availability.getTimeSlot().getEndDate()
        );
    }

    public static AccommodationAvailabilityDTO mapToAccommodationAvailabilityDto(Availability availability) {
        return new AccommodationAvailabilityDTO(
                availability.getTimeSlot().getStartDate(),
                availability.getTimeSlot().getEndDate()
        );
    }
    public static Availability mapToAvailability(AvailabilityDTO dto) {
        Availability availability = new Availability();
        availability.setId(dto.getId());
        availability.setTimeSlot(new TimeSlot(dto.getStartDate(),dto.getEndDate()));
        return availability;
    }
}
