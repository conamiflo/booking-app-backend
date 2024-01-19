package rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.service;

import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.domain.Notification;
import rs.ac.uns.ftn.siit.team11.ProjectSIIT2023Team11.repository.INotificationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService implements INotificationService{
    @Autowired
    private INotificationRepository notificationRepository;


    @Override
    public List<Notification> findAll() {
        return notificationRepository.findAll();
    }

    @Override
    public <S extends Notification> S save(S entity) {
        return notificationRepository.save(entity);
    }

    @Override
    public Optional<Notification> findById(Long aLong) {
        return notificationRepository.findById(aLong);
    }

    @Override
    public void deleteById(Long aLong) {
        notificationRepository.deleteById(aLong);
    }

    @Override
    public List<Notification> findAllUsersNotifications(String email) {
        List<Notification> usersNotifications = new ArrayList<>();
        for(Notification notification: notificationRepository.findAll()){
            if(notification.getReceiver().getEmail().equals(email))
                usersNotifications.add(notification);
        }
        return usersNotifications;
    }
}
