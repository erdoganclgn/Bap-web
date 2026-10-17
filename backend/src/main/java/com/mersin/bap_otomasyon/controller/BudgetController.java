package com.mersin.bap_otomasyon.controller;

import com.mersin.bap_otomasyon.dto.request.BudgetTransferRequest;
import com.mersin.bap_otomasyon.dto.response.ApiResponse;
import com.mersin.bap_otomasyon.dto.response.BudgetSummaryResponse;
import com.mersin.bap_otomasyon.entity.BudgetItem;
import com.mersin.bap_otomasyon.entity.BudgetTransfer;
import com.mersin.bap_otomasyon.service.BudgetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects/{projeId}/budget")
public class BudgetController {

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BudgetItem>>> getItems(@PathVariable UUID projeId) {
        return ResponseEntity.ok(ApiResponse.ok(budgetService.getItems(projeId)));
    }

    @PostMapping("/items")
    public ResponseEntity<ApiResponse<BudgetItem>> addItem(
            @PathVariable UUID projeId, @RequestBody BudgetItem item) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(budgetService.addItem(projeId, item)));
    }

    @PatchMapping("/items/{itemId}")
    public ResponseEntity<ApiResponse<BudgetItem>> updateItem(
            @PathVariable UUID itemId, @RequestBody BudgetItem item) {
        return ResponseEntity.ok(ApiResponse.ok(budgetService.updateItem(itemId, item)));
    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> removeItem(@PathVariable UUID itemId) {
        budgetService.removeItem(itemId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ozet")
    public ResponseEntity<ApiResponse<BudgetSummaryResponse>> getSummary(@PathVariable UUID projeId) {
        List<BudgetItem> items = budgetService.getItems(projeId);
        @SuppressWarnings("null")
        BigDecimal toplam = items.stream()
                .map(BudgetItem::getToplamTutar)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal kalan = budgetService.getRemainingBudget(projeId);
        return ResponseEntity.ok(ApiResponse.ok(
                new BudgetSummaryResponse(toplam, toplam.subtract(kalan), kalan)));
    }

    @PostMapping("/transfers")
    public ResponseEntity<ApiResponse<BudgetTransfer>> requestTransfer(
            @PathVariable UUID projeId, @RequestBody BudgetTransferRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(
                budgetService.requestTransfer(projeId, request.getKaynakKalemId(),
                        request.getHedefKalemId(), request.getTutar(), request.getGerekce())));
    }

    @PostMapping("/transfers/{transferId}/approve")
    public ResponseEntity<ApiResponse<BudgetTransfer>> approveTransfer(@PathVariable UUID transferId) {
        return ResponseEntity.ok(ApiResponse.ok(budgetService.approveTransfer(transferId)));
    }

    @PostMapping("/transfers/{transferId}/reject")
    public ResponseEntity<ApiResponse<BudgetTransfer>> rejectTransfer(
            @PathVariable UUID transferId, @RequestParam String reason) {
        return ResponseEntity.ok(ApiResponse.ok(budgetService.rejectTransfer(transferId, reason)));
    }

    @GetMapping("/transfers")
    public ResponseEntity<ApiResponse<List<BudgetTransfer>>> getTransfers(@PathVariable UUID projeId) {
        return ResponseEntity.ok(ApiResponse.ok(budgetService.getTransfers(projeId)));
    }
}