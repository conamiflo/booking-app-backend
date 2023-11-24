package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Notification;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface INotificationService {

    List<Notification> findAll();
    <S extends Notification> S save(S entity);
    Optional<Notification> findById(Long aLong);
    void deleteById(Long aLong);
    Collection<Notification> search(String receiver, String type);
}
