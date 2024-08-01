package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Guest;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.User;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.ReservationStatus;

import java.util.Collection;
import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User,String> {

    @Query("SELECT DISTINCT owner.email FROM Reservation reservation JOIN reservation.accommodation accommodation " +
            "JOIN accommodation.owner owner JOIN reservation.guest guest WHERE guest.email =:guestEmail " +
            "AND (reservation.status = :statusAccepted OR reservation.status = :statusFinished)")
    Collection<String> findOwnersForGuestReport(@Param("guestEmail") String guestEmail,
                                                @Param("statusAccepted") ReservationStatus statusAccepted,
                                                @Param("statusFinished") ReservationStatus statusFinished);

    @Query("SELECT DISTINCT guest.email FROM Reservation reservation JOIN reservation.accommodation accommodation " +
            "JOIN accommodation.owner owner JOIN reservation.guest guest WHERE owner.email = :ownerEmail  " +
            "AND (reservation.status = :statusAccepted OR reservation.status = :statusFinished)")
    Collection<String> findGuestsForOwnerReport(@Param("ownerEmail") String ownerEmai,
                                                @Param("statusAccepted") ReservationStatus statusAccepted,
                                                @Param("statusFinished") ReservationStatus statusFinished);

}