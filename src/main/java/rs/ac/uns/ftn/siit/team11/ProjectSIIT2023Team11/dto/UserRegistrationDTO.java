package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto;

import lombok.Data;

@Data
public class UserRegistrationDTO {

    private String email;
    private String password;
    private boolean isActive;
    private String name;
    private String lastName;
    private String address;
    private String phoneNumber;
    private String role;
}
