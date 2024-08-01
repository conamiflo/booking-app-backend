package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO;

import lombok.*;


@Getter
@Setter
public class FavoriteAccommodationDTO {
    private Long accommodationId;
    private boolean favorite;

    public FavoriteAccommodationDTO(Long accommodationId, boolean favorite) {
        this.accommodationId = accommodationId;
        this.favorite = favorite;
    }

    public FavoriteAccommodationDTO() {

    }

}
