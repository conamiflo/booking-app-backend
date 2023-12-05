package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Analytic;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Reservation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.ReservationStatus;

import java.time.Month;
import java.time.Year;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IAnalyticService {

    List<Analytic> findAll();

    <S extends Analytic> S save(S entity);

    Optional<Analytic> findById(Long aLong);

    void deleteById(Long aLong);

    Collection<Analytic> findAllByAccommodationId(Long aLong);

    Collection<Analytic> findByMonthYearAndAccommodationId(@Param("month") Month month, @Param("year") Year year, @Param("accommodation") Long id);
}
