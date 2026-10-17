package com.mersin.bap_otomasyon.repository;

import com.mersin.bap_otomasyon.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findByVarlikTuruAndVarlikId(String varlikTuru, UUID varlikId);

    List<AuditLog> findByKullaniciId(UUID kullaniciId);
}
