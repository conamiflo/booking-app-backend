package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Notification;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface INotificationRepository extends JpaRepository<Notification,Long> {
    Collection<Notification> search(String User, String type);
}
