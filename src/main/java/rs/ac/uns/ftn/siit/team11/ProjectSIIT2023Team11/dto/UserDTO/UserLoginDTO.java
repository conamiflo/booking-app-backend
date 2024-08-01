package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.UserDTO;

import lombok.*;

@Data
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class UserLoginDTO {
    private String email;
    private String password;
}
