package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;

import lombok.*;

import jakarta.persistence.*;

import javax.naming.Name;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@DiscriminatorValue("Guest")
public class Guest extends User{

    @OneToMany(cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
    @JoinTable(name = "favoriteAccommodations")
    private List<Accommodation> favoriteAccommodations;
    public Guest(String email, String password, boolean isActive, String name, String lastName, String address, String phoneNumber) {
        super(email, password, isActive, name, lastName, address, phoneNumber, true);
        this.favoriteAccommodations = new ArrayList<>();
    }
}