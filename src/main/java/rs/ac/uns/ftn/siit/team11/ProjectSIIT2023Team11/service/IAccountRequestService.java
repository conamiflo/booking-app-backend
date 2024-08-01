package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.AccountRequest;

import java.util.List;
import java.util.Optional;

public interface IAccountRequestService {

    List<AccountRequest> findAll();

    <S extends AccountRequest> S save(S entity);

    Optional<AccountRequest> findById(String s);

    void deleteById(String s);

}
