package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Report;

import java.util.Collection;

public interface IReportService {

    Collection<Report> findAll();

    Report findById(Long id);

    Report create(Report report) throws Exception;

    Report update(Report report) throws Exception;

    void delete(Long id);

    Collection<Report> search(String sender, String receiver);
}
