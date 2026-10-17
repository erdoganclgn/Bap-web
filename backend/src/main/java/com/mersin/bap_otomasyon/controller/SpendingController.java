package com.mersin.bap_otomasyon.controller;

import com.mersin.bap_otomasyon.dto.request.AdvanceCloseRequest;
import com.mersin.bap_otomasyon.dto.request.ExpenseVoucherStatusRequest;
import com.mersin.bap_otomasyon.dto.response.ApiResponse;
import com.mersin.bap_otomasyon.entity.Advance;
import com.mersin.bap_otomasyon.entity.ExpenseVoucher;
import com.mersin.bap_otomasyon.entity.PurchaseRequest;
import com.mersin.bap_otomasyon.exception.BusinessException;
import com.mersin.bap_otomasyon.service.SpendingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects/{projeId}/purchases")
public class SpendingController {

    private final SpendingService spendingService;

    public SpendingController(SpendingService spendingService) {
        this.spendingService = spendingService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PurchaseRequest>>> getPurchases(@PathVariable UUID projeId) {
        return ResponseEntity.ok(ApiResponse.ok(spendingService.getPurchaseRequests(projeId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PurchaseRequest>> createPurchase(
            @PathVariable UUID projeId,
            @RequestParam UUID butceKalemId,
            @RequestBody PurchaseRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(
                spendingService.createPurchaseRequest(projeId, butceKalemId, request)));
    }

    @PostMapping("/{purchaseId}/approve")
    public ResponseEntity<ApiResponse<PurchaseRequest>> approvePurchase(@PathVariable UUID purchaseId) {
        return ResponseEntity.ok(ApiResponse.ok(spendingService.approvePurchase(purchaseId)));
    }

    @PostMapping("/{purchaseId}/reject")
    public ResponseEntity<ApiResponse<PurchaseRequest>> rejectPurchase(
            @PathVariable UUID purchaseId, @RequestParam String reason) {
        return ResponseEntity.ok(ApiResponse.ok(spendingService.rejectPurchase(purchaseId, reason)));
    }

    @GetMapping("/advances")
    public ResponseEntity<ApiResponse<?>> getAdvances(
            @PathVariable UUID projeId,
            @RequestParam(required = false) String durum) {
        if (durum != null) {
            if (!"Gecikmis".equals(durum)) {
                throw new BusinessException("BAP-SPN-002", "Gecersiz avans durumu: " + durum);
            }
            return ResponseEntity.ok(ApiResponse.ok(spendingService.getOverdueAdvances()));
        }
        return ResponseEntity.ok(ApiResponse.ok(spendingService.getAdvances(projeId)));
    }

    @PostMapping("/advances")
    public ResponseEntity<ApiResponse<Advance>> createAdvance(
            @PathVariable UUID projeId,
            @RequestParam UUID purchaseId,
            @RequestParam String avansTuru,
            @RequestParam BigDecimal tutar) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(
                spendingService.createAdvance(projeId, purchaseId, avansTuru, tutar)));
    }

    @PostMapping("/advances/{advanceId}/close")
    public ResponseEntity<ApiResponse<Advance>> closeAdvance(
            @PathVariable UUID advanceId, @RequestBody AdvanceCloseRequest request) {
        if (request.getMahsubBelgesiId() == null) {
            throw new BusinessException("BAP-SPN-003", "mahsubBelgesiId zorunludur");
        }
        return ResponseEntity.ok(ApiResponse.ok(
                spendingService.closeAdvance(advanceId, request.getMahsubBelgesiId())));
    }

    @GetMapping("/expense-vouchers")
    public ResponseEntity<ApiResponse<List<ExpenseVoucher>>> getExpenseVouchers(@PathVariable UUID projeId) {
        return ResponseEntity.ok(ApiResponse.ok(spendingService.getExpenseVouchers(projeId)));
    }

    @PostMapping("/expense-vouchers")
    public ResponseEntity<ApiResponse<ExpenseVoucher>> createExpenseVoucher(
            @PathVariable UUID projeId, @RequestBody ExpenseVoucher voucher) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(
                spendingService.createExpenseVoucher(projeId, voucher)));
    }

    @PatchMapping("/expense-vouchers/{voucherId}")
    public ResponseEntity<ApiResponse<ExpenseVoucher>> updateVoucherStatus(
            @PathVariable UUID voucherId, @RequestBody ExpenseVoucherStatusRequest request) {
        return ResponseEntity.ok(ApiResponse.ok(
                spendingService.updateExpenseVoucherStatus(voucherId, request.getDurum())));
    }
}