package com.mersin.bap_otomasyon.repository;

import com.mersin.bap_otomasyon.entity.BursaryPayment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BursaryPaymentRepository extends JpaRepository<BursaryPayment, UUID> {
    List<BursaryPayment> findByProjectId(UUID projeId);

    List<BursaryPayment> findByBursiyerId(UUID bursiyerId);

    Optional<BursaryPayment> findByBursiyerIdAndAyAndYil(UUID bursiyerId, Integer ay, Integer yil);
}
