package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Reservation;

import java.util.Collection;

public interface IReservationService {

    Collection<Reservation> findAll();

    Collection<Reservation> findByUserEmail(String email);

    Collection<Reservation> findByAccommodationId(Long accommodationId);

    Reservation findById(Long reservationId);

    Reservation create(Reservation reservation);

    Reservation update(Reservation reservation);

    void delete(Long reservationId);
}
