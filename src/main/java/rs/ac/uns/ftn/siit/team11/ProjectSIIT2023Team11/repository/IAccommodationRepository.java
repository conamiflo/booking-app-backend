package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Reservation;

import java.time.LocalDate;
import java.util.Collection;

public interface IAccommodationRepository extends JpaRepository<Accommodation, Long> {

    @Query("SELECT r FROM Reservation r WHERE " +
            "(:startDate IS NULL OR :endDate IS NULL OR r.startDate BETWEEN :startDate AND :endDate) " +
            "AND (:accommodationName IS NULL OR r.accommodation.name LIKE %:accommodationName%) AND r.guest.email = :email")
    Collection<Reservation> searchAccommodation(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,
                                                    @Param("accommodationName") String accommodationName, @Param("email") String email);

    @Query("SELECT r FROM Reservation r WHERE " +
            "(:startDate IS NULL OR :endDate IS NULL OR r.startDate BETWEEN :startDate AND :endDate) " +
            "AND (:accommodationName IS NULL OR r.accommodation.name LIKE %:accommodationName%) AND r.accommodation.owner.email = :email")
    Collection<Reservation> filterAccommodation(@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate,
                                                    @Param("accommodationName") String accommodationName, @Param("email") String email);


}
