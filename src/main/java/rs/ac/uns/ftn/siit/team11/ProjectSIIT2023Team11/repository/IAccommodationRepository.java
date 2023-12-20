package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Reservation;

import java.time.LocalDate;
import java.util.Collection;

public interface IAccommodationRepository extends JpaRepository<Accommodation, Long> {

    @Query("SELECT a FROM Accommodation a " +
            "JOIN a.availability av " +
            "WHERE ((:guests IS NULL OR (a.minGuests <= :guests AND a.maxGuests >= :guests ))" +
            "AND (:location IS NULL OR lower(a.location) LIKE %:location%) " +
            "AND (cast(:startDate as date) IS NULL OR cast(:endDate as date) IS NULL OR (av.timeSlot.startDate <= :endDate AND av.timeSlot.endDate >= :startDate)))")
    Collection<Accommodation> searchAccommodationsByCriteria(
            @Param("guests") Integer guests,
            @Param("location") String location,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("SELECT r FROM Reservation r WHERE " +
            "(:startDate IS NULL OR :endDate IS NULL OR r.startDate BETWEEN :startDate AND :endDate) " +
            "AND (:accommodationName IS NULL OR r.accommodation.name LIKE %:accommodationName%) AND r.accommodation.owner.email = :email")
    Collection<Reservation> filterAccommodation(@Param("startDate") LocalDate startDate,@Param("endDate") LocalDate endDate,
                                                    @Param("accommodationName") String accommodationName, @Param("email") String email);

}
