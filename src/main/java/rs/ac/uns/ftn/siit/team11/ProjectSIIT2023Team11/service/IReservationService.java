package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Reservation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.ReservationStatus;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IReservationService {
    List<Reservation> findAll();

    <S extends Reservation> S save(S entity);

    Optional<Reservation> findById(Long id);

    void deleteById(Long id);

    Collection<Reservation> findAllByGuestEmail(String email);
    Collection<Reservation> findAllByAccommodationOwnerEmail(String email);
    Collection<Reservation> findAllByAccommodationName(String name);
    List<Reservation> findByStatusAndGuestEmail(@Param("status") ReservationStatus status, @Param("email") String email);
    List<Reservation> findByStatusAndOwnerEmail(@Param("status") ReservationStatus status, @Param("email") String email);


}
