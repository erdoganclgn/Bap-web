package com.mersin.bap_otomasyon.controller;

import com.mersin.bap_otomasyon.dto.request.ProjectTypeCreateRequest;
import com.mersin.bap_otomasyon.dto.request.ProjectTypeUpdateRequest;
import com.mersin.bap_otomasyon.dto.response.ApiResponse;
import com.mersin.bap_otomasyon.dto.response.ProjectTypeResponse;
import com.mersin.bap_otomasyon.service.ProjectTypeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project-types")
public class ProjectTypeController {

    private final ProjectTypeService projectTypeService;

    public ProjectTypeController(ProjectTypeService projectTypeService) {
        this.projectTypeService = projectTypeService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProjectTypeResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.ok(projectTypeService.findAll()));
    }

    @GetMapping("/{kod}")
    public ResponseEntity<ApiResponse<ProjectTypeResponse>> getByKod(@PathVariable String kod) {
        return ResponseEntity.ok(ApiResponse.ok(projectTypeService.findByKod(kod)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProjectTypeResponse>> create(
            @Valid @RequestBody ProjectTypeCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(projectTypeService.create(request)));
    }

    @DeleteMapping("/{kod}")
    public ResponseEntity<Void> delete(@PathVariable String kod) {
        projectTypeService.delete(kod);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{kod}")
    public ResponseEntity<ApiResponse<ProjectTypeResponse>> update(
            @PathVariable String kod,
            @RequestBody ProjectTypeUpdateRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(projectTypeService.update(kod, request)));
    }
}
