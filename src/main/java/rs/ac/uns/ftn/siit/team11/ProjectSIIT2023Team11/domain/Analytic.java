package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Month;
import java.time.Year;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="analytic")
public class Analytic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation")
    private Accommodation accommodation;

    private Month month;

    private Year year;
    private int numberOfReservations;
    private float profit;
}
