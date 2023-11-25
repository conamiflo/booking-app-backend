package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;

import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User {
    @Id
    private String email;

    private String password;

    private boolean isActive;

    private String name;

    private String lastName;

    private String address;

    private String phoneNumber;


    public User(String email, String password, boolean isActive, String name, String lastName, String address, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.name = name;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

}
