package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Analytic;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Notification;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Reservation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.AnalyticDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.NotificationDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO.GuestReservationDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReservationDTO.OwnerReservationDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.AnalyticMapper;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.GuestReservationMapper;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.NotificationMapper;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.OwnerReservationMapper;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IAccommodationService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IAnalyticService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.ReservationStatus;

import java.time.Month;
import java.time.Year;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {
    @Autowired
    private IAnalyticService analyticService;
    @Autowired
    IAccommodationService accommodationService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AnalyticDTO>> getAnalytics() {
        Collection<Analytic> analytics = analyticService.findAll();
        return new ResponseEntity<Collection<AnalyticDTO>>(AnalyticMapper.mapToAnalyticsDTO(analytics), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AnalyticDTO> getAnalyticsById(@PathVariable("id") Long id) {
        Optional<Analytic> analytic = analyticService.findById(id);
        if (analytic.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(AnalyticMapper.mapToAnalyticDTO(analytic.get()), HttpStatus.OK);
    }

    @GetMapping(value = "/accommodation/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AnalyticDTO>> getAnalyticByAccommodationId(@PathVariable("id") Long id) {
        Collection<Analytic> analytics = analyticService.findAllByAccommodationId(id);
        return new ResponseEntity<>(analytics.stream()
                .map(AnalyticMapper::mapToAnalyticDTO)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

   /* @GetMapping(value = "/accommodation/filter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AnalyticDTO>> filterAccommodationAnalytics(

            @RequestParam("month") Month month,
            @RequestParam("year") Year year,
            @RequestParam("accommodation") Long id){
        System.out.println("Month: " + month);
        System.out.println("Year: " + year);
        System.out.println("Accommodation: " + id);

        Collection<Analytic> analytics = analyticService.findByMonthYearAndAccommodationId(month,year, id);
        return new ResponseEntity<>(analytics.stream()
                .map(AnalyticMapper::mapToAnalyticDTO)
                .collect(Collectors.toList()), HttpStatus.OK);

    }*/

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AnalyticDTO> createAnalytic(@RequestBody AnalyticDTO analyticDTO) throws Exception {

        if (accommodationService.findById(analyticDTO.getAccommodationId()).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try{
            analyticService.save(AnalyticMapper.mapDtoToAnalytic(analyticDTO, accommodationService));
            return new ResponseEntity<>(analyticDTO, HttpStatus.CREATED);
        } catch (Exception exception){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AnalyticDTO> updateAnalytic(@RequestBody AnalyticDTO analyticDTO, @PathVariable Long id) throws Exception {
        Optional<Analytic> existingAnalytic = analyticService.findById(id);
        if (existingAnalytic.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Analytic updatedAnalytic = analyticService.save(AnalyticMapper.mapDtoToAnalytic(analyticDTO, accommodationService));
        return new ResponseEntity<>(AnalyticMapper.mapToAnalyticDTO(updatedAnalytic), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAnalytic(@PathVariable("id") Long id) {
        analyticService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
