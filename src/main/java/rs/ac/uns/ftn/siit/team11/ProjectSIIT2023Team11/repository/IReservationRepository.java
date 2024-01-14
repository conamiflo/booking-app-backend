package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Guest;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Owner;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Reservation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.ReservationStatus;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation,Long> {

    Collection<Reservation> findAllByGuestEmail(String email);
    Collection<Reservation> findAllByAccommodationOwnerEmail(String email);

    @Query("SELECT r FROM Reservation r WHERE " +
            "((:startDate IS NULL OR :endDate IS NULL OR ( r.startDate >= :startDate AND r.startDate <= :endDate AND r.endDate >= :startDate AND r.endDate <= :endDate)) " +
            "AND (:accommodationName IS NULL OR r.accommodation.name LIKE %:accommodationName%) AND r.guest.email LIKE :email)")
    Collection<Reservation> searchGuestReservations(@Param("startDate") Long startDate,@Param("endDate") Long endDate,
                                                           @Param("accommodationName") String accommodationName, @Param("email") String email);

    @Query("SELECT r FROM Reservation r WHERE " +
            "((:startDate IS NULL OR :endDate IS NULL OR ( r.startDate >= :startDate AND r.startDate <= :endDate AND r.endDate >= :startDate AND r.endDate <= :endDate)) " +
            "AND (:accommodationName IS NULL OR r.accommodation.name LIKE %:accommodationName%) AND r.accommodation.owner.email LIKE :email)")
    Collection<Reservation> searchOwnerReservations(@Param("startDate") Long startDate,@Param("endDate") Long endDate,
                                                    @Param("accommodationName") String accommodationName, @Param("email") String email);


    @Query("SELECT r FROM Reservation r WHERE (:status IS NULL OR r.status = :status) AND r.guest.email = :email")
    Collection<Reservation> findByStatusAndGuestEmail(@Param("status") ReservationStatus status, @Param("email") String email);

    @Query("SELECT r FROM Reservation r WHERE (:status IS NULL OR r.status = :status) AND r.accommodation.owner.email = :email")
    List<Reservation> findByStatusAndOwnerEmail(@Param("status") ReservationStatus status, @Param("email") String email);

    @Query("SELECT COUNT(r) > 0 FROM Reservation r WHERE r.accommodation.owner.email = :ownerEmail AND r.guest.email = :guestEmail AND (r.status = :statusAccepted OR r.status = :statusFinished)")
    boolean hasUnCancelledReservationWithOwnerAndStatus(@Param("guestEmail") String guestEmail, @Param("ownerEmail") String ownerEmail, @Param("statusAccepted") ReservationStatus statusAccepted, @Param("statusFinished") ReservationStatus statusFinished);


    @Query("SELECT r FROM Reservation r WHERE r.guest.email = :guestEmail AND r.accommodation.id = :accommodationId AND (r.status = :statusAccepted OR r.status = :statusFinished)")
    Collection<Reservation> findReservationsForAccommodationReview(
            @Param("guestEmail") String guestEmail,
            @Param("accommodationId") Long accommodationId,
            @Param("statusAccepted") ReservationStatus statusAccepted,
            @Param("statusFinished") ReservationStatus statusFinished);

}
