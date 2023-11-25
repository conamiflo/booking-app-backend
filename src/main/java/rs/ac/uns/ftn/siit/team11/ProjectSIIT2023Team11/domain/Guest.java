package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;

import lombok.*;

import jakarta.persistence.*;

@Getter
@Setter
@Entity
@DiscriminatorValue("Guest")
public class Guest extends User{

    private int cancelledReservations;

    public Guest(String email, String password, boolean isActive, String name, String lastName, String address, String phoneNumber) {
        super(email, password, isActive, name, lastName, address, phoneNumber);
        this.cancelledReservations = 0;
    }

    public Guest() {

    }
}