package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Notification;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.INotificationRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService implements INotificationService {

    @Autowired
    INotificationRepository notificationRepository;

    @Override
    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    public <S extends Notification> S save(S entity) {
        return notificationRepository.save(entity);
    }

    @Override
    public Optional<Notification> findById(Long aLong) {
        return notificationRepository.findById(aLong);
    }

    public void deleteById(Long aLong) {
        notificationRepository.deleteById(aLong);
    }

    @Override
    public Collection<Notification> search(String receiver, String type) {
        return notificationRepository.search(receiver, type);
    }
}
