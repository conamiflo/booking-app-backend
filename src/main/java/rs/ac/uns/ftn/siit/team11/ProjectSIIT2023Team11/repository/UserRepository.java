package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository;

import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.User;

import java.util.Collection;

@Repository
public class UserRepository implements IUserRepository {

    @Override
    public Collection<User> findAll() {
        return null;
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
