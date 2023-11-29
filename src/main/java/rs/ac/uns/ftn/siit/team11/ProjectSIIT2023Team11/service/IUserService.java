package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<User> findAll();

    <S extends User> S save(S entity);

    Optional<User> findById(String s);

    void deleteById(String s);
    boolean isLoginValid(String email, String password);

}