package rw.academics.OnlineBankingSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.academics.OnlineBankingSystem.model.Notification;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Find all unread notifications
    List<Notification> findByIsReadFalse();
    int countByIsReadFalse(); // Counts notifications where isRead is false
}
