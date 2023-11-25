package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.processing.Generated;
import javax.persistence.Entity;
import java.util.Collection;

@Getter
@Setter
@Entity
public class Guest extends User{

    private int cancelledReservations;

    public Guest(String email, String password, boolean isActive, String name, String lastName, String address, String phoneNumber, int cancelledReservations) {
        super(email, password, isActive, name, lastName, address, phoneNumber);
        this.cancelledReservations = cancelledReservations;
    }
}