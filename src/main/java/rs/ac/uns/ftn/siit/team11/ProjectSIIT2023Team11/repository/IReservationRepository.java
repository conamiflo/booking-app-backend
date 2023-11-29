package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Reservation;

import java.util.Collection;
import java.util.List;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation,Long> {

    Collection<Reservation> findReservationsByAccommodation_Owner_Email(String email);
    Collection<Reservation> findReservationsByGuestEmail(String email);
//    Collection<Reservation> findByAccommodationName(String accommodationName);
}
