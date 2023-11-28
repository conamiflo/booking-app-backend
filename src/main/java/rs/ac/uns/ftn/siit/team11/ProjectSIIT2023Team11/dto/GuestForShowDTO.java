package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;

import java.util.List;

public class GuestForShowDTO {
    private String email;

    private String name;

    private String lastName;

    private String role;
    private List<Accommodation> favoriteAccommodations;

    public GuestForShowDTO(String email, String name, String lastName, String role, List<Accommodation> favoriteAccommodations) {
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.role = role;
        this.favoriteAccommodations = favoriteAccommodations;
    }

    public GuestForShowDTO() {
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Accommodation> getFavoriteAccommodations() {
        return favoriteAccommodations;
    }

    public void setFavoriteAccommodations(List<Accommodation> favoriteAccommodations) {
        this.favoriteAccommodations = favoriteAccommodations;
    }

}
