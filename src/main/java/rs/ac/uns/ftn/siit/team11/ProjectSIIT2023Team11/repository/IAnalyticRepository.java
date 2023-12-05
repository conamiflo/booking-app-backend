package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Analytic;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Reservation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.ReservationStatus;

import java.time.Month;
import java.time.Year;
import java.util.Collection;

@Repository
public interface IAnalyticRepository extends JpaRepository<Analytic,Long> {
    Collection<Analytic> findAllByAccommodationId(Long id);
    @Query("SELECT a FROM Analytic a WHERE a.month = :month AND a.year = :year AND a.accommodation = :id")
    Collection<Analytic> findByMonthYearAndAccommodationId(@Param("month")Month month, @Param("year")Year year, @Param("id")Long id);
}
