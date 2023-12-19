package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.IAccountRequestRepository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.util.AccountRequest;

import java.util.List;
import java.util.Optional;

//@Service
//public class AccountRequestService implements  IAccountRequestService{
//
//    @Autowired
//    private IAccountRequestRepository accountRequestRepository;
//
//    public List<AccountRequest> findAll() {
//        return accountRequestRepository.findAll();
//    }
//    public <S extends AccountRequest> S save(S entity) {
//        return accountRequestRepository.save(entity);
//    }
//    public Optional<AccountRequest> findById(String s) {
//        return accountRequestRepository.findById(s);
//    }
//
//    public void deleteById(String s) {
//        accountRequestRepository.deleteById(s);
//    }
//}
