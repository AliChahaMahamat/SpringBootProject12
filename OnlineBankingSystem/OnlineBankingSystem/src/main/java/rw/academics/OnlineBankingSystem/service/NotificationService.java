package rw.academics.OnlineBankingSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.academics.OnlineBankingSystem.model.Notification;
import rw.academics.OnlineBankingSystem.repository.NotificationRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public void createNotification(String message) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setCreatedAt(LocalDateTime.now());
        notificationRepository.save(notification);
    }

    public List<Notification> getUnreadNotifications() {
        List<Notification> notifications = notificationRepository.findByIsReadFalse();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // Format createdAt for each notification
        for (Notification notification : notifications) {
            notification.setFormattedCreatedAt(notification.getCreatedAt().format(formatter));
        }

        return notifications;
    }

    public void markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id).orElseThrow();
        notification.setRead(true);
        notificationRepository.save(notification);
    }
    public int getUnreadNotificationCount() {

        return notificationRepository.countByIsReadFalse();
    }

}
