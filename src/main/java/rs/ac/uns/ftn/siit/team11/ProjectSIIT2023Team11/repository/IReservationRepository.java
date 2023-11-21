package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository;

import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Reservation;

import java.util.Collection;

public interface IReservationRepository {

    Collection<Reservation> findAll();
    Collection<Reservation> findByUserEmail(String email);
    Collection<Reservation> findByAccommodationId(int accommodationId);
    Reservation findById(int reservationId);
    Reservation create(Reservation reservation) throws Exception;
    Reservation update(Reservation reservation) throws Exception;
    void delete(int reservationId);
}
