package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Notification;

import java.util.Collection;

public interface INotificationService {

    Collection<Notification> findAll();

    Notification findById(Long id);

    Notification create(Notification notification) throws Exception;

    Notification update(Notification notification) throws Exception;

    void delete(Long id);

    Collection<Notification> search(String receiver, String type);
}
