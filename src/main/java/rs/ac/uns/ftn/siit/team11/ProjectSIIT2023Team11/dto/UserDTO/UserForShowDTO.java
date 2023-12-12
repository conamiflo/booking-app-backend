package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.UserDTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserForShowDTO {

    private String firstName;

    private String lastName;

    private String email;

    private String address;

    private String phoneNumber;

    private String password;

    private boolean notifications;

    private String photo;
}
