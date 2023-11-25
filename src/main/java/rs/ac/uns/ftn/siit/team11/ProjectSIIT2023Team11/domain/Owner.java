package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;

import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.Collection;

@Getter
@Setter
@Entity
public class Owner extends User{


    private Collection<Accommodation> accommodations;
    private Collection<Reservation> reservations;
    private Double averageScore;

    public Owner(String email, String password, boolean isActive, String name, String lastName, String address, String phoneNumber, Collection<Accommodation> accommodations, Collection<Reservation> reservations, Double averageScore) {
        super(email, password, isActive, name, lastName, address, phoneNumber);
        this.accommodations = accommodations;
        this.reservations = reservations;
        this.averageScore = averageScore;
    }
}
