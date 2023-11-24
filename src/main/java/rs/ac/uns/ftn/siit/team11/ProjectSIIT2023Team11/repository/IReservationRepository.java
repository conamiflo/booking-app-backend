package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Reservation;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IReservationRepository extends JpaRepository<Reservation,Long> {

    List<Reservation> findAll();
    Collection<Reservation> findByUserEmail(String email);
    Collection<Reservation> findByAccommodationId(int accommodationId);
    Optional<Reservation> findById(Long reservationId);
    Reservation create(Reservation reservation) throws Exception;
    Reservation update(Reservation reservation) throws Exception;
    void delete(int reservationId);
}
