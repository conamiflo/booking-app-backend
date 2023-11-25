package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;

import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class Admin extends User{
    public Admin(String email, String password, boolean isActive, String name, String lastName, String address, String phoneNumber) {
        super(email, password, isActive, name, lastName, address, phoneNumber);
    }
}
