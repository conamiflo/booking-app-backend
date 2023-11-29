package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository;

import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Report;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class ReportRepository implements IReportRepository {

    private final Map<Long, Report> reports = new HashMap<>();
    private long nextId = 1;

    @Override
    public Collection<Report> findAll() {
        return reports.values();
    }

    @Override
    public Report findById(Long id) {
        return reports.get(id);
    }

    @Override
    public Report save(Report report) {
        if (report.getId() == null) {
            // If the report doesn't have an ID, assign a new one
            report.setId(nextId);
            nextId++;
        }
        reports.put(report.getId(), report);
        return report;
    }

    @Override
    public Report update(Report report) {
        if (reports.containsKey(report.getId())) {
            // If the report with the given ID exists, update it
            reports.put(report.getId(), report);
            return report;
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        reports.remove(id);
    }

    @Override
    public Collection<Report> search(String sender, String receiver) {
        return reports.values();
    }
}
