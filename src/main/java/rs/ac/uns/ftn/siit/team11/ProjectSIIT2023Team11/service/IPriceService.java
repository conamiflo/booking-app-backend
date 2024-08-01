package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Price;

import java.util.List;
import java.util.Optional;

public interface IPriceService {
    List<Price> findAll();

    <S extends Price> S save(S entity);

    Optional<Price> findById(Long s);

    void deleteById(Long s);
    void deletePrices(List<Price> prices);

}
