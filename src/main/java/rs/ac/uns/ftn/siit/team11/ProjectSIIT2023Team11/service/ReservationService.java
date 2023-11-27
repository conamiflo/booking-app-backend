package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Reservation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IReservationRepository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.ReservationStatus;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

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

    public Collection<Reservation> findAllByGuestEmail(String email) {
        return reservationRepository.findAllByGuestEmail(email);
    }
    public Collection<Reservation> findAllByAccommodationOwnerEmail(String email) {
        return reservationRepository.findAllByAccommodationOwnerEmail(email);
    }

    public Collection<Reservation> findByStatusAndGuestEmail(ReservationStatus status, String email) {
        return reservationRepository.findByStatusAndGuestEmail(status, email);
    }

    public List<Reservation> findByStatusAndOwnerEmail(ReservationStatus status, String email) {
        return reservationRepository.findByStatusAndOwnerEmail(status, email);
    }
}