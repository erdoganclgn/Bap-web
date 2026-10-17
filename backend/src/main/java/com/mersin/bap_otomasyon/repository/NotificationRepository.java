package com.mersin.bap_otomasyon.repository;

import com.mersin.bap_otomasyon.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findByUserIdOrderByCreatedAtDesc(UUID userId);

    List<Notification> findByUserIdAndOkunduMuFalse(UUID userId);

    long countByUserIdAndOkunduMuFalse(UUID userId);
}
