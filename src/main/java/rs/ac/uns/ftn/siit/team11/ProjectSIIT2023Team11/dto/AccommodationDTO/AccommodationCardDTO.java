package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO;

import java.util.List;

public record AccommodationCardDTO (
        Long id,
        String name,
        String description,
        String location,
        Double price,
        List<String> photos,
        int minGuests,
        int maxGuests
){




}
