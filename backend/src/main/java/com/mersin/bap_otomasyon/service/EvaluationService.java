package com.mersin.bap_otomasyon.service;

import com.mersin.bap_otomasyon.entity.Evaluation;
import com.mersin.bap_otomasyon.entity.enums.DegerlendirmeTuru;

import java.util.List;
import java.util.UUID;

public interface EvaluationService {
    List<Evaluation> findByProject(UUID projeId);
    Evaluation findById(UUID evaluationId);
    List<Evaluation> findPendingByReviewer(UUID degerlendiriciId);
    Evaluation startEvaluation(UUID projeId, UUID degerlendiriciId, DegerlendirmeTuru tur);
    void inviteReviewer(UUID evaluationId);
    Evaluation acceptInvitation(UUID evaluationId);
    Evaluation rejectInvitation(UUID evaluationId);
    Evaluation submitEvaluation(UUID evaluationId, Integer puan, String gorus, String karar);
    Evaluation sendReminder(UUID evaluationId);
}
