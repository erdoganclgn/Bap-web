package com.mersin.bap_otomasyon.controller;

import com.mersin.bap_otomasyon.dto.request.AltAlanRequest;
import com.mersin.bap_otomasyon.dto.request.TemelAlanRequest;
import com.mersin.bap_otomasyon.dto.response.AltAlanResponse;
import com.mersin.bap_otomasyon.dto.response.ApiResponse;
import com.mersin.bap_otomasyon.dto.response.TemelAlanResponse;
import com.mersin.bap_otomasyon.service.TemelAlanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/temel-alanlar")
public class TemelAlanController {

    private final TemelAlanService temelAlanService;

    public TemelAlanController(TemelAlanService temelAlanService) {
        this.temelAlanService = temelAlanService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TemelAlanResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.ok(temelAlanService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TemelAlanResponse>> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.ok(temelAlanService.findById(id)));
    }

    @GetMapping("/by-kod/{kod}")
    public ResponseEntity<ApiResponse<TemelAlanResponse>> getByKod(@PathVariable String kod) {
        return ResponseEntity.ok(ApiResponse.ok(temelAlanService.findByKod(kod)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TemelAlanResponse>> create(@RequestBody TemelAlanRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(temelAlanService.create(request)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<TemelAlanResponse>> update(
            @PathVariable UUID id, @RequestBody TemelAlanRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(temelAlanService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        temelAlanService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{temelAlanId}/alt-alanlar")
    public ResponseEntity<ApiResponse<List<AltAlanResponse>>> getAltAlanlar(@PathVariable UUID temelAlanId) {
        return ResponseEntity.ok(ApiResponse.ok(temelAlanService.findAltAlanlar(temelAlanId)));
    }

    @GetMapping("/{temelAlanId}/alt-alanlar/{altAlanId}")
    public ResponseEntity<ApiResponse<AltAlanResponse>> getAltAlan(
            @PathVariable UUID temelAlanId, @PathVariable UUID altAlanId) {
        return ResponseEntity.ok(ApiResponse.ok(temelAlanService.findAltAlan(temelAlanId, altAlanId)));
    }

    @PostMapping("/{temelAlanId}/alt-alanlar")
    public ResponseEntity<ApiResponse<AltAlanResponse>> createAltAlan(
            @PathVariable UUID temelAlanId, @RequestBody AltAlanRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(temelAlanService.createAltAlan(temelAlanId, request)));
    }

    @PatchMapping("/{temelAlanId}/alt-alanlar/{altAlanId}")
    public ResponseEntity<ApiResponse<AltAlanResponse>> updateAltAlan(
            @PathVariable UUID temelAlanId, @PathVariable UUID altAlanId, @RequestBody AltAlanRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(temelAlanService.updateAltAlan(temelAlanId, altAlanId, request)));
    }

    @DeleteMapping("/{temelAlanId}/alt-alanlar/{altAlanId}")
    public ResponseEntity<Void> deleteAltAlan(@PathVariable UUID temelAlanId, @PathVariable UUID altAlanId) {
        temelAlanService.deleteAltAlan(temelAlanId, altAlanId);
        return ResponseEntity.noContent().build();
    }
}