package com.mersin.bap_otomasyon.controller;

import com.mersin.bap_otomasyon.dto.response.ApiResponse;
import com.mersin.bap_otomasyon.entity.BursaryPayment;
import com.mersin.bap_otomasyon.entity.ProjectResearcher;
import com.mersin.bap_otomasyon.service.BursaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects/{projeId}/bursary")
public class BursaryController {

    private final BursaryService bursaryService;

    public BursaryController(BursaryService bursaryService) {
        this.bursaryService = bursaryService;
    }

    @GetMapping("/students")
    public ResponseEntity<ApiResponse<List<ProjectResearcher>>> getStudents(@PathVariable UUID projeId) {
        return ResponseEntity.ok(ApiResponse.ok(bursaryService.getBursaryStudents(projeId)));
    }

    @PostMapping("/students")
    public ResponseEntity<ApiResponse<ProjectResearcher>> addStudent(
            @PathVariable UUID projeId, @RequestParam UUID ogrenciId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(bursaryService.addStudent(projeId, ogrenciId)));
    }

    @DeleteMapping("/students/{ogrenciId}")
    public ResponseEntity<Void> removeStudent(
            @PathVariable UUID projeId, @PathVariable UUID ogrenciId) {
        bursaryService.removeStudent(projeId, ogrenciId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/payments")
    public ResponseEntity<ApiResponse<BursaryPayment>> processPayment(
            @PathVariable UUID projeId,
            @RequestParam UUID bursiyerId,
            @RequestParam Integer ay,
            @RequestParam Integer yil) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(bursaryService.processPayment(projeId, bursiyerId, ay, yil)));
    }

    @GetMapping("/payments")
    public ResponseEntity<ApiResponse<List<BursaryPayment>>> getPayments(@PathVariable UUID projeId) {
        return ResponseEntity.ok(ApiResponse.ok(bursaryService.getPaymentHistory(projeId)));
    }
}
