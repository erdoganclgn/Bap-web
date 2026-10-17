package com.mersin.bap_otomasyon.repository;

import com.mersin.bap_otomasyon.entity.IntegrationLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IntegrationLogRepository extends JpaRepository<IntegrationLog, Long> {
    List<IntegrationLog> findByEntegrasyonAd(String entegrasyonAd);

    List<IntegrationLog> findByBasariliMiFalse();
}
