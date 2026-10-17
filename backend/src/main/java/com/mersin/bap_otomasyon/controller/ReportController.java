package com.mersin.bap_otomasyon.controller;

import com.mersin.bap_otomasyon.dto.request.ReportEvaluationRequest;
import com.mersin.bap_otomasyon.dto.response.ApiResponse;
import com.mersin.bap_otomasyon.entity.Report;
import com.mersin.bap_otomasyon.entity.enums.RaporTuru;
import com.mersin.bap_otomasyon.exception.BusinessException;
import com.mersin.bap_otomasyon.service.ReportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects/{projeId}/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAll(
            @PathVariable UUID projeId,
            @RequestParam(required = false) RaporTuru raporTuru,
            @RequestParam(required = false) Boolean latest) {
        if (Boolean.TRUE.equals(latest)) {
            if (raporTuru == null) {
                throw new BusinessException("BAP-RPT-001", "latest icin raporTuru zorunludur");
            }
            return ResponseEntity.ok(ApiResponse.ok(reportService.getLatestReport(projeId, raporTuru)));
        }
        List<Report> reports = reportService.findByProject(projeId);
        if (raporTuru != null) {
            reports = reports.stream().filter(r -> r.getRaporTuru() == raporTuru).toList();
        }
        return ResponseEntity.ok(ApiResponse.ok(reports));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Report>> submit(
            @PathVariable UUID projeId,
            @RequestParam RaporTuru raporTuru,
            @RequestBody Report report) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(reportService.submitReport(projeId, raporTuru, report)));
    }

    @PostMapping("/{reportId}/evaluate")
    public ResponseEntity<ApiResponse<Report>> evaluate(
            @PathVariable UUID reportId, @RequestBody ReportEvaluationRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(
                reportService.evaluateReport(reportId, request.getKarar(), request.getNotlar())));
    }
}