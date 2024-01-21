package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Accommodation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Guest;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Owner;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Reservation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO.AccommodationNumberOfReservations;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO.AccommodationProfitDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO.AccommodationYearlyNumberOfReservations;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO.AccommodationYearlyProfitDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.ReservationStatus;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface IReservationService {
    List<Reservation> findAll();

    <S extends Reservation> S save(S entity);

    Optional<Reservation> findById(Long id);

    void deleteById(Long id);

    Collection<Reservation> findAllByGuestEmail(String email);
    Collection<Reservation> findAllByAccommodationOwnerEmail(String email);

    Collection<Reservation> searchGuestReservations(@Param("startDate") Long startDate, @Param("endDate") Long endDate,
                                                           @Param("name") String accommodationName,@Param("email") String email);
    Collection<Reservation> searchOwnerReservations(@Param("startDate") Long startDate,@Param("endDate") Long endDate,
                                                    @Param("accommodationName") String accommodationName, @Param("email") String email);
    Collection<Reservation> findByStatusAndGuestEmail(@Param("status") ReservationStatus status, @Param("email") String email);
    Collection<Reservation> findByStatusAndOwnerEmail(@Param("status") ReservationStatus status, @Param("email") String email);


    boolean anyReservationInFuture(Accommodation accommodation);

    boolean guestHasActiveReservations(String email);


    int countCancellationsForGuest(String guestId);


    void declineWaitingReservations(Long startDate, Long endDate, Long accommodationId);

    boolean hasUnCancelledReservation(@Param("guestEmail") String guestEmail, @Param("ownerEmail") String ownerEmail);

    boolean canReviewAccommodation(@Param("guestEmail") String guestEmail, @Param("accommodationId") Long accommodationId);

    Optional<Reservation> createNewReservation(Reservation newReservationEntry);

    Collection<AccommodationNumberOfReservations> getStatisticNumberOfReservations(Long startDate, Long endDate, String username);

    Collection<AccommodationProfitDTO> getStatisticProfit(Long startDate, Long endDate, String username);

    Collection<AccommodationYearlyNumberOfReservations> getStatisticYearlyNumberOfReservations(Integer year, String username);

    Collection<AccommodationYearlyProfitDTO> getStatisticYearlyProfit(Integer year, String username);

    byte[] generatePdfContent(Collection<AccommodationNumberOfReservations> accommodationNumberOfReservations, Collection<AccommodationProfitDTO> accommodationProfit, Collection<AccommodationYearlyNumberOfReservations> accommodationYearlyNumberOfReservations, Collection<AccommodationYearlyProfitDTO> accommodationYearlyProfitDTOS);
    Collection<Reservation> getReservationsByAccommodationId(Long id);

}
