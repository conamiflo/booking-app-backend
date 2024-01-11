package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Guest;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.User;

import java.util.Collection;
import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User,String> {

    @Query("SELECT DISTINCT owner.email FROM Reservation reservation JOIN reservation.accommodation accommodation JOIN accommodation.owner owner JOIN reservation.guest guest WHERE guest.email =:guestEmail")
    Collection<String> findOwnersForGuestReport(@Param("guestEmail") String guestEmail);

    @Query("SELECT DISTINCT guest.email FROM Reservation reservation JOIN reservation.accommodation accommodation JOIN accommodation.owner owner JOIN reservation.guest guest WHERE owner.email = :ownerEmail")
    Collection<String> findGuestsForOwnerReport(@Param("ownerEmail") String ownerEmail);

}