package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Reservation;

import java.util.Collection;

@Service
public class ReservationService implements IReservationService{
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
    public Reservation create(Reservation reservation) {
        return null;
    }

    @Override
    public Reservation update(Reservation reservation) {
        return null;
    }

    @Override
    public void delete(int reservationId) {

    }
}
