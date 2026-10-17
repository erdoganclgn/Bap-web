package com.mersin.bap_otomasyon.repository;

import com.mersin.bap_otomasyon.entity.QuotaIncrease;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface QuotaIncreaseRepository extends JpaRepository<QuotaIncrease, UUID> {
    List<QuotaIncrease> findByUserId(UUID kullaniciId);

    List<QuotaIncrease> findByUserIdAndBitisTarihiAfter(UUID kullaniciId, LocalDate date);

    List<QuotaIncrease> findByProjeTuru(String projeTuru);
}
