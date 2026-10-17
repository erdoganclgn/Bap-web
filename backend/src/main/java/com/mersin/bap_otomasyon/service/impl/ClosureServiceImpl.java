package com.mersin.bap_otomasyon.service.impl;

import com.mersin.bap_otomasyon.entity.ClosureCriteria;
import com.mersin.bap_otomasyon.entity.Project;
import com.mersin.bap_otomasyon.entity.enums.ProjeDurum;
import com.mersin.bap_otomasyon.repository.ClosureCriteriaRepository;
import com.mersin.bap_otomasyon.repository.ProjectRepository;
import com.mersin.bap_otomasyon.service.ClosureService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ClosureServiceImpl implements ClosureService {

    private final ProjectRepository projectRepository;
    private final ClosureCriteriaRepository closureCriteriaRepository;

    public ClosureServiceImpl(ProjectRepository projectRepository,
                              ClosureCriteriaRepository closureCriteriaRepository) {
        this.projectRepository = projectRepository;
        this.closureCriteriaRepository = closureCriteriaRepository;
    }

    @Override
    public void closeProject(UUID projeId, UUID olcutId) {
        Project project = projectRepository.findById(projeId)
                .orElseThrow(() -> new RuntimeException("Proje bulunamadi"));
        ClosureCriteria criteria = closureCriteriaRepository.findById(olcutId)
                .orElseThrow(() -> new RuntimeException("Kapatma olcutu bulunamadi"));

        project.setDurum(ProjeDurum.Kapandi);
        project.setKapatmaOlcult(criteria);
        project.setYayinSartiSaglandiMi(true);
        project.setKontenjanIstilaEdiyorMu(false);
        projectRepository.save(project);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean checkYayinSarti(UUID projeId) {
        Project project = projectRepository.findById(projeId)
                .orElseThrow(() -> new RuntimeException("Proje bulunamadi"));
        return project.getYayinSartiSaglandiMi();
    }

    @Override
    public void releaseQuota(UUID projeId) {
        Project project = projectRepository.findById(projeId)
                .orElseThrow(() -> new RuntimeException("Proje bulunamadi"));
        project.setKontenjanIstilaEdiyorMu(false);
        projectRepository.save(project);
    }

    @Override
    public void applyYayinIbaresiControl(UUID projeId) {
        // BAP destek ibaresi kontrolu yapilacak
    }

    @Override
    public void checkContenantIstila() {
        List<Project> istilaEdenler = projectRepository.findByKontenjanIstilaEdiyorMuTrue();
        for (Project project : istilaEdenler) {
            if (project.getYayinSartiSaglandiMi()) {
                project.setKontenjanIstilaEdiyorMu(false);
                projectRepository.save(project);
            }
        }
    }
}
