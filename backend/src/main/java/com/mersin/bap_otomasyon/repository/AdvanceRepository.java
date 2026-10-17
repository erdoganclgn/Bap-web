package com.mersin.bap_otomasyon.repository;

import com.mersin.bap_otomasyon.entity.Advance;
import com.mersin.bap_otomasyon.entity.enums.AvansDurum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AdvanceRepository extends JpaRepository<Advance, UUID> {
    List<Advance> findByProjectId(UUID projeId);

    List<Advance> findByProjectIdAndDurum(UUID projeId, AvansDurum durum);

    @Query("SELECT a FROM Advance a WHERE a.mahsupEdildiMi = false AND a.tarih < :threshold")
    List<Advance> findOverdueAdvances(@Param("threshold") LocalDate threshold);
}
