package com.mersin.bap_otomasyon.controller;

import com.mersin.bap_otomasyon.dto.request.EvaluationRequest;
import com.mersin.bap_otomasyon.dto.response.ApiResponse;
import com.mersin.bap_otomasyon.entity.Evaluation;
import com.mersin.bap_otomasyon.entity.enums.DegerlendirmeTuru;
import com.mersin.bap_otomasyon.service.EvaluationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class EvaluationController {

    private final EvaluationService evaluationService;

    public EvaluationController(EvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    @GetMapping("/api/projects/{projeId}/evaluations")
    public ResponseEntity<ApiResponse<List<Evaluation>>> getByProject(@PathVariable UUID projeId) {
        return ResponseEntity.ok(ApiResponse.ok(evaluationService.findByProject(projeId)));
    }

    @GetMapping("/api/evaluations")
    public ResponseEntity<ApiResponse<List<Evaluation>>> getPending(
            @RequestParam UUID degerlendiriciId) {
        return ResponseEntity.ok(ApiResponse.ok(evaluationService.findPendingByReviewer(degerlendiriciId)));
    }

    @PostMapping("/api/evaluations")
    public ResponseEntity<ApiResponse<Evaluation>> startEvaluation(@RequestBody EvaluationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(
                evaluationService.startEvaluation(
                        request.getProjeId(), null,
                        DegerlendirmeTuru.valueOf(request.getDegerlendirmeTuru()))));
    }

    @PostMapping("/api/evaluations/{id}/invite")
    public ResponseEntity<ApiResponse<Void>> invite(@PathVariable UUID id) {
        evaluationService.inviteReviewer(id);
        return ResponseEntity.ok(ApiResponse.ok(null, "Degerlendirmeye davet gonderildi."));
    }

    @PostMapping("/api/evaluations/{id}/accept")
    public ResponseEntity<ApiResponse<Evaluation>> accept(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.ok(evaluationService.acceptInvitation(id)));
    }

    @PostMapping("/api/evaluations/{id}/reject")
    public ResponseEntity<ApiResponse<Evaluation>> reject(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.ok(evaluationService.rejectInvitation(id)));
    }

    @PostMapping("/api/evaluations/{id}/submit")
    public ResponseEntity<ApiResponse<Evaluation>> submit(
            @PathVariable UUID id, @RequestBody EvaluationRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(
                evaluationService.submitEvaluation(
                        id, request.getPuan(), request.getGorus(), request.getKarar())));
    }

    @PostMapping("/api/evaluations/{id}/remind")
    public ResponseEntity<ApiResponse<Evaluation>> remind(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.ok(evaluationService.sendReminder(id)));
    }
}