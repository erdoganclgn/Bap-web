package com.mersin.bap_otomasyon.repository;

import com.mersin.bap_otomasyon.entity.PurchaseRequest;
import com.mersin.bap_otomasyon.entity.enums.SatinAlmaDurum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PurchaseRequestRepository extends JpaRepository<PurchaseRequest, UUID> {
    List<PurchaseRequest> findByProjectId(UUID projeId);

    List<PurchaseRequest> findByProjectIdAndDurum(UUID projeId, SatinAlmaDurum durum);
}
