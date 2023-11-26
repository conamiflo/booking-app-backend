package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Reservation;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IRepositoryService {
    List<Reservation> findAll();

    <S extends Reservation> S save(S entity);

    Optional<Reservation> findById(Long id);

    void deleteById(Long id);

    Collection<Reservation> findReservationsByOwner(String owner);


    Collection<Reservation> findReservationsByGuest(String guest);

    Collection<Reservation> findReservationsByAccommodation(Long accommodationId);
}
