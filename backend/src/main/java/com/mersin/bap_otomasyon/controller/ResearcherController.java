package com.mersin.bap_otomasyon.controller;

import com.mersin.bap_otomasyon.dto.request.ResearcherRequest;
import com.mersin.bap_otomasyon.dto.response.ApiResponse;
import com.mersin.bap_otomasyon.dto.response.ResearcherResponse;
import com.mersin.bap_otomasyon.dto.response.UserResearchersResponse;
import com.mersin.bap_otomasyon.entity.enums.ArastirmaciRol;
import com.mersin.bap_otomasyon.service.ResearcherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ResearcherController {

    private final ResearcherService researcherService;

    public ResearcherController(ResearcherService researcherService) {
        this.researcherService = researcherService;
    }

    @GetMapping("/api/projects/{projeId}/researchers")
    public ResponseEntity<ApiResponse<List<ResearcherResponse>>> getByProject(
            @PathVariable UUID projeId,
            @RequestParam(required = false) ArastirmaciRol rol) {
        List<ResearcherResponse> data = rol != null
                ? researcherService.findByProjectIdAndRol(projeId, rol)
                : researcherService.findByProjectId(projeId);
        return ResponseEntity.ok(ApiResponse.ok(data));
    }

    @GetMapping("/api/projects/{projeId}/researchers/{id}")
    public ResponseEntity<ApiResponse<ResearcherResponse>> getById(
            @PathVariable UUID projeId, @PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.ok(researcherService.findById(projeId, id)));
    }

    @PostMapping("/api/projects/{projeId}/researchers")
    public ResponseEntity<ApiResponse<ResearcherResponse>> create(
            @PathVariable UUID projeId, @RequestBody ResearcherRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(researcherService.create(projeId, request)));
    }

    @PatchMapping("/api/projects/{projeId}/researchers/{id}")
    public ResponseEntity<ApiResponse<ResearcherResponse>> update(
            @PathVariable UUID projeId, @PathVariable UUID id,
            @RequestBody ResearcherRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(researcherService.update(projeId, id, request)));
    }

    @DeleteMapping("/api/projects/{projeId}/researchers/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID projeId, @PathVariable UUID id) {
        researcherService.delete(projeId, id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/api/users/{kullaniciId}/researchers")
    public ResponseEntity<ApiResponse<UserResearchersResponse>> getByUser(
            @PathVariable UUID kullaniciId) {
        return ResponseEntity.ok(ApiResponse.ok(researcherService.findByUserId(kullaniciId)));
    }
}