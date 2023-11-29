package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Report;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IReportRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService implements IReportService{
    @Autowired
    private IReportRepository reportRepository;


    @Override
    public List<Report> findAll() {
        return reportRepository.findAll();
    }

    @Override
    public <S extends Report> S save(S entity) {
        return reportRepository.save(entity);
    }

    @Override
    public Optional<Report> findById(Long aLong) {
        return reportRepository.findById(aLong);
    }

    @Override
    public void deleteById(Long aLong) {
        reportRepository.deleteById(aLong);
    }
}
