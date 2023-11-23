package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.User;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.UserRepository;

import java.util.Collection;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByEmail(String email) {
        return null;
    }

    @Override
    public User create(User greeting) throws Exception {
        return null;
    }

    @Override
    public User update(User greeting) throws Exception {
        return null;
    }

    @Override
    public void delete(String email) {

    }
}
