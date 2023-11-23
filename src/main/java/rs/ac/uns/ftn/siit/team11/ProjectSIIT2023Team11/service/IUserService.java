package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.User;

import java.util.Collection;

public interface IUserService {

    Collection<User> findAll();

    User findByEmail(String email);

    User create(User greeting) throws Exception;

    User update(User greeting) throws Exception;

    void delete(String email);

    boolean isLoginValid(String email, String password);

}
