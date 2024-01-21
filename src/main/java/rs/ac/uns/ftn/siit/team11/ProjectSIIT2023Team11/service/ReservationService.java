package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.*;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO.AccommodationNumberOfReservations;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO.AccommodationProfitDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO.AccommodationYearlyNumberOfReservations;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO.AccommodationYearlyProfitDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IAccommodationRepository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IReservationRepository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.ReservationStatus;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class ReservationService implements IReservationService {

    @Autowired
    IReservationRepository reservationRepository;

    @Autowired

    IAvailabilityService availabilityService;


    IAccommodationRepository accommodationRepository;


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
    public Collection<Reservation> searchGuestReservations(Long startDate, Long endDate, String accommodationName,String email) {
        return reservationRepository.searchGuestReservations(startDate,endDate,accommodationName,email);
    }

    @Override
    public Collection<Reservation> searchOwnerReservations(Long startDate, Long endDate, String accommodationName, String email) {
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
    public int countCancellationsForGuest(String guestId) {
        return (int) reservationRepository.findAll().stream()
                .filter(reservation -> guestId.equals(reservation.getGuest().getEmail()) && ReservationStatus.Cancelled.equals(reservation.getStatus()))
                .count();
    }

    @Override
    public void declineWaitingReservations(Long startDate, Long endDate, Long accommodationId) {
        for (Reservation reservation : reservationRepository.findAll()) {
            if ((Objects.equals(reservation.getAccommodation().getId(), accommodationId)) &&
                    ((reservation.getStartDate() < endDate && reservation.getEndDate() > startDate) ||
                            (startDate < reservation.getEndDate() && endDate > reservation.getStartDate()))) {

                if(reservation.getStatus() == ReservationStatus.Waiting){
                    reservation.setStatus(ReservationStatus.Declined);
                    save(reservation);
                }
            }
        }
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

    @Override
    public void declineBlockedGuestsReservations(User guest){
        Collection<Reservation> reservations = reservationRepository.findAllByGuestEmail(guest.getEmail());
        for(Reservation r : reservations){
            if(r.getStatus() == ReservationStatus.Waiting){
                r.setStatus(ReservationStatus.Declined);
            }
            if(r.getStatus() == ReservationStatus.Accepted) {
                r.setStatus(ReservationStatus.Declined);
                availabilityService.returnCancelledAvailability(r.getStartDate(), r.getEndDate(), r.getAccommodation());
            }
        }

        for(Reservation r: reservations){
            save(r);
        }
    }

    @Override
    public Optional<Reservation> createNewReservation(Reservation newReservationEntry) {
        newReservationEntry.calculatePrice();
        newReservationEntry.setStatus(ReservationStatus.Waiting);

        if(newReservationEntry.getAccommodation().isAutomaticApproval()){
            availabilityService.fitAcceptedReservation(newReservationEntry.getStartDate(),newReservationEntry.getEndDate(),newReservationEntry.getAccommodation());
            newReservationEntry.setStatus(ReservationStatus.Accepted);
            return  Optional.ofNullable(save(newReservationEntry));
        }

        return  Optional.ofNullable(save(newReservationEntry));
    }

    @Override
    public Collection<AccommodationNumberOfReservations> getStatisticNumberOfReservations(Long startDate, Long endDate, String username) {
        List<AccommodationNumberOfReservations> accommodationsNumberOfReservations = new ArrayList<>();
        for(Accommodation accommodation: accommodationRepository.findAll()){
            if(!accommodation.getOwner().getEmail().equals(username)) continue;

            AccommodationNumberOfReservations accommodationNumberOfReservations = new AccommodationNumberOfReservations(accommodation.getName(),0);

            for(Reservation reservation: findAll()){
                if(!reservation.getAccommodation().getId().equals(accommodation.getId())) continue;

                if(reservation.getEndDate()<= endDate && reservation.getEndDate() >= startDate) accommodationNumberOfReservations.increaseNumberOfReservations();

            }
            accommodationsNumberOfReservations.add(accommodationNumberOfReservations);
        }
        return accommodationsNumberOfReservations;
    }

    @Override
    public Collection<AccommodationYearlyNumberOfReservations> getStatisticYearlyNumberOfReservations(Integer year, String username) {
        List<AccommodationYearlyNumberOfReservations> yearlyReservationsList = new ArrayList<>();

        for (Accommodation accommodation : accommodationRepository.findAll()) {
            if (!accommodation.getOwner().getEmail().equals(username)) continue;

            AccommodationYearlyNumberOfReservations yearlyReservations = new AccommodationYearlyNumberOfReservations();
            yearlyReservations.setAccommodationName(accommodation.getName());
            yearlyReservations.setMonthlyNumberOfReservations(new ArrayList<>());

            for (int month = 1; month <= 12; month++) {
                Long startDate = getStartOfMonthTimestamp(year, month);
                Long endDate = getEndOfMonthTimestamp(year, month);

                int monthlyReservationsCount = getMonthlyReservationsCount(accommodation, startDate, endDate);
                yearlyReservations.getMonthlyNumberOfReservations().add(monthlyReservationsCount);
            }

            yearlyReservationsList.add(yearlyReservations);
        }

        return yearlyReservationsList;
    }

    private Long getStartOfMonthTimestamp(Integer year, int month) {
        LocalDate startOfMonth = LocalDate.of(year, month, 1);
        return startOfMonth.atStartOfDay(ZoneId.systemDefault()).toEpochSecond();
    }

    private Long getEndOfMonthTimestamp(Integer year, int month) {
        LocalDate endOfMonth = LocalDate.of(year, month, YearMonth.of(year, month).lengthOfMonth());
        return endOfMonth.atTime(LocalTime.MAX).atZone(ZoneId.systemDefault()).toEpochSecond();
    }

    private int getMonthlyReservationsCount(Accommodation accommodation, Long startDate, Long endDate) {
        int monthlyReservationsCount = 0;

        for (Reservation reservation : findAll()) {
            if (reservation.getAccommodation().getId().equals(accommodation.getId())
                    && reservation.getEndDate() <= endDate
                    && reservation.getEndDate() >= startDate) {
                monthlyReservationsCount++;
            }
        }

        return monthlyReservationsCount;
    }


    @Override
    public Collection<AccommodationProfitDTO> getStatisticProfit(Long startDate, Long endDate, String username) {
        List<AccommodationProfitDTO> accommodationsNumberOfReservations = new ArrayList<>();
        for(Accommodation accommodation: accommodationRepository.findAll()){
            if(!accommodation.getOwner().getEmail().equals(username)) continue;

            AccommodationProfitDTO accommodationNumberOfReservations = new AccommodationProfitDTO(accommodation.getName(),0.0);

            for(Reservation reservation: findAll()){
                if(!reservation.getAccommodation().getId().equals(accommodation.getId())) continue;
                if(!reservation.getStatus().equals(ReservationStatus.Accepted)) continue;


                if(reservation.getEndDate()<= endDate && reservation.getEndDate() >= startDate) accommodationNumberOfReservations.IncreaseProfit(reservation.getPrice());

            }
            accommodationsNumberOfReservations.add(accommodationNumberOfReservations);
        }
        return accommodationsNumberOfReservations;
    }



    @Override
    public Collection<AccommodationYearlyProfitDTO> getStatisticYearlyProfit(Integer year, String username) {
        List<AccommodationYearlyProfitDTO> yearlyProfitList = new ArrayList<>();

        for (Accommodation accommodation : accommodationRepository.findAll()) {
            if (!accommodation.getOwner().getEmail().equals(username)) continue;

            AccommodationYearlyProfitDTO yearlyProfit = new AccommodationYearlyProfitDTO();
            yearlyProfit.setAccommodationName(accommodation.getName());
            yearlyProfit.setMonthlyProfits(new ArrayList<>());

            for (int month = 1; month <= 12; month++) {
                Long startOfMonth = getStartOfMonthTimestamp(year, month);
                Long endOfMonth = getEndOfMonthTimestamp(year, month);

                double monthlyProfit = getMonthlyProfit(accommodation, startOfMonth, endOfMonth);
                yearlyProfit.getMonthlyProfits().add(monthlyProfit);
            }

            yearlyProfitList.add(yearlyProfit);
        }

        return yearlyProfitList;
    }

    @Override
    public byte[] generatePdfContent(Collection<AccommodationNumberOfReservations> reservations,
                                     Collection<AccommodationProfitDTO> profits,
                                     Collection<AccommodationYearlyNumberOfReservations> yearlyReservations,
                                     Collection<AccommodationYearlyProfitDTO> yearlyProfits) {

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
             PDDocument document = new PDDocument()) {

            // Add a page
            PDPage page = new PDPage();
            document.addPage(page);

            // Create a content stream
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Write your statistical data to the content stream
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 700); // Set starting position
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

            contentStream.showText("Accommodation Number of Reservations:");
            contentStream.newLineAtOffset(0, -12); 

            for (AccommodationNumberOfReservations reservation : reservations) {
                contentStream.showText("    Accommodation name: " + reservation.getAccommodationName());
                contentStream.newLineAtOffset(0, -12); 
                contentStream.showText("    Number of reservations: " + reservation.getNumberOfReservations());
                contentStream.newLineAtOffset(0, -12); 
                contentStream.newLineAtOffset(0, -12); 
            }

            contentStream.showText("Accommodation Profit:");
            contentStream.newLineAtOffset(0, -12); 

            for (AccommodationProfitDTO profit : profits) {
                contentStream.showText("    Accommodation name: " + profit.getAccommodationName());
                contentStream.newLineAtOffset(0, -12); 
                contentStream.showText("    Profit: $" + profit.getProfit());
                contentStream.newLineAtOffset(0, -12); 
                contentStream.newLineAtOffset(0, -12); 
            }

            contentStream.showText("Accommodation Yearly Number of Reservations:");
            contentStream.newLineAtOffset(0, -12); 

            for (AccommodationYearlyNumberOfReservations yearlyNumberOfReservations : yearlyReservations) {
                contentStream.showText("    Accommodation name: " + yearlyNumberOfReservations.getAccommodationName());
                contentStream.newLineAtOffset(0, -12); 
                contentStream.showText("    Number of reservations: ");
                contentStream.newLineAtOffset(0, -12); 
                contentStream.showText("        Jan: " + yearlyNumberOfReservations.getMonthlyNumberOfReservations().get(0));
                contentStream.newLineAtOffset(0, -12); 
                contentStream.showText("        Feb: "+ yearlyNumberOfReservations.getMonthlyNumberOfReservations().get(1));
                contentStream.newLineAtOffset(0, -12); 
                contentStream.showText("        Mar: "+ yearlyNumberOfReservations.getMonthlyNumberOfReservations().get(2));
                contentStream.newLineAtOffset(0, -12); 
                contentStream.showText("        Apr: "+ yearlyNumberOfReservations.getMonthlyNumberOfReservations().get(3));
                contentStream.newLineAtOffset(0, -12); 
                contentStream.showText("        May: "+ yearlyNumberOfReservations.getMonthlyNumberOfReservations().get(4));
                contentStream.newLineAtOffset(0, -12); 
                contentStream.showText("        Jun: "+ yearlyNumberOfReservations.getMonthlyNumberOfReservations().get(5));
                contentStream.newLineAtOffset(0, -12); 
                contentStream.showText("        Jul: "+ yearlyNumberOfReservations.getMonthlyNumberOfReservations().get(6));
                contentStream.newLineAtOffset(0, -12); 
                contentStream.showText("        Avg: "+ yearlyNumberOfReservations.getMonthlyNumberOfReservations().get(7));
                contentStream.newLineAtOffset(0, -12); 
                contentStream.showText("        Sep: "+ yearlyNumberOfReservations.getMonthlyNumberOfReservations().get(8));
                contentStream.newLineAtOffset(0, -12); 
                contentStream.showText("        Oct: "+ yearlyNumberOfReservations.getMonthlyNumberOfReservations().get(9));
                contentStream.newLineAtOffset(0, -12); 
                contentStream.showText("        Nov: "+ yearlyNumberOfReservations.getMonthlyNumberOfReservations().get(10));
                contentStream.newLineAtOffset(0, -12); 
                contentStream.showText("        Dec: "+ yearlyNumberOfReservations.getMonthlyNumberOfReservations().get(11));
                
                contentStream.newLineAtOffset(0, -12); 
                contentStream.newLineAtOffset(0, -12); 
            }

            contentStream.showText("Accommodation Yearly Profit:");
            contentStream.newLineAtOffset(0, -12); 

            for (AccommodationYearlyProfitDTO accommodationYearlyProfitDTO : yearlyProfits) {
                contentStream.showText("    Accommodation name: " + accommodationYearlyProfitDTO.getAccommodationName());
                contentStream.newLineAtOffset(0, -12); 
                contentStream.showText("    Number of reservations: ");
                contentStream.newLineAtOffset(0, -12); 
                contentStream.showText("        Jan: $" + accommodationYearlyProfitDTO.getMonthlyProfits().get(0));
                contentStream.newLineAtOffset(0, -12); 
                contentStream.showText("        Feb: $"+ accommodationYearlyProfitDTO.getMonthlyProfits().get(1));
                contentStream.newLineAtOffset(0, -12); 
                contentStream.showText("        Mar: $"+ accommodationYearlyProfitDTO.getMonthlyProfits().get(2));
                contentStream.newLineAtOffset(0, -12); 
                contentStream.showText("        Apr: $"+ accommodationYearlyProfitDTO.getMonthlyProfits().get(3));
                contentStream.newLineAtOffset(0, -12); 
                contentStream.showText("        May: $"+ accommodationYearlyProfitDTO.getMonthlyProfits().get(4));
                contentStream.newLineAtOffset(0, -12); 
                contentStream.showText("        Jun: $"+ accommodationYearlyProfitDTO.getMonthlyProfits().get(5));
                contentStream.newLineAtOffset(0, -12); 
                contentStream.showText("        Jul: $"+ accommodationYearlyProfitDTO.getMonthlyProfits().get(6));
                contentStream.newLineAtOffset(0, -12); 
                contentStream.showText("        Avg: $"+ accommodationYearlyProfitDTO.getMonthlyProfits().get(7));
                contentStream.newLineAtOffset(0, -12); 
                contentStream.showText("        Sep: $"+ accommodationYearlyProfitDTO.getMonthlyProfits().get(8));
                contentStream.newLineAtOffset(0, -12); 
                contentStream.showText("        Oct: $"+ accommodationYearlyProfitDTO.getMonthlyProfits().get(9));
                contentStream.newLineAtOffset(0, -12); 
                contentStream.showText("        Nov: $"+ accommodationYearlyProfitDTO.getMonthlyProfits().get(10));
                contentStream.newLineAtOffset(0, -12); 
                contentStream.showText("        Dec: $"+ accommodationYearlyProfitDTO.getMonthlyProfits().get(11));

                contentStream.newLineAtOffset(0, -12); 
                contentStream.newLineAtOffset(0, -12); 
            }

            // Repeat the process for other statistical data

            contentStream.endText();
            contentStream.close();

            // Save the document to the output stream
            document.save(outputStream);

            return outputStream.toByteArray();
        } catch (IOException e) {
            // Handle exceptions
            e.printStackTrace();
            return new byte[0];
        }
    }

    private double getMonthlyProfit(Accommodation accommodation, Long startDate, Long endDate) {
        double monthlyProfit = 0.0;

        for (Reservation reservation : findAll()) {
            if (reservation.getAccommodation().getId().equals(accommodation.getId())
                    && reservation.getEndDate() <= endDate
                    && reservation.getEndDate() >= startDate
                    && reservation.getStatus().equals(ReservationStatus.Accepted)) {
                monthlyProfit += reservation.getPrice();
            }
        }

        return monthlyProfit;
    }
}
