package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue("Owner")
public class Owner extends User{

    private Double averageScore;

    public Owner(String email, String password, boolean isActive, String name, String lastName, String address, String phoneNumber) {
        super(email, password, isActive, name, lastName, address, phoneNumber, true);
        this.averageScore = (double) 0;
    }
    public Owner() {
        this.averageScore = 0.0;
    }
    public Owner(User user) {
        super(user.getEmail(), user.getPassword(), user.isActive(), user.getName(), user.getLastName(), user.getAddress(), user.getPhoneNumber(), true);
        this.averageScore = 0.0;
    }

    public Owner(String email, String password, boolean active, String firstName, String lastName, String address, String phoneNumber, boolean notifications, String photo, Double averageScore) {
        super(email, password, active, firstName, lastName, address, phoneNumber, notifications);
        this.averageScore = averageScore;
    }
}