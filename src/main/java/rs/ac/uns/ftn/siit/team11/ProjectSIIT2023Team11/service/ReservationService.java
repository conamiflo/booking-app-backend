package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Reservation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IReservationRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService implements IReservationService {

    @Autowired
    IReservationRepository reservationRepository;

    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public <S extends Reservation> S save(S entity) {
        return reservationRepository.save(entity);
    }

    public Optional<Reservation> findById(Long id) {
        return reservationRepository.findById(id);
    }
    public void deleteById(Long id) {
        reservationRepository.deleteById(id);
    }

    public Collection<Reservation> findReservationsByAccommodation_Owner_Email(String email) {
        return reservationRepository.findReservationsByAccommodation_Owner_Email(email);
    }
    @Override
    public Collection<Reservation> findReservationsByGuestEmail(String email) {
        return reservationRepository.findReservationsByGuestEmail(email);
    }

//    @Override
//    public Collection<Reservation> findByAccommodationName(String accommodationName) {
//        return reservationRepository.findByAccommodationName(accommodationName);
//    }
}
