package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Notification;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.INotificationRepository;

import java.util.Collection;

@Service
public class NotificationService implements INotificationService {

    private final INotificationRepository notificationRepository;

    @Autowired
    public NotificationService(INotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Override
    public Collection<Notification> findAll() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification findById(Long id) {
        return notificationRepository.findById(id);
    }

    @Override
    public Notification create(Notification notification) throws Exception {
        if (notification.getId() != null) {
            throw new IllegalArgumentException("Notification ID should be null for creation.");
        }

        return notificationRepository.save(notification);
    }

    @Override
    public Notification update(Notification notification) throws Exception {
        if (notification.getId() == null) {
            throw new IllegalArgumentException("Notification ID cannot be null for update.");
        }

        Notification existingNotification = notificationRepository.findById(notification.getId());
        if (existingNotification == null) {
            throw new IllegalArgumentException("Notification with ID " + notification.getId() + " not found.");
        }

        return notificationRepository.update(notification);
    }

    @Override
    public void delete(Long id) {
        notificationRepository.delete(id);
    }

    @Override
    public Collection<Notification> search(String receiver, String type) {
        return notificationRepository.search(receiver, type);
    }
}
