package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Analytic;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Notification;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Reservation;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IAnalyticRepository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.INotificationRepository;

import java.time.Month;
import java.time.Year;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class AnalyticService implements IAnalyticService{
    @Autowired
    private IAnalyticRepository analyticRepository;

    @Override
    public List<Analytic> findAll() {
        return analyticRepository.findAll();
    }

    @Override
    public <S extends Analytic> S save(S entity) {
        return analyticRepository.save(entity);
    }

    @Override
    public Optional<Analytic> findById(Long aLong) {
        return analyticRepository.findById(aLong);
    }

    @Override
    public void deleteById(Long aLong) {
        analyticRepository.deleteById(aLong);
    }

    @Override
    public Collection<Analytic> findAllByAccommodationId(Long aLong) {
        return analyticRepository.findAllByAccommodationId(aLong);
    }

    @Override
    public Collection<Analytic> findByMonthYearAndAccommodationId(Month month, Year year, Long id) {
        return analyticRepository.findByMonthYearAndAccommodationId(month, year, id);
    }

}
