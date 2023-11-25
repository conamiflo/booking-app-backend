package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
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