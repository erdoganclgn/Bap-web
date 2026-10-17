package com.mersin.bap_otomasyon.service;

import com.mersin.bap_otomasyon.entity.BudgetItem;
import com.mersin.bap_otomasyon.entity.BudgetTransfer;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface BudgetService {
    List<BudgetItem> getItems(UUID projeId);
    BudgetItem addItem(UUID projeId, BudgetItem item);
    BudgetItem updateItem(UUID itemId, BudgetItem item);
    void removeItem(UUID itemId);
    List<BudgetTransfer> getTransfers(UUID projeId);
    BudgetTransfer requestTransfer(UUID projeId, UUID kaynakId, UUID hedefId, BigDecimal tutar, String gerekce);
    BudgetTransfer approveTransfer(UUID transferId);
    BudgetTransfer rejectTransfer(UUID transferId, String reason);
    BigDecimal getRemainingBudget(UUID projeId);
    void validateBudgetSufficiency(UUID projeId, BigDecimal amount);
}
