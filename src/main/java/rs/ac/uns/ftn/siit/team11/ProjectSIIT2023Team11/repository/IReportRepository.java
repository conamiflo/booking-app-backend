package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Report;

import java.util.Collection;

public interface IReportRepository {

    Collection<Report> findAll();

    Report findById(Long id);

    Report save(Report report);

    Report update(Report report);

    void delete(Long id);

    Collection<Report> search(String sender, String receiver);
}
