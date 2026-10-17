package com.mersin.bap_otomasyon.controller;

import com.mersin.bap_otomasyon.dto.request.ClosureCriteriaRequest;
import com.mersin.bap_otomasyon.dto.response.ApiResponse;
import com.mersin.bap_otomasyon.dto.response.ClosureCriteriaResponse;
import com.mersin.bap_otomasyon.service.ClosureCriteriaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/closure-criteria")
public class ClosureCriteriaController {

    private final ClosureCriteriaService closureCriteriaService;

    public ClosureCriteriaController(ClosureCriteriaService closureCriteriaService) {
        this.closureCriteriaService = closureCriteriaService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ClosureCriteriaResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.ok(closureCriteriaService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClosureCriteriaResponse>> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.ok(closureCriteriaService.findById(id)));
    }

    @GetMapping("/by-no/{olcutNo}")
    public ResponseEntity<ApiResponse<ClosureCriteriaResponse>> getByOlcutNo(@PathVariable Integer olcutNo) {
        return ResponseEntity.ok(ApiResponse.ok(closureCriteriaService.findByOlcutNo(olcutNo)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ClosureCriteriaResponse>> create(
            @RequestBody ClosureCriteriaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(closureCriteriaService.create(request)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<ClosureCriteriaResponse>> update(
            @PathVariable UUID id,
            @RequestBody ClosureCriteriaRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(closureCriteriaService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        closureCriteriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}