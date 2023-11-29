package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.UserRole;

public class UserEditAdminDTO {
    private String email;

    private String password;

    private boolean isActive;

    private String name;

    private String lastName;

    private String address;

    private String phoneNumber;


    public UserEditAdminDTO(String email, String password, boolean isActive, String name, String lastName, String address, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
