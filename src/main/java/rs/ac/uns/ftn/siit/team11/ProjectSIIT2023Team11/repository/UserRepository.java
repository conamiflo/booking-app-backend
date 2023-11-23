package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository;

import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.User;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.UserRole;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

@Repository
public class UserRepository implements IUserRepository {

    @Override
    public Collection<User> findAll() {

        Collection<User> users = new ArrayList<User>();
        users.add(new User("example1@example.com", "password1", true, "John", "Doe", "123 Main St", "123-456-7890", UserRole.GUEST));
        users.add(new User("example2@example.com", "password2", true, "Alice", "Smith", "456 Elm St", "987-654-3210", UserRole.ADMIN));
        users.add(new User("example3@example.com", "password3", true, "Bob", "Johnson", "789 Oak St", "111-222-3333", UserRole.GUEST));
        return users;
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
