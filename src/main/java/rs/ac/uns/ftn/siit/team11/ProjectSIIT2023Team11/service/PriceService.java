package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Price;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IPriceRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PriceService implements IPriceService{
    @Autowired
    IPriceRepository repository;

    public List<Price> findAll() {
        return repository.findAll();
    }

    public <S extends Price> S save(S entity) {
        return repository.save(entity);
    }

    public Optional<Price> findById(Long s) {
        return repository.findById(s);
    }

    public void deleteById(Long s) {
        repository.deleteById(s);
    }
}
