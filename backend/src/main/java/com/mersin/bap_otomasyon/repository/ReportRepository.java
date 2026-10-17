package com.mersin.bap_otomasyon.repository;

import com.mersin.bap_otomasyon.entity.Report;
import com.mersin.bap_otomasyon.entity.enums.RaporTuru;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReportRepository extends JpaRepository<Report, UUID> {
    List<Report> findByProjectId(UUID projeId);

    List<Report> findByProjectIdAndRaporTuru(UUID projeId, RaporTuru raporTuru);

    Optional<Report> findTopByProjectIdAndRaporTuruOrderByDonemNoDesc(UUID projeId, RaporTuru raporTuru);
}
