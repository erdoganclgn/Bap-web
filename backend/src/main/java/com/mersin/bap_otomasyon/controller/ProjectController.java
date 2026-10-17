package com.mersin.bap_otomasyon.controller;

import com.mersin.bap_otomasyon.dto.request.CreateProjectRequest;
import com.mersin.bap_otomasyon.dto.request.ProjectStatusRequest;
import com.mersin.bap_otomasyon.dto.response.ApiResponse;
import com.mersin.bap_otomasyon.dto.response.ProjectLimitResponse;
import com.mersin.bap_otomasyon.dto.response.ValidationResult;
import com.mersin.bap_otomasyon.entity.Project;
import com.mersin.bap_otomasyon.entity.enums.ProjeDurum;
import com.mersin.bap_otomasyon.service.ProjectService;
import com.mersin.bap_otomasyon.service.ProjectValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectValidationService projectValidationService;

    public ProjectController(ProjectService projectService, ProjectValidationService projectValidationService) {
        this.projectService = projectService;
        this.projectValidationService = projectValidationService;
    }

    @GetMapping("/api/projects")
    public ResponseEntity<ApiResponse<List<Project>>> getAll(
            @RequestParam(required = false) ProjeDurum durum) {
        if (durum != null) {
            return ResponseEntity.ok(ApiResponse.ok(projectService.findByDurum(durum)));
        }
        return ResponseEntity.ok(ApiResponse.ok(projectService.findAll()));
    }

    @GetMapping("/api/projects/{id}")
    public ResponseEntity<ApiResponse<Project>> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.ok(projectService.findById(id)));
    }

    @PostMapping("/api/projects")
    public ResponseEntity<ApiResponse<Project>> create(
            @RequestParam UUID userId,
            @RequestBody CreateProjectRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(projectService.create(userId, request)));
    }

    @PostMapping("/api/projects/validate")
    public ResponseEntity<ApiResponse<ValidationResult>> validate(
            @RequestParam UUID userId,
            @RequestBody CreateProjectRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(
                projectValidationService.validate(userId, request)));
    }

    @PatchMapping("/api/projects/{id}")
    public ResponseEntity<ApiResponse<Project>> update(@PathVariable UUID id, @RequestBody Project project) {
        return ResponseEntity.ok(ApiResponse.ok(projectService.update(id, project)));
    }

    @DeleteMapping("/api/projects/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/api/projects/{id}/submit")
    public ResponseEntity<ApiResponse<Project>> submit(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.ok(projectService.submitForReview(id)));
    }

    @PostMapping("/api/projects/{id}/approve")
    public ResponseEntity<ApiResponse<Project>> approve(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.ok(projectService.approveByExecutive(id)));
    }

    @PostMapping("/api/projects/{id}/review")
    public ResponseEntity<ApiResponse<Project>> review(
            @PathVariable UUID id, @RequestBody ProjectStatusRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(
                projectService.reviewByUnit(id, request.isApproved(), request.getReason())));
    }

    @PostMapping("/api/projects/{id}/submit-commission")
    public ResponseEntity<ApiResponse<Project>> submitCommission(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.ok(projectService.submitToCommission(id)));
    }

    @PostMapping("/api/projects/{id}/state")
    public ResponseEntity<ApiResponse<Project>> changeState(
            @PathVariable UUID id, @RequestParam ProjeDurum newState) {
        return ResponseEntity.ok(ApiResponse.ok(projectService.changeState(id, newState)));
    }

    @GetMapping("/api/users/{userId}/projects")
    public ResponseEntity<ApiResponse<List<Project>>> getByYurutucu(@PathVariable UUID userId) {
        return ResponseEntity.ok(ApiResponse.ok(projectService.findByYurutucu(userId)));
    }

    @GetMapping("/api/projects/{id}/limits")
    public ResponseEntity<ApiResponse<ProjectLimitResponse>> getLimits(@PathVariable UUID id) {
        Project project = projectService.findById(id);
        long aktif = projectService.countActiveByYurutucu(project.getYurutucu().getId());
        Integer limit = project.getProjectType() != null ? project.getProjectType().getEszamanliLimit() : 0;
        return ResponseEntity.ok(ApiResponse.ok(
                new ProjectLimitResponse(id, aktif, limit, aktif < limit)));
    }
}