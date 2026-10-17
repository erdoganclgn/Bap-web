package com.mersin.bap_otomasyon.service;

import com.mersin.bap_otomasyon.entity.Notification;

import java.util.List;
import java.util.UUID;

public interface NotificationService {
    List<Notification> getUserNotifications(UUID userId);
    List<Notification> getUnreadNotifications(UUID userId);
    long getUnreadCount(UUID userId);
    Notification sendNotification(UUID userId, String baslik, String mesaj);
    void markAsRead(UUID notificationId);
    void markAllAsRead(UUID userId);
}
