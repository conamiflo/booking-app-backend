package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;

import lombok.Getter;

public class User {
    private String email;

    private String password;

    private boolean isActive;

    private String name;

    private String lastName;

    private String address;

    private String phoneNumber;

    @Getter
    private UserRole userRole;

    public User(String email, String password, boolean isActive, String name, String lastName, String address, String phoneNumber, UserRole userRole) {
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.userRole = userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
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
