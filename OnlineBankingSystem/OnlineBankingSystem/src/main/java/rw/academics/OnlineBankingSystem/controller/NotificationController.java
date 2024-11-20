package rw.academics.OnlineBankingSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rw.academics.OnlineBankingSystem.model.Notification;
import rw.academics.OnlineBankingSystem.service.NotificationService;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // Display unread notifications in Thymeleaf view
    @GetMapping("/notifications")
    public String getUnreadNotifications(Model model) {
        List<Notification> notifications = notificationService.getUnreadNotifications();
        model.addAttribute("notifications", notifications);
        return "notifications"; // Thymeleaf template "notifications.html"
    }

    // Endpoint for fetching the unread notification count
    @GetMapping("/notifications/count")
    @ResponseBody
    public Map<String, Integer> getUnreadNotificationCount() {
        int unreadCount = notificationService.getUnreadNotificationCount();
        return Collections.singletonMap("count", unreadCount);
    }

    // Mark a notification as read and redirect to notifications page
    @PostMapping("/notifications/{id}/mark-as-read")
    public String markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return "redirect:/admin/notifications"; // Redirect to refresh notifications
    }
}
