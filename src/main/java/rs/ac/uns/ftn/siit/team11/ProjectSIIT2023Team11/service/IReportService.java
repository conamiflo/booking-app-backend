package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Report;

import java.util.List;
import java.util.Optional;

public interface IReportService {
    List<Report> findAll();

    <S extends Report> S save(S entity);

    Optional<Report> findById(Long aLong);

    void deleteById(Long aLong);
}
