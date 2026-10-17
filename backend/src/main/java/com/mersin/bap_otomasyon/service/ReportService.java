package com.mersin.bap_otomasyon.service;

import com.mersin.bap_otomasyon.entity.Report;
import com.mersin.bap_otomasyon.entity.enums.RaporTuru;

import java.util.List;
import java.util.UUID;

public interface ReportService {
    List<Report> findByProject(UUID projeId);
    Report submitReport(UUID projeId, RaporTuru raporTuru, Report report);
    Report evaluateReport(UUID reportId, String karar, String notlar);
    Report getLatestReport(UUID projeId, RaporTuru raporTuru);
    void checkDeadlines();
    void applyPenalty(UUID projeId);
    void liftPenalty(UUID projeId);
}
