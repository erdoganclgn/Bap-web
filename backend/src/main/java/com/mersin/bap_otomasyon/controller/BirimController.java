package com.mersin.bap_otomasyon.controller;

import com.mersin.bap_otomasyon.dto.request.BirimRequest;
import com.mersin.bap_otomasyon.dto.response.ApiResponse;
import com.mersin.bap_otomasyon.dto.response.BirimResponse;
import com.mersin.bap_otomasyon.service.BirimService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/birimler")
public class BirimController {

    private final BirimService birimService;

    public BirimController(BirimService birimService) {
        this.birimService = birimService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BirimResponse>>> getAll() {
        return ResponseEntity.ok(ApiResponse.ok(birimService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BirimResponse>> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.ok(birimService.findById(id)));
    }

    @GetMapping("/by-kod/{kod}")
    public ResponseEntity<ApiResponse<BirimResponse>> getByKod(@PathVariable String kod) {
        return ResponseEntity.ok(ApiResponse.ok(birimService.findByKod(kod)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BirimResponse>> create(@RequestBody BirimRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.ok(birimService.create(request)));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<BirimResponse>> update(
            @PathVariable UUID id, @RequestBody BirimRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(birimService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        birimService.delete(id);
        return ResponseEntity.noContent().build();
    }
}