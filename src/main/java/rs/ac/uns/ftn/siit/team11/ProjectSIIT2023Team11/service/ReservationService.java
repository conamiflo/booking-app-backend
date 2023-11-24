package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Reservation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IReservationRepository;

import java.util.Collection;

@Service
public class ReservationService implements IReservationService{

    @Autowired
    IReservationRepository reservationRepository;
    @Override
    public Collection<Reservation> findAll() {
        return null;
    }

    @Override
    public Collection<Reservation> findByUserEmail(String email) {
        return null;
    }

    @Override
    public Collection<Reservation> findByAccommodationId(Long accommodationId) {
        return null;
    }

    @Override
    public Reservation findById(Long reservationId) {
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
    public void delete(Long reservationId) {

    }
}
