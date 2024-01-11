package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role")
public class User {
    @Id
    private String email;

    private String password;
    private boolean isActive;
    private String name;
    private String lastName;
    private String address;
    private String phoneNumber;
    private boolean notifications;
    private String photo;

    @Transient
    private String jwt;

    public User(String email, String password, boolean isActive, String name, String lastName, String address, String phoneNumber, boolean notifications, String photo) {
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.notifications = notifications;
        this.photo = photo;
    }

    public User(String email, String password, boolean isActive, String name, String lastName, String address, String phoneNumber, boolean b) {
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.notifications = notifications;
    }

    public String getRole(){
        if (this instanceof Owner) {
            return "Owner";
        } else if (this instanceof Admin) {
            return "Admin";
        } else {
            return "Guest";
        }
    }
}