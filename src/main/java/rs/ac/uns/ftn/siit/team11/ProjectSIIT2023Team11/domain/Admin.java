package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;

import lombok.*;

import jakarta.persistence.*;

@Getter
@Setter
@Entity
@DiscriminatorValue("Admin")
public class Admin extends User{

}