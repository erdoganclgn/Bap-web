package com.mersin.bap_otomasyon.service.impl;

import com.mersin.bap_otomasyon.entity.Notification;
import com.mersin.bap_otomasyon.entity.User;
import com.mersin.bap_otomasyon.entity.enums.BildirimTipi;
import com.mersin.bap_otomasyon.repository.NotificationRepository;
import com.mersin.bap_otomasyon.repository.UserRepository;
import com.mersin.bap_otomasyon.service.NotificationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository,
                                   UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Notification> getUserNotifications(UUID userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Notification> getUnreadNotifications(UUID userId) {
        return notificationRepository.findByUserIdAndOkunduMuFalse(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public long getUnreadCount(UUID userId) {
        return notificationRepository.countByUserIdAndOkunduMuFalse(userId);
    }

    @Override
    public Notification sendNotification(UUID userId, String baslik, String mesaj) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Kullanici bulunamadi: " + userId));
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setBaslik(baslik);
        notification.setMesaj(mesaj);
        notification.setTip(BildirimTipi.Sistem);
        return notificationRepository.save(notification);
    }

    @Override
    public void markAsRead(UUID notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Bildirim bulunamadi: " + notificationId));
        notification.setOkunduMu(true);
        notificationRepository.save(notification);
    }

    @Override
    public void markAllAsRead(UUID userId) {
        List<Notification> unread = notificationRepository.findByUserIdAndOkunduMuFalse(userId);
        unread.forEach(n -> n.setOkunduMu(true));
        notificationRepository.saveAll(unread);
    }
}
