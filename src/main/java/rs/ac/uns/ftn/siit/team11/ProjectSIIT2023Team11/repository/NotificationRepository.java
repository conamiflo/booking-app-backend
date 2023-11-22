package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository;

import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Notification;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class NotificationRepository implements INotificationRepository {

    private final Map<Long, Notification> notifications = new HashMap<>();
    private long nextId = 1;

    @Override
    public Collection<Notification> findAll() {
        return notifications.values();
    }

    @Override
    public Notification findById(Long id) {
        return notifications.get(id);
    }

    @Override
    public Notification save(Notification notification) {
        if (notification.getId() == null) {
            // If the notification doesn't have an ID, assign a new one
            notification.setId(nextId);
            nextId++;
        }
        notifications.put(notification.getId(), notification);
        return notification;
    }

    @Override
    public Notification update(Notification notification) {
        if (notifications.containsKey(notification.getId())) {
            // If the notification with the given ID exists, update it
            notifications.put(notification.getId(), notification);
            return notification;
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        notifications.remove(id);
    }

    @Override
    public Collection<Notification> search(String receiver, String type) {
        return notifications.values();
    }
}
