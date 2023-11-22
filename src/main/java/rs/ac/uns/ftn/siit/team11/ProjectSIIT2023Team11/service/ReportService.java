package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Report;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IReportRepository;

import java.util.Collection;

@Service
public class ReportService implements IReportService {

    private final IReportRepository reportRepository;

    @Autowired
    public ReportService(IReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    public Collection<Report> findAll() {
        return reportRepository.findAll();
    }

    @Override
    public Report findById(Long id) {
        return reportRepository.findById(id);
    }

    @Override
    public Report create(Report report) throws Exception {
        if (report.getId() != null) {
            throw new IllegalArgumentException("Report ID should be null for creation.");
        }

        return reportRepository.save(report);
    }

    @Override
    public Report update(Report report) throws Exception {
        if (report.getId() == null) {
            throw new IllegalArgumentException("Report ID cannot be null for update.");
        }

        Report existingReport = reportRepository.findById(report.getId());
        if (existingReport == null) {
            throw new IllegalArgumentException("Report with ID " + report.getId() + " not found.");
        }

        return reportRepository.update(report);
    }

    @Override
    public void delete(Long id) {
        reportRepository.delete(id);
    }

    @Override
    public Collection<Report> search(String sender, String receiver) {
        return reportRepository.search(sender, receiver);
    }
}
