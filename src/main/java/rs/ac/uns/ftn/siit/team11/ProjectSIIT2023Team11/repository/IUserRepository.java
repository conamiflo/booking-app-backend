package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.User;

import java.util.List;

public interface IUserRepository extends JpaRepository<User,String> {
    List<User> findAll();

    User findByEmail(String email);

    User create(User greeting) throws Exception;

    User update(User greeting) throws Exception;

    void delete(String email);
}