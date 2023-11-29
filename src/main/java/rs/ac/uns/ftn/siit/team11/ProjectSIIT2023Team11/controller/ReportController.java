package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Report;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.dto.ReportDTO;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.mapper.ReportMapper;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IReportService;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service.IUserService;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    @Autowired
    private IReportService reportService;

    @Autowired
    private IUserService userService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ReportDTO>> getReports() {
        Collection<Report> reports = reportService.findAll();
        return new ResponseEntity<Collection<ReportDTO>>(ReportMapper.mapToReportsDTO(reports), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReportDTO> getReportById(@PathVariable("id") Long id) {
        Optional<Report> report = reportService.findById(id);
        if (report.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(ReportMapper.mapToReportDTO(report.get()), HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReportDTO> createReport(@RequestBody ReportDTO reportDTO) throws Exception {

        if (userService.findById(reportDTO.getSenderEmail()).isEmpty() ||
                userService.findById(reportDTO.getReceiverEmail()).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try{
            reportService.save(ReportMapper.mapDtoToReport(reportDTO, userService));
            return new ResponseEntity<>(reportDTO, HttpStatus.CREATED);
        } catch (Exception exception){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReportDTO> updateReport(@RequestBody ReportDTO reportDTO, @PathVariable Long id) throws Exception {
        Optional<Report> existingReport = reportService.findById(id);
        if (existingReport.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Report updatedReport = reportService.save(ReportMapper.mapDtoToReport(reportDTO, userService));
        return new ResponseEntity<>(ReportMapper.mapToReportDTO(updatedReport), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable("id") Long id) {
        reportService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
