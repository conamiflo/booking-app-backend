package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Notification;

import java.util.Collection;

public interface INotificationRepository {

    Collection<Notification> findAll();

    Notification findById(Long id);

    Notification save(Notification notification);

    Notification update(Notification notification);

    void delete(Long id);

    Collection<Notification> search(String receiver, String type);
}
