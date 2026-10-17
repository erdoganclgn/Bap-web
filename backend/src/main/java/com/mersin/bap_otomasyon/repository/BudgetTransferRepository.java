package com.mersin.bap_otomasyon.repository;

import com.mersin.bap_otomasyon.entity.BudgetTransfer;
import com.mersin.bap_otomasyon.entity.enums.TransferDurumu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BudgetTransferRepository extends JpaRepository<BudgetTransfer, UUID> {
    List<BudgetTransfer> findByProjectId(UUID projeId);

    List<BudgetTransfer> findByProjectIdAndDurum(UUID projeId, TransferDurumu durum);
}
