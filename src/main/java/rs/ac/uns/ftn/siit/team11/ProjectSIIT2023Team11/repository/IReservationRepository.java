package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Reservation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.ReservationStatus;

import java.util.Collection;
import java.util.List;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation,Long> {

    Collection<Reservation> findAllByGuestEmail(String email);
    Collection<Reservation> findAllByAccommodationOwnerEmail(String email);

//    Collection<Reservation> findAllByGuestEmailAndAccommodationName(String email,String name);

//    Collection<Reservation> findAllByOwnerEmailAndAccommodationName(String email,String name);

    @Query("SELECT r FROM Reservation r WHERE r.status = :status AND r.guest.email = :email")
    Collection<Reservation> findByStatusAndGuestEmail(@Param("status") ReservationStatus status, @Param("email") String email);

    @Query("SELECT r FROM Reservation r WHERE r.status = :status AND r.accommodation.owner.email = :email")
    List<Reservation> findByStatusAndOwnerEmail(@Param("status") ReservationStatus status, @Param("email") String email);
}
