package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.*;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IReservationRepository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.ReservationStatus;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

    public Collection<Reservation> findAllByGuestEmail(String email) {
        return reservationRepository.findAllByGuestEmail(email);
    }
    public Collection<Reservation> findAllByAccommodationOwnerEmail(String email) {
        return reservationRepository.findAllByAccommodationOwnerEmail(email);
    }

    @Override
    public Collection<Reservation> searchGuestReservations(LocalDate startDate, LocalDate endDate, String accommodationName,String email) {
        return reservationRepository.searchGuestReservations(startDate,endDate,accommodationName,email);
    }

    @Override
    public Collection<Reservation> searchOwnerReservations(LocalDate startDate, LocalDate endDate, String accommodationName, String email) {
        return reservationRepository.searchOwnerReservations(startDate,endDate,accommodationName,email);
    }

    public Collection<Reservation> findByStatusAndGuestEmail(ReservationStatus status, String email) {
        return reservationRepository.findByStatusAndGuestEmail(status, email);
    }

    public List<Reservation> findByStatusAndOwnerEmail(ReservationStatus status, String email) {
        return reservationRepository.findByStatusAndOwnerEmail(status, email);
    }

    @Override
    public boolean anyReservationInFuture(Accommodation accommodation) {
        for(Reservation reservation : reservationRepository.findAll()){
            if(reservation.getEndDate() > (Instant.now().getEpochSecond()) && reservation.getStatus().equals(ReservationStatus.Accepted)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean guestHasActiveReservations(String email) {
        for(Reservation reservation : reservationRepository.findAll()){
            if(email.equals(reservation.getGuest().getEmail()) && reservation.getStatus().equals(ReservationStatus.Accepted)
                    && reservation.getEndDate() > Instant.now().getEpochSecond()){
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean hasUnCancelledReservation(String guestEmail, String ownerEmail) {
        return reservationRepository.hasUnCancelledReservationWithOwnerAndStatus(guestEmail,ownerEmail,ReservationStatus.Accepted,ReservationStatus.Finished);
    }

    @Override
    public boolean canReviewAccommodation(String guestEmail, Long accommodationId) {
        Collection<Reservation> reservations = reservationRepository.findReservationsForAccommodationReview(guestEmail,accommodationId,ReservationStatus.Accepted,ReservationStatus.Finished) ;
        for (Reservation reservation: reservations) {
            Instant endDate = Instant.ofEpochSecond(reservation.getEndDate());
            if (Instant.now().isAfter(endDate) && Instant.now().isBefore(endDate.plus(7, ChronoUnit.DAYS))) {
                return true;
            }
        }
        return false;
    }
}
