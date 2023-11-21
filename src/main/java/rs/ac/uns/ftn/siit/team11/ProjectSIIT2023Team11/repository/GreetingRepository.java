package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository;

import java.util.Collection;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Greeting;


public interface GreetingRepository {

    Collection<Greeting> findAll();

    Greeting create(Greeting greeting);

    Greeting findOne(Long id);

    Greeting update(Greeting greeting);

    void delete(Long id);

}
