package com.mersin.bap_otomasyon.service;

import com.mersin.bap_otomasyon.entity.Advance;
import com.mersin.bap_otomasyon.entity.ExpenseVoucher;
import com.mersin.bap_otomasyon.entity.PurchaseRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface SpendingService {
    List<PurchaseRequest> getPurchaseRequests(UUID projeId);
    PurchaseRequest createPurchaseRequest(UUID projeId, UUID butceKalemId, PurchaseRequest request);
    PurchaseRequest approvePurchase(UUID purchaseId);
    PurchaseRequest rejectPurchase(UUID purchaseId, String reason);
    List<Advance> getAdvances(UUID projeId);
    Advance createAdvance(UUID projeId, UUID purchaseId, String avansTur, BigDecimal tutar);
    Advance closeAdvance(UUID advanceId, UUID mahsupBelgesiId);
    List<Advance> getOverdueAdvances();
    List<ExpenseVoucher> getExpenseVouchers(UUID projeId);
    ExpenseVoucher createExpenseVoucher(UUID projeId, ExpenseVoucher voucher);
    ExpenseVoucher updateExpenseVoucherStatus(UUID voucherId, String durum);
}
