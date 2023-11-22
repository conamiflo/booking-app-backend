package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.UserRole;

public class UserForShowDTO {
    private String email;

    private String name;

    private String lastName;

    private UserRole userRole;


    public UserForShowDTO(String email, String name, String lastName, UserRole userRole) {
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.userRole = userRole;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}

