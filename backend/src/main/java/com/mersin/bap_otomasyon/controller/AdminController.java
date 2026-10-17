package com.mersin.bap_otomasyon.controller;

import com.mersin.bap_otomasyon.dto.request.UserBanRequest;
import com.mersin.bap_otomasyon.dto.response.ApiResponse;
import com.mersin.bap_otomasyon.entity.*;
import com.mersin.bap_otomasyon.entity.enums.KullaniciRol;
import com.mersin.bap_otomasyon.repository.AuditLogRepository;
import com.mersin.bap_otomasyon.service.ParameterService;
import com.mersin.bap_otomasyon.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('Admin')")
public class AdminController {

    private final UserService userService;
    private final ParameterService parameterService;
    private final AuditLogRepository auditLogRepository;

    public AdminController(UserService userService, ParameterService parameterService,
                           AuditLogRepository auditLogRepository) {
        this.userService = userService;
        this.parameterService = parameterService;
        this.auditLogRepository = auditLogRepository;
    }

    // --- Users ---

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers(
            @RequestParam(required = false) KullaniciRol rol,
            @RequestParam(required = false) UUID birimId) {
        if (rol != null) {
            return ResponseEntity.ok(ApiResponse.ok(userService.findByRol(rol)));
        }
        if (birimId != null) {
            return ResponseEntity.ok(ApiResponse.ok(userService.findByBirim(birimId)));
        }
        return ResponseEntity.ok(ApiResponse.ok(userService.findAll()));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ApiResponse<User>> getUser(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.ok(userService.findById(id)));
    }

    @PostMapping("/users")
    public ResponseEntity<ApiResponse<User>> createUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(userService.create(user)));
    }

    @PatchMapping("/users/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(@PathVariable UUID id, @RequestBody User user) {
        return ResponseEntity.ok(ApiResponse.ok(userService.update(id, user)));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/users/{id}/ban")
    public ResponseEntity<ApiResponse<Void>> banUser(@PathVariable UUID id, @RequestBody UserBanRequest request) {
        userService.banUser(id, request.getYasakBitisTarihi());
        return ResponseEntity.ok(ApiResponse.ok(null, "Destek yasagi uygulandi."));
    }

    @PostMapping("/users/{id}/unban")
    public ResponseEntity<ApiResponse<Void>> unbanUser(@PathVariable UUID id) {
        userService.unbanUser(id);
        return ResponseEntity.ok(ApiResponse.ok(null, "Destek yasagi kaldirildi."));
    }

    // --- Parameters ---

    @GetMapping("/parameters")
    public ResponseEntity<ApiResponse<List<Parameter>>> getAllParameters(
            @RequestParam(required = false) String grup) {
        if (grup != null) {
            return ResponseEntity.ok(ApiResponse.ok(parameterService.findByGrup(grup)));
        }
        return ResponseEntity.ok(ApiResponse.ok(parameterService.findAll()));
    }

    @GetMapping("/parameters/{id}")
    public ResponseEntity<ApiResponse<Parameter>> getParameter(@PathVariable UUID id) {
        Parameter parameter = parameterService.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Parametre bulunamadi: " + id));
        return ResponseEntity.ok(ApiResponse.ok(parameter));
    }

    @PostMapping("/parameters")
    public ResponseEntity<ApiResponse<Parameter>> createParameter(@RequestBody Parameter parameter) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(parameterService.create(parameter)));
    }

    @PatchMapping("/parameters/{id}")
    public ResponseEntity<ApiResponse<Parameter>> updateParameter(
            @PathVariable UUID id, @RequestBody Parameter parameter) {
        return ResponseEntity.ok(ApiResponse.ok(parameterService.update(id, parameter)));
    }

    // --- Audit ---

    @GetMapping("/audit")
    public ResponseEntity<ApiResponse<List<AuditLog>>> getAuditLogs(
            @RequestParam(required = false) String varlikTuru,
            @RequestParam(required = false) UUID varlikId) {
        if (varlikTuru != null && varlikId != null) {
            return ResponseEntity.ok(ApiResponse.ok(auditLogRepository.findByVarlikTuruAndVarlikId(varlikTuru, varlikId)));
        }
        return ResponseEntity.ok(ApiResponse.ok(auditLogRepository.findAll()));
    }
}