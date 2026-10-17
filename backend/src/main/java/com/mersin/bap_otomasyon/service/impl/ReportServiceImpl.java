package com.mersin.bap_otomasyon.service.impl;

import com.mersin.bap_otomasyon.entity.Project;
import com.mersin.bap_otomasyon.entity.Report;
import com.mersin.bap_otomasyon.entity.enums.ProjeDurum;
import com.mersin.bap_otomasyon.entity.enums.RaporDurum;
import com.mersin.bap_otomasyon.entity.enums.RaporTuru;
import com.mersin.bap_otomasyon.repository.ProjectRepository;
import com.mersin.bap_otomasyon.repository.ReportRepository;
import com.mersin.bap_otomasyon.service.ReportService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final ProjectRepository projectRepository;

    public ReportServiceImpl(ReportRepository reportRepository,
                             ProjectRepository projectRepository) {
        this.reportRepository = reportRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Report> findByProject(UUID projeId) {
        return reportRepository.findByProjectId(projeId);
    }

    @Override
    public Report submitReport(UUID projeId, RaporTuru raporTuru, Report report) {
        Project project = projectRepository.findById(projeId)
                .orElseThrow(() -> new RuntimeException("Proje bulunamadi"));
        report.setProject(project);
        report.setRaporTuru(raporTuru);
        report.setDurum(RaporDurum.Beklemede);
        report.setTeslimTarihi(LocalDate.now());

        if (report.getBaslik() == null) {
            report.setBaslik(raporTuru == RaporTuru.AraRapor ? "Ara Rapor" : "Sonuc Raporu");
        }

        return reportRepository.save(report);
    }

    @Override
    public Report evaluateReport(UUID reportId, String karar, String notlar) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Rapor bulunamadi"));
        report.setDurum(RaporDurum.valueOf(karar));
        report.setDegerlendirmeNotu(notlar);

        if (karar.equals("Kabul") && report.getRaporTuru() == RaporTuru.SonucRaporu) {
            Project project = report.getProject();
            project.setDurum(ProjeDurum.SonucRaporuBekliyor);
            projectRepository.save(project);
        }

        return reportRepository.save(report);
    }

    @Override
    @Transactional(readOnly = true)
    public Report getLatestReport(UUID projeId, RaporTuru raporTuru) {
        return reportRepository
                .findTopByProjectIdAndRaporTuruOrderByDonemNoDesc(projeId, raporTuru)
                .orElse(null);
    }

    @Override
    public void checkDeadlines() {
        List<Project> activeProjects = projectRepository.findByDurum(ProjeDurum.Aktif);
        LocalDate now = LocalDate.now();

        for (Project project : activeProjects) {
            if (project.getBitisTarihi() != null && project.getBitisTarihi().isBefore(now)) {
                project.setDurum(ProjeDurum.SonucRaporuBekliyor);
                projectRepository.save(project);
            }
        }
    }

    @Override
    public void applyPenalty(UUID projeId) {
        Project project = projectRepository.findById(projeId)
                .orElseThrow(() -> new RuntimeException("Proje bulunamadi"));
        project.setDurum(ProjeDurum.RaporGecikti);
        projectRepository.save(project);
    }

    @Override
    public void liftPenalty(UUID projeId) {
        Project project = projectRepository.findById(projeId)
                .orElseThrow(() -> new RuntimeException("Proje bulunamadi"));
        project.setDurum(ProjeDurum.RaporKabul);
        projectRepository.save(project);
    }
}
