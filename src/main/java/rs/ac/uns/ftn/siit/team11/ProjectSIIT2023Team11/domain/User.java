package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Transient
    private String jwt;

    public User(String email, String password, boolean isActive, String name, String lastName, String address, String phoneNumber, boolean notifications) {
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.notifications = notifications;
    }
}