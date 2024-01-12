package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AccommodationDTO;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccommodationIsAutomaticApprovalDTO {
    private Long id;
    private boolean automaticApproval;

    public AccommodationIsAutomaticApprovalDTO(Long id, boolean automaticApproval) {
        this.id = id;
        this.automaticApproval = automaticApproval;
    }

    public AccommodationIsAutomaticApprovalDTO() {
    }

}
