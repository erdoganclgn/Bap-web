package com.mersin.bap_otomasyon.service.impl;

import com.mersin.bap_otomasyon.entity.Evaluation;
import com.mersin.bap_otomasyon.entity.Project;
import com.mersin.bap_otomasyon.entity.User;
import com.mersin.bap_otomasyon.entity.enums.DegerlendiriciTip;
import com.mersin.bap_otomasyon.entity.enums.DegerlendirmeKarar;
import com.mersin.bap_otomasyon.entity.enums.DegerlendirmeTuru;
import com.mersin.bap_otomasyon.repository.EvaluationRepository;
import com.mersin.bap_otomasyon.repository.ProjectRepository;
import com.mersin.bap_otomasyon.repository.UserRepository;
import com.mersin.bap_otomasyon.service.EvaluationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class EvaluationServiceImpl implements EvaluationService {

    private final EvaluationRepository evaluationRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public EvaluationServiceImpl(EvaluationRepository evaluationRepository,
                                 ProjectRepository projectRepository,
                                 UserRepository userRepository) {
        this.evaluationRepository = evaluationRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Evaluation> findByProject(UUID projeId) {
        return evaluationRepository.findByProjectId(projeId);
    }

    @Override
    @Transactional(readOnly = true)
    public Evaluation findById(UUID evaluationId) {
        return evaluationRepository.findById(evaluationId)
                .orElseThrow(() -> new RuntimeException("Degerlendirme bulunamadi"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Evaluation> findPendingByReviewer(UUID degerlendiriciId) {
        return evaluationRepository.findByDegerlendiriciIdAndTeslimTarihiIsNull(degerlendiriciId);
    }

    @Override
    public Evaluation startEvaluation(UUID projeId, UUID degerlendiriciId, DegerlendirmeTuru tur) {
        Project project = projectRepository.findById(projeId)
                .orElseThrow(() -> new RuntimeException("Proje bulunamadi"));
        User degerlendirici = userRepository.findById(degerlendiriciId)
                .orElseThrow(() -> new RuntimeException("Degerlendirici bulunamadi"));

        Evaluation evaluation = new Evaluation();
        evaluation.setProject(project);
        evaluation.setDegerlendirici(degerlendirici);
        evaluation.setDegerlendirmeTuru(tur);
        evaluation.setDegerlendiriciTip(DegerlendiriciTip.Hakem);
        evaluation.setDavetTarihi(LocalDateTime.now());
        return evaluationRepository.save(evaluation);
    }

    @Override
    public void inviteReviewer(UUID evaluationId) {
        // Email sending will be implemented
    }

    @Override
    public Evaluation acceptInvitation(UUID evaluationId) {
        Evaluation evaluation = evaluationRepository.findById(evaluationId)
                .orElseThrow(() -> new RuntimeException("Degerlendirme bulunamadi"));
        evaluation.setDavetCevapDurumu(true);
        evaluation.setDavetCevapTarihi(LocalDateTime.now());
        return evaluationRepository.save(evaluation);
    }

    @Override
    public Evaluation rejectInvitation(UUID evaluationId) {
        Evaluation evaluation = evaluationRepository.findById(evaluationId)
                .orElseThrow(() -> new RuntimeException("Degerlendirme bulunamadi"));
        evaluation.setDavetCevapDurumu(false);
        evaluation.setDavetCevapTarihi(LocalDateTime.now());
        return evaluationRepository.save(evaluation);
    }

    @Override
    public Evaluation submitEvaluation(UUID evaluationId, Integer puan, String gorus, String karar) {
        Evaluation evaluation = evaluationRepository.findById(evaluationId)
                .orElseThrow(() -> new RuntimeException("Degerlendirme bulunamadi"));
        evaluation.setPuan(puan);
        evaluation.setGorus(gorus);
        evaluation.setKarar(DegerlendirmeKarar.valueOf(karar));
        evaluation.setTeslimTarihi(LocalDateTime.now());
        return evaluationRepository.save(evaluation);
    }

    @Override
    public Evaluation sendReminder(UUID evaluationId) {
        Evaluation evaluation = evaluationRepository.findById(evaluationId)
                .orElseThrow(() -> new RuntimeException("Degerlendirme bulunamadi"));
        // Reminder email will be sent
        return evaluation;
    }
}
