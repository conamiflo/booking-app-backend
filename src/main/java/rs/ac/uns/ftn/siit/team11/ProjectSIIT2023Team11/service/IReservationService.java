package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Reservation;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IReservationService {
    List<Reservation> findAll();

    <S extends Reservation> S save(S entity);

    Optional<Reservation> findById(Long id);

    void deleteById(Long id);

    Collection<Reservation> findReservationsByAccommodation_Owner_Email(String email);
    Collection<Reservation> findReservationsByGuestEmail(String email);
//    Collection<Reservation> findByAccommodationName(String accommodationName);

}
