package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository;

import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Reservation;

import java.util.Collection;

@Repository
public class ReservationRepository implements  IReservationRepository{

    @Override
    public Collection<Reservation> findAll() {
        return null;
    }

    @Override
    public Collection<Reservation> findByUserEmail(String email) {
        return null;
    }

    @Override
    public Collection<Reservation> findByAccommodationId(int accommodationId) {
        return null;
    }

    @Override
    public Reservation findById(int reservationId) {
        return null;
    }

    @Override
    public Reservation create(Reservation reservation) throws Exception {
        return null;
    }

    @Override
    public Reservation update(Reservation reservation) throws Exception {
        return null;
    }

    @Override
    public void delete(int reservationId) {

    }
}
